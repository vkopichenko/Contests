fun main() = println(
    generateSequence(::readLine).toList().run {
        val space = "\\s+".toRegex()
        val args = dropLast(1).map {
            it.trim().split(space).map(String::toLong)
        }
        last().trim().split(space).mapIndexed { i, op ->
            args.map { it[i] }.reduce(if (op == "+") Long::plus else Long::times)
        }.sum()
    }
)
