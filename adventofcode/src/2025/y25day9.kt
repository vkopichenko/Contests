import kotlin.math.absoluteValue
import kotlin.time.measureTimedValue

fun main() {
    data class Vector(val x: Long, val y: Long) {
        infix fun area(that: Vector) = ((this.x - that.x + 1) * ((this.y - that.y + 1))).absoluteValue
    }
    fun <T> Sequence<T>.pairs() = flatMapIndexed { i, a -> drop(i + 1).map { b -> a to b } }

    val corners = generateSequence(::readlnOrNull)
        .map { it.split(",").map(String::toLong).run { Vector(get(0), get(1)) } }.toList()

    measureTimedValue { // part 1
        corners.asSequence().pairs().maxOf { (a, b) -> a area b }
    }.also(::println)

    measureTimedValue { // part 2
        // bfs fill is simple, but not efficient
        // TODO: Sunday's winding / ray casting: https://web.archive.org/web/20131210180851/http://geomalgorithms.com/a03-_inclusion.html
    }.also(::println)
}
