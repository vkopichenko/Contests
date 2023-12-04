fun main() {
    data class Point(val x: Int, val y: Int)
    generateSequence { readlnOrNull() }.toList().let { rows ->
        val gears = mutableMapOf<Point, MutableList<Int>>()
        rows.forEachIndexed { i, row ->
            var digitsCount = 0
            val adjacentGears = mutableSetOf<Point>()
            fun adj(i: Int, j: Int) {
                if (i in 0..<rows.size && j in 0..<rows[i].length && rows[i][j] != '.') {
                    adjacentGears += Point(i, j)
                }
            }
            row.forEachIndexed { j, symbol ->
                if (symbol.isDigit()) {
                    if (digitsCount == 0) {
                        adj(i, j-1); adj(i-1, j-1); adj(i+1,j-1)
                    }
                    adj(i-1, j); adj(i+1, j)
                    ++digitsCount
                    if (j == row.length-1 || !row[j+1].isDigit()) {
                        adj(i, j+1); adj(i-1, j+1); adj(i+1, j+1)
                        val number = row.substring(j-digitsCount+1, j+1).toInt()
                        adjacentGears.forEach {
                            gears.getOrPut(it) { mutableListOf() } += number
                        }
                        adjacentGears.clear()
                        digitsCount = 0
                    }
                }
            }
        }
        gears.values.sumOf { numbers ->
            numbers.let { if (it.size == 2) it[0] * it[1] else 0 }
        }.let(::println)
    }
}
