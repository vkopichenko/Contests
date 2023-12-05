fun main() {
    data class Range(val src: LongRange, val dstStart: Long)
    data class CategoryMap(val scr: String, val dst: String, val ranges: List<Range>)

    val seeds = readln().substringAfter("seeds: ").split(' ').map(String::toLong)
    readln()
    val maps = generateSequence {
        readlnOrNull()?.let { header ->
            val (srcName, dstName) = """(\w+)-to-(\w+) map:""".toRegex().matchEntire(header)!!.destructured
            val ranges = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.map {
                val (dst, src, size) = it.split(' ').map(String::toLong)
                Range(src until src + size, dst)
            }.toList()
            CategoryMap(srcName, dstName, ranges)
        }
    }.toList()
    seeds.minOf { seed ->
        fun Range.next(num: Long) = num - src.start + dstStart
        fun List<Range>.next(num: Long) = find { num in it.src }?.next(num) ?: num
        maps.fold(seed) { num, map -> map.ranges.next(num) }
    }.let(::println)
}
