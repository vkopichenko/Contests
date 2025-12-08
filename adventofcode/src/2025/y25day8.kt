import kotlin.time.measureTimedValue

fun main() {
    data class Vector(val x: Long, val y: Long, val z: Long) {
        fun lengthSquared() = x * x + y * y + z * z
        operator fun minus(that: Vector) = Vector(x - that.x, y - that.y, z - that.z)
        infix fun dist(that: Vector) = (this - that).lengthSquared()
    }
    fun <T> Sequence<T>.pairs() = flatMapIndexed { i, a -> drop(i + 1).map { b -> a to b } }

    val vertices = generateSequence(::readlnOrNull)
        .map { it.split(",").map(String::toLong).run { Vector(get(0), get(1), get(2)) } }.toList()

    measureTimedValue { // part 1
        vertices.asSequence().pairs().sortedBy { (a, b) -> a dist b }.take(1000)
            .fold(vertices.withIndex().associate { it.value to it.index }) { circuits, (a, b) ->
                val circuitA = circuits[a]!!
                val circuitB = circuits[b]!!
                if (circuitA == circuitB) circuits else circuits + circuits.filterValues { it == circuitB }.mapValues { circuitA }
            }.values.groupingBy { it }.eachCount().values.sortedDescending().take(3).fold(1L, Long::times)
    }.also(::println)

    measureTimedValue { // part 2
        vertices.asSequence().pairs().sortedBy { (a, b) -> a dist b }
            .runningFold(vertices[0] to vertices[0] to vertices.withIndex().associate { it.value to it.index }) { (_, circuits), (a, b) ->
                val circuitA = circuits[a]!!
                val circuitB = circuits[b]!!
                a to b to if (circuitA == circuitB) circuits else circuits + circuits.filterValues { it == circuitB }.mapValues { circuitA }
            }.dropWhile { it.second.values.toSet().size > 1 }.first().first.run { first.x * second.x }
    }.also(::println)
}
