fun main() {
    val whitespace = "\\s+".toRegex()
    val lists = generateSequence {
        readlnOrNull()?.splitToSequence(whitespace)?.map(String::toInt)?.iterator()?.run { next() to next() }
    }.unzip()
    val secondListDistribution = lists.second.groupingBy { it }.eachCount()
    println(lists.first.sumOf { it * (secondListDistribution[it] ?: 0) })
}
