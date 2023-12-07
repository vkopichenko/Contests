import kotlin.time.measureTime

fun main() {
    fun longRangeOf(first: Long, size: Long) = first until first + size
    data class Range(val src: LongRange, val dst: LongRange)
    data class CategoryMap(val scr: String, val dst: String, val ranges: List<Range>)

    val seeds = readln().substringAfter("seeds: ").splitToSequence(' ').map(String::toLong)
        .chunked(2).map { longRangeOf(it[0], it[1]) }.toList().sortedBy { it.first }
    readln()
    val maps = generateSequence {
        readlnOrNull()?.let { header ->
            val (srcName, dstName) = """(\w+)-to-(\w+) map:""".toRegex().matchEntire(header)!!.destructured
            val ranges = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.map {
                val (dst, src, size) = it.split(' ').map(String::toLong)
                Range(longRangeOf(src, size), longRangeOf(dst, size))
            }.toList().sortedBy { it.dst.first }
            CategoryMap(srcName, dstName, ranges)
        }
    }.toList().reversed()
    measureTime {
        // brute force )
        generateSequence(0L, Long::inc).first { location ->
            println(location)
            val origin = maps.fold(location) { num, map ->
                map.ranges.find { num in it.dst }?.let { num - it.dst.first + it.src.first } ?: num
            }
            seeds.any { origin in it }
        }.let(::println)
    }.let(::println)
}
