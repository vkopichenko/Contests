fun main() = println(
    generateSequence(::readLine).sumOf {
        val (target, buttons) = it.run {
            subSequence(1, indexOf(']')).foldRight(0) { c, bits ->
                bits shl 1 or if (c == '#') 1 else 0
            } to subSequence(indexOf('(') + 1, lastIndexOf(')')).split(") (").map {
                it.splitToSequence(',').fold(0) { bits, s -> bits or (1 shl s.toInt()) }
            }
        }
        val visited = mutableSetOf<Int>()
        generateSequence(listOf(0 to 0)) { states ->
            states.flatMap { (step, state) ->
                buttons.asSequence().map { step + 1 to (it xor state) }.filter { visited.add(it.second) }
            }
        }.flatten().dropWhile { it.second != target }.first().first
    }
)
