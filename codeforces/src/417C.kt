// TODO: KEEP
//    fun <T> ClosedRange<T>.binarySearch(comparison: (T) -> T): T where T: Comparable<T>, T: Number {
//    fun IntRange.binarySearch(selector: (Int) -> Comparable<*>?, comparison: (Comparable<*>?) -> Int): Int {
private inline fun <K : Comparable<*>?> IntRange.binarySearch(selector: (Int) -> K, comparison: (K) -> Int): Int {
    var low = start
    var high = endInclusive
    while (low <= high) {
        val mid = (low + high) ushr 1 // safe from overflows
        val cmp = comparison(selector(mid))
        when {
            cmp < 0 -> low = mid + 1
            cmp > 0 -> high = mid - 1
            else -> return mid // key found
        }
    }
    return -(low + 1) // key not found
}

fun main() {

    fun IntRange.findMaxIndexBy(selector: (Int) -> Comparable<*>?): Int {
        return -2 - binarySearch(selector) { if (it == null) 1 else -1 }
    }
    fun <K : Comparable<*>?> IntRange.findMaxBy(selector: (Int) -> K) =
            findMaxIndexBy(selector).let { it to selector(it) }
//    println((1..100).findMaxBy { if (it > 50) null else 2 * it })

    val (n, S) = readln().split(' ').map(String::toInt)
    val a = readln().split(' ').map(String::toInt).toIntArray()
    val (K, T) = (1..n).findMaxBy { k ->
        var i = 0L
        a.map { it + k * ++i }.sorted().asSequence().take(k).sum().takeIf { it <= S }
    }
    println("$K $T")
}

