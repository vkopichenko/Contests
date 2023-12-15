package y23day2part1

private enum class Color(val max: Int) { red(12), green(13), blue(14) }
fun main() {
    generateSequence { readLine() }.mapNotNull { game ->
        game.run { substring(indexOf(' ') + 1, indexOf(':')).toInt() }.takeIf { id ->
            game.splitToSequence(':', ';').drop(1).none { round ->
                round.splitToSequence(',').map { it.split(' ') }.any { (_, count, color) ->
                    count.toInt() > Color.valueOf(color).max
                }
            }
        }
    }.sum().let(::println)
}
