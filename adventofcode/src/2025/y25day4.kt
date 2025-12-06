import kotlin.time.measureTimedValue

fun main() {
    operator fun List<CharArray>.get(i: Int, j: Int) = getOrNull(i)?.getOrNull(j)

    val grid = generateSequence(::readlnOrNull).map { it.toCharArray() }.toList()

    measureTimedValue { // part 1
        grid.indices.sumOf { i ->
            grid[i].indices.count { j ->
                grid[i][j] == '@' && (i - 1..i + 1).sumOf { ii ->
                    (j - 1..j + 1).count { jj ->
                        grid[ii, jj] == '@'
                    }
                } <= 4
            }
        }
    }.also(::println)

    measureTimedValue { // part 2
        generateSequence {
            grid.indices.sumOf { i ->
                grid[i].indices.count { j ->
                    (grid[i][j] == '@' && (i - 1..i + 1).sumOf { ii ->
                        (j - 1..j + 1).count { jj ->
                            grid[ii, jj] == '@'
                        }
                    } <= 4).also { if (it) grid[i][j] = '.' }
                }
            }
        }.takeWhile { it > 0 }.sum()
    }.also(::println)
}
