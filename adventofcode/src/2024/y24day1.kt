import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val whitespace = "\\s+".toRegex()
    val lists = generateSequence {
        readlnOrNull()?.splitToSequence(whitespace)?.map(String::toInt)?.iterator()?.run { next() to next() }
    }.unzip()

    measureTimedValue { // part 1
        val sortedPairs = lists.first.sorted() zip lists.second.sorted()
        sortedPairs.sumOf { abs(it.first - it.second) }
    }.also(::println)

    measureTimedValue { // part 2
        val secondListDistribution = lists.second.groupingBy { it }.eachCount()
        lists.first.sumOf { it * (secondListDistribution[it] ?: 0) }
    }.also(::println)
}
