fun main() = println(
    generateSequence(::readLine).toList().run {
        val argLines = dropLast(1)
        last().run {
            (indices.filterNot { get(it) == ' ' } + length).windowed(2).sumOf { (pos, next) ->
                val plus = get(pos) == '+'
                (pos until next).map { col ->
                    argLines.map { it[col] }.joinToString(separator = "").trim()
                        .ifEmpty { if (plus) "0" else "1" }.toLong()
                }.reduce(if (plus) Long::plus else Long::times)
            }
        }
    }
)
