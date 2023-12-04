fun main() {
    generateSequence { readlnOrNull() }.toList().let { rows ->
        fun adj(i: Int, j: Int) = i in 0..<rows.size && j in 0..<rows[i].length && rows[i][j] != '.'
        sequence {
            rows.forEachIndexed { i, row ->
                var digitsCount = 0; var hasAdjacent = false
                row.forEachIndexed { j, symbol ->
                    if (symbol.isDigit()) {
                        hasAdjacent = hasAdjacent ||
                                digitsCount == 0 && (adj(i, j-1) || adj(i-1, j-1) || adj(i+1,j-1)) ||
                                adj(i-1, j) || adj(i+1, j)
                        ++digitsCount
                        if (j == row.length-1 || !row[j+1].isDigit()) {
                            if (hasAdjacent || adj(i, j+1) || adj(i-1, j+1) || adj(i+1, j+1)) {
                                yield(row.substring(j-digitsCount+1, j+1).toInt()/*.also(::println)*/)
                            }
                            digitsCount = 0; hasAdjacent = false
                        }
                    }
                }
            }
        }.sum().let(::println)
    }
}
