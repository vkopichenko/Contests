fun main() {
    var count = 0
    while (true) {
        val line = readLine() ?: break
        if (line.isEmpty()) break
        run breaker@ {
            var abba = false
            """([a-z])([a-z])\2\1""".toRegex().findAll(line).forEach { match ->
                if (match.groupValues[1] == match.groupValues[2]) return@forEach // continue
                val ind1 = line.indexOf('[', match.range.last)
                val ind2 = line.indexOf(']', match.range.last)
                if (ind2 != -1 && (ind2 < ind1 || ind1 == -1)) return@breaker // break
                else abba = true
            }
            if (abba) count++
        }
    }
    println(count)
}


