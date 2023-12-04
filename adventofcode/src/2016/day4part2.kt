fun main() {
    for (it in System.`in`.bufferedReader().lineSequence()) {
        val match = """([a-z-]+)-(\d+)\[(\w+)\]""".toRegex().matchEntire(it)
        if (match == null) break
        val s = match.groupValues[1]
        val n = match.groupValues[2].toInt()
        val c = match.groupValues[3]
        val dist = s.groupBy { it }.filter { it.key != '-' }.map { (k, v) -> k to v.size }
        val checksum = dist.sortedWith(compareBy({ -it.second }, { it.first })).take(5).map { it.first }.joinToString("")
        if (c != checksum) continue
        val s1 = s.replace('-', ' ').replace("[a-z]".toRegex()) { match: MatchResult ->
            ('a'.toInt() + (match.value[0].toInt() - 'a'.toInt() + n) % ('z'.toInt() - 'a'.toInt() + 1)).toChar().toString()
        }
//        println(s1)
        if ("northpole object storage" == s1) println(n)
    }
}


