fun main() {
    generateSequence { readlnOrNull() }.toList().apply {
        sequence {
            get(0).indices.forEach { j ->
                var lastRock = -1
                indices.forEach { i ->
                    when (get(i)[j]) {
                        '#' -> lastRock = i
                        'O' -> {
                            lastRock = (lastRock + 1 until i).find { get(it)[j] != '#' } ?: i
                            yield(size - lastRock)
                        }
                    }
                }
            }
        }.sum().let(::println)
    }
}
