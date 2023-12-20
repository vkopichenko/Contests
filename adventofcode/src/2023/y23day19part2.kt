import java.util.*
import kotlin.time.measureTime

private val IntProgression.size: Int get() = last - first + 1
fun main() {
    data class Rule(val property: String, val op: String, val value: Int?, val workflow: String)
    val workflows = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.map { line ->
        val name = line.substringBefore('{')
        val rules = line.substring(name.length + 1, line.length - 1).splitToSequence(',').map { rule ->
            """((\w+)([<>])(\d+):)?(\w+)""".toRegex().matchEntire(rule)!!.destructured.let {
                (_, prop, op, value, workflow) -> Rule(prop, op, value.takeIf(String::isNotEmpty)?.toInt(), workflow)
            }
        }.toList()
        name to rules
    }.toMap()
    val distinctPropValues = buildMap<String, SortedSet<Int>> {
        workflows.values.forEach { rules ->
            rules.forEach { rule ->
                rule.value?.also {
                    getOrPut(rule.property) { sortedSetOf() }.add(
                        it + if (rule.op == "<") 0 else 1
                    )
                }
            }
        }
    }
    println()
    distinctPropValues.forEach { println("${it.key}: ${it.value.size}") }
    val distinctPropRanges = distinctPropValues.mapValues { (_, values) ->
        sequenceOf(1, *values.toTypedArray(), 4001).windowed(2).map { (a, b) -> a until b }
    }
    measureTime {
        var totalAccepted = 0L
        for (x in distinctPropRanges.getValue("x")) {
            for (m in distinctPropRanges.getValue("m")) {
                for (a in distinctPropRanges.getValue("a")) {
                    for (s in distinctPropRanges.getValue("s")) {
                        val accepted = generateSequence("in") { workflow ->
                            workflows.getValue(workflow).firstNotNullOf { rule ->
                                fun value(rule: Rule) = when (rule.property) {
                                    "x" -> x
                                    "m" -> m
                                    "a" -> a
                                    "s" -> s
                                    else -> error("Game over!")
                                }.first
                                rule.workflow.takeIf {
                                    when (rule.op) {
                                        "<" -> value(rule) < rule.value!!
                                        ">" -> value(rule) > rule.value!!
                                        else -> true
                                    }
                                }
                            }
                        }.first { it == "A" || it == "R" } == "A"
                        if (accepted) {
                            totalAccepted += x.size.toLong() * m.size * a.size * s.size
                        }
                    }
                }
            }
        }
        println(totalAccepted)
    }.let(::println)
}
