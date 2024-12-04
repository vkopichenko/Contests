fun main() {
    val rows = generateSequence { readlnOrNull()?.toList() }.toList()

    // part 1
    val pattern = "XMAS".toList()
    rows.apply {
        val subindices = this[0].indices
        val diags = (pattern.size - indices.count())..(subindices.count() - pattern.size)
        sequenceOf(
            indices.asSequence().map { i -> subindices.asSequence().map { j -> i to j } },
            indices.asSequence().map { i -> subindices.reversed().asSequence().map { j -> i to j } },
            subindices.asSequence().map { j -> indices.asSequence().map { i -> i to j } },
            subindices.asSequence().map { j -> indices.reversed().asSequence().map { i -> i to j } },
            diags.asSequence().map { j -> indices.asSequence().map { i -> i to j + i } },
            diags.asSequence().map { j -> indices.reversed().asSequence().map { i -> i to j + i } },
            diags.asSequence().map { j -> indices.asSequence().map { i -> i to indices.last + j - i } },
            diags.asSequence().map { j -> indices.reversed().asSequence().map { i -> i to indices.last + j - i } },
        ).sumOf { directions ->
            directions.sumOf { path ->
                path.filter { (i, j) ->
                    i in indices && j in subindices
                }.map { (i, j) ->
                    this[i][j]
                }.windowed(pattern.size) { s ->
                    s == pattern
                }.count { it }
            }
        }.also(::println)
    }

    // part 2
    (1 until rows.indices.last).sumOf { i ->
        (1 until rows[i].indices.last).count { j ->
            rows[i][j] == 'A' && sequenceOf(
                setOf(rows[i-1][j-1], rows[i+1][j+1]),
                setOf(rows[i-1][j+1], rows[i+1][j-1]),
            ).all { it == setOf('M', 'S') }
        }
    }.also(::println)
}
