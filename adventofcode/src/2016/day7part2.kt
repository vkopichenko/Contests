fun main() {
    var count = 0
    while (true) {
        val line = readLine() ?: break
        if (line.isEmpty()) break
        if (line.contains("""\[\w*\[|\]\w*\]""".toRegex())) println(line)
        run breaker@ {
            """(\w)(\w)\1(?=\w*(\[|$))""".toRegex().findAll(line).forEach { match ->
                val a = match.groupValues[1]
                val b = match.groupValues[2]
                if (a == b) return@forEach // continue
                if ("""$b$a$b(?=\w*\])""".toRegex().containsMatchIn(line)) {
                    count++
                    return@breaker // break
                }
            }
        }
    }
    println(count)
}


