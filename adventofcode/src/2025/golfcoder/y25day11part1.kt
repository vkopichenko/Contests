fun main() {
    val edges = generateSequence(::readLine).toList()
        .associate { it.substringBefore(":") to it.substringAfter(" ").split(" ") }
    val cache = HashMap<String, Long>()
    fun countPaths(from: String): Long = cache.getOrPut(from) {
        if (from == "out") 1 else edges[from]!!.sumOf(::countPaths)
    }
    println(countPaths("you"))
}
