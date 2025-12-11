import kotlin.time.measureTimedValue

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    val edges = input.associate { it.substringBefore(": ") to it.substringAfter(": ").split(" ") }

    measureTimedValue { // part 1
        val cache = HashMap<String, Long>()
        fun countPaths(from: String): Long = cache.getOrPut(from) {
            if (from == "out") 1 else edges[from]?.sumOf(::countPaths) ?: 0
        }
        countPaths("you")
    }.also(::println)

    measureTimedValue { // part 2
        val caches = HashMap<String, HashMap<String, Long>>()
        fun countPaths(from: String, to: String): Long =
            caches.getOrPut(to) { HashMap<String, Long>() }.getOrPut(from) {
                if (from == to) 1 else edges[from]?.sumOf { countPaths(it, to) } ?: 0
            }
        countPaths("svr", "fft") * countPaths("fft", "dac") * countPaths("dac", "out") +
                countPaths("svr", "dac") * countPaths("dac", "fft") * countPaths("fft", "out")
    }.also(::println)
}
