fun main() = println(
    generateSequence(::readLine).map(String::toCharArray).toList().run {
        generateSequence {
            indices.sumOf { i ->
                this[i].indices.count { j ->
                    (this[i][j] == '@' && (i - 1..i + 1).sumOf { ii ->
                        (j - 1..j + 1).count { jj ->
                            getOrNull(ii)?.getOrNull(jj) == '@'
                        }
                    } <= 4).also { if (it) this[i][j] = '.' }
                }
            }
        }
    }.takeWhile { it > 0 }.sum()
)
