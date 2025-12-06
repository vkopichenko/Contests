fun main() = println(
    generateSequence(::readLine).takeWhile(String::isNotBlank).map {
        it.split("-").map(String::toLong).let { it[0]..it[1] }
    }.toList().let { ranges ->
        generateSequence(::readLine).map(String::toLong)
            .count { id -> ranges.any { id in it } }
    }
)
