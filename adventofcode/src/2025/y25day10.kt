import kotlin.time.measureTimedValue

fun main() {
    val input = generateSequence(::readlnOrNull).toList()

    measureTimedValue { // part 1
        val targets = input.map { it.subSequence(1, it.indexOf(']')).foldRight(0) { c, bits -> bits shl 1 or if (c == '#') 1 else 0 } }
        val buttons = input.map {
            it.subSequence(it.indexOf('(') + 1, it.lastIndexOf(')')).split(") (").map {
                it.splitToSequence(',').fold(0) { bits, s -> bits or (1 shl s.toInt()) }
            }
        }
        targets.asSequence().mapIndexed { i, target ->
            // println("$i -> ${target.toString(2)}")
            val visited = mutableSetOf<Int>()
            generateSequence(listOf(0 to 0)) { states ->
                states.flatMap { (step, state) ->
                    buttons[i].asSequence().map { step + 1 to (it xor state) }
                        .filter { it.second !in visited }.onEach { visited += it.second }
                }
            }.flatten()
                // .onEach { println("${it.first}: ${it.second.toString(2)}") }
                .dropWhile { it.second != target }.first().first
        }.sum()
    }.also(::println)

    measureTimedValue { // part 2
        val buttons = input.map {
            it.subSequence(it.indexOf('(') + 1, it.lastIndexOf(')')).split(") (").map {
                it.split(',').map(String::toInt)
            }
        }
        val joltages = input.map { it.subSequence(it.indexOf('{') + 1, it.lastIndexOf('}')).split(',').map(String::toInt) }
        // TODO: https://github.com/Kotlin/multik mk.linalg.solve
    }.also(::println)
}
