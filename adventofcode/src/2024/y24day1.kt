import kotlin.math.abs

fun main() {
    val whitespace = "\\s+".toRegex()
    val lists = generateSequence {
        readlnOrNull()?.splitToSequence(whitespace)?.map(String::toInt)?.iterator()?.run { next() to next() }
    }.unzip()
    // Day 1
    val sortedPairs = lists.first.sorted() zip lists.second.sorted()
    println(sortedPairs.sumOf { abs(it.first - it.second) })
    // Day 2
    val secondListDistribution = lists.second.groupingBy { it }.eachCount()
    println(lists.first.sumOf { it * (secondListDistribution[it] ?: 0) })
}
