fun main() {
    generateSequence { readlnOrNull() }.flatMap { it.splitToSequence(',') }.sumOf {
        it.fold(0.toUByte()) { acc, char ->
            ((acc.toInt() + char.code) * 17).toUByte()
        }.toInt()
    }.let(::println)
}
