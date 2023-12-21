import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.MutableSet
import kotlin.collections.buildMap
import kotlin.collections.buildSet
import kotlin.collections.forEach
import kotlin.collections.getOrPut
import kotlin.collections.indexOf
import kotlin.collections.mutableSetOf
import kotlin.collections.plusAssign
import kotlin.time.measureTime

fun main() {
    data class Signal(val pulse: Boolean, val source: String, val destination: String)
    open class Module(val name: String, val destinations: List<String>) {
        fun process(input: Signal, output: (Signal) -> Unit) {
            modulate(input)?.let { pulse ->
                destinations.forEach { output(Signal(pulse, name, it)) }
            }
        }
        open fun modulate(input: Signal): Boolean? = input.pulse
    }
    class FlipFlop(name: String, destinations: List<String>) : Module(name, destinations) {
        var on = false
        override fun modulate(input: Signal) =
            if (!input.pulse) {
                on = !on
                on
            } else null
    }
    class Conjunction(name: String, destinations: List<String>) : Module(name, destinations) {
        val inputs = mutableSetOf<String>()
        val states = BitSet()
        override fun modulate(input: Signal) =
            states.apply {
                set(inputs.indexOf(input.source), input.pulse)
            }.run { cardinality() != inputs.size }
    }
    val modules = generateSequence { readlnOrNull() }.map { line ->
        line.splitToSequence(" -> ", ", ").let { tokens ->
            val it = tokens.iterator()
            val name = it.next()
            val destinations = it.asSequence().toList()
            when (name[0]) {
                '%' -> FlipFlop(name.substring(1), destinations)
                '&' -> Conjunction(name.substring(1), destinations)
                else -> Module(name, destinations)
            }
        }
    }.associateBy { it.name }
    val sources = buildMap<String, MutableSet<String>> {
        modules.values.forEach { module ->
            module.destinations.forEach { dest ->
                (modules[dest] as? Conjunction)?.apply { inputs += module.name }
                getOrPut(dest) { mutableSetOf() } += module.name
            }
        }
    }
    val perspectiveModules = buildSet {
        val marcReachable = DeepRecursiveFunction<String, Unit> { dest ->
            add(dest)
            sources[dest]?.forEach { if (it !in this@buildSet) callRecursive(it) }
        }
        marcReachable("rx")
    }
    println("Total Modules: ${modules.keys.size}")
    println("Reachable Modules: ${perspectiveModules.size}")
    measureTime {
        generateSequence(1L) { it + 1 }.first {
            var rxLoCount = 0
            val queue = ArrayDeque<Signal>()
            fun send(signal: Signal) {
                if (signal.destination in perspectiveModules) queue.add(signal)
            }
            send(Signal(false, "button", "broadcaster"))
            while (true) {
                val signal = queue.removeFirstOrNull() ?: break
                if (signal.destination == "rx" && !signal.pulse) ++rxLoCount
                modules[signal.destination]?.process(signal, ::send)
            }
            rxLoCount == 1
        }.let(::println)
    }.let(::println)
}
