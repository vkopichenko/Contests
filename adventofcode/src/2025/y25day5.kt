import kotlin.time.measureTimedValue

fun main() {
    val ranges = generateSequence(::readlnOrNull).takeWhile(String::isNotBlank)
        .map { it.split("-").map(String::toLong).let { it[0]..it[1] } }.toList()

    measureTimedValue { // part 1
        generateSequence(::readlnOrNull).map(String::toLong)
            .count { id -> ranges.any { id in it } }
    }.also(::println)

    measureTimedValue { // part 2
        ranges.sortedWith(compareBy({ it.first }, { it.last }))
            .fold(0L..0L to 0L) { (prev, cnt), next ->
                (if (next.last < prev.last) prev else next) to cnt + (next.last + 1 - next.first.coerceAtLeast(prev.last + 1)).coerceAtLeast(0)
            }.second
    }.also(::println)
}
