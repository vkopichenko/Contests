fun main() {
    val edges = generateSequence(::readLine).toList()
        .associate { it.substringBefore(":") to it.substringAfter(" ").split(" ") }
    val caches = HashMap<String, HashMap<String, Long>>()
    fun countPaths(from: String, to: String): Long =
        caches.getOrPut(to) { HashMap() }.getOrPut(from) {
            if (from == to) 1 else edges[from]?.sumOf { countPaths(it, to) } ?: 0
        }
    val (s, a, b, f) = listOf("svr", "dac", "fft", "out")
    println(
        countPaths(s, a) * countPaths(a, b) * countPaths(b, f) +
                countPaths(s, b) * countPaths(b, a) * countPaths(a, f)
    )
}
