fun main() = println(
    generateSequence(::readLine).takeWhile(String::isNotBlank).map {
        it.split("-").map(String::toLong).let { it[0]..it[1] }
    }.sortedBy { it.first }.fold(0L..0L to 0L) { (prev, cnt), next ->
        (if (next.last < prev.last) prev else next) to cnt + (next.last + 1 - next.first.coerceAtLeast(prev.last + 1)).coerceAtLeast(0)
    }.second
)
