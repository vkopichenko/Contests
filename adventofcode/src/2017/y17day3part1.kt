fun main() {
    val coords = generateSequence(1 to Pair(0, 0)) {
        it.first.inc().let { i ->
            i to it.second.let { (x, y) ->
                val (dx, dy) = when {
                    i in 0..1 -> Pair(0, 0)
                    else -> TODO()
                }
                Pair(x + dx, y + dy)
            }
        }
    }.map { it.second }
    println(coords.elementAt(readln().toInt()).run { first + second })
}
