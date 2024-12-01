import kotlin.math.abs

fun main() {
    val whitespace = "\\s+".toRegex()
    val lists = generateSequence {
        readlnOrNull()?.splitToSequence(whitespace)?.map(String::toInt)?.iterator()?.run { next() to next() }
    }.unzip()
    val sortedPairs = lists.first.sorted() zip lists.second.sorted()
    println(sortedPairs.sumOf { abs(it.first - it.second) })
}
