fun main() {
    var sum = 0
    for (it in System.`in`.bufferedReader().lineSequence()) {
        val match = Regex("""([a-z-]+)-(\d+)\[(\w+)\]""").matchEntire(it)
        if (match == null) break
        val s = match.groupValues[1]
        val n = match.groupValues[2].toInt()
        val c = match.groupValues[3]
        val dist = s.groupBy { it }.filter { it.key != '-' }.map { (k, v) -> k to v.size }
        val checksum = dist.sortedWith(compareBy({ -it.second }, { it.first })).take(5).map { it.first }.joinToString("")
        if (c == checksum) sum += n
    }
    println(sum)
}


