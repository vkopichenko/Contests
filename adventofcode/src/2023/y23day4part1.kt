fun main() {
    generateSequence { readlnOrNull() }.map { line ->
        val colonIndex = line.indexOf(':')
        val pipeIndex = line.indexOf('|')
        fun String.parseNumbers() =
            splitToSequence(' ').filter { it.isNotEmpty() }.map { it.toInt() }
        val winningNumbers = line.substring(colonIndex + 1, pipeIndex).parseNumbers().toSet()
        val haveNumbers = line.substring(pipeIndex + 1).parseNumbers()
        haveNumbers.count { it in winningNumbers }
            .let { if (it == 0) 0 else 1 shl (it - 1) }
    }/*.onEach(::println)*/.sum().let(::println)
}
