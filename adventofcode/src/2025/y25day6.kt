import kotlin.time.measureTimedValue

fun main() {
    fun Char.perform(): (Long, Long) -> Long = when (this) {
        '+' -> Long::plus
        '*' -> Long::times
        else -> error("Unknown op $this")
    }

    val input = generateSequence(::readlnOrNull).toList()

    measureTimedValue { // part 1
        val space = "\\s+".toRegex()
        val (args, ops) = input.dropLast(1).map {
            it.trim().split(space).map(String::toLong)
        } to input.last().trim().split(space).map { it[0] }
        ops.mapIndexed { i, op ->
            args.map { it[i] }.reduce(op.perform())
        }.sum()
    }.also(::println)

    measureTimedValue { // part 2
        val argLines = input.dropLast(1)
        val opIndices = input.last().run { indices.filterNot { get(it) == ' ' } + length }
        opIndices.windowed(2).sumOf { (pos, next) ->
            val op = input.last()[pos]
            (pos until next).map { col ->
                argLines.map { it[col] }.filterNot { it == ' ' }.joinToString(separator = "")
                    .ifEmpty { if (op == '+') "0" else "1" }.toLong()//.also(::println)
            }.reduce(op.perform())//.also(::println)
        }
    }.also(::println)
}
