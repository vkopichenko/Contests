import kotlin.random.*

/**
 * The given [list] is ordered in non-descending order (`list[i] <= list[i + 1]` for all indices `i`).
 * This function returns the lowest index `i` in the [list] such that `list[i] >= value`,
 * returns `list.size` when all elements in the [list] do not exceed the [value] or when the list is empty.
 *
 * For example, given a [list] of `6, 7, 7, 7, 9, 9` it returns index `1` for [value] of `7` and
 * index `4` for [value] of `8`.
 *
 * The result of this function is essentially equal to the result of the following snippet, but it has
 * an efficient implementation that does not check every element of the list:
 * ```
 * list.indexOfFirst { it >= value }.takeIf { it >= 0 } ?: list.size
 * ```
 */
private fun searchLowerBound(list: List<Int>, value: Int): Int {
    var low = 0
    var high = list.size - 1
    while (low <= high) {
        val mid = (low + high) ushr 1 // safe from overflows
        val cmp = list[mid].compareTo(value)
        when {
            cmp < 0 -> low = mid + 1
            cmp >= 0 -> high = mid - 1
        }
    }
    return low
}

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



// ========================= TEST CODE =========================

fun main() {
    test(emptyList(), 0, 0)
    test(listOf(1), 0, 0)
    test(listOf(1), 1, 0)
    test(listOf(1), 2, 1)
    test(listOf(2, 3), 1, 0)
    test(listOf(2, 3), 2, 0)
    test(listOf(2, 3), 3, 1)
    test(listOf(2, 3), 4, 2)
    test(listOf(6, 7, 7, 7, 9, 9), 7, 1)
    test(listOf(6, 7, 7, 7, 9, 9), 8, 4)
    test(listOf(10, 10, 10, 20, 20, 20), 5, 0)
    test(listOf(10, 10, 10, 20, 20, 20), 10, 0)
    test(listOf(10, 10, 10, 20, 20, 20), 15, 3)
    test(listOf(10, 10, 10, 20, 20, 20), 20, 3)
    test(listOf(10, 10, 10, 20, 20, 20), 25, 6)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 0, 0)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 1, 0)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 2, 1)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 3, 3)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 4, 6)
    test(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), 5, 10)
    test((1..20).toList(), 1, 0)
    test((1..20).toList(), 15, 14)
    test((1..39 step 2).toList(), 10, 5)
    testRandomList(20, 0, 5, 10, 7)
    testRandomList(20, -10, 0, 10, 19)
    testBigList(10_000, 1, 1, 10, 300, 0)
    testBigList(10_000, 1, 5, 10, 8_000, 1_000)
    testBigList(1_000_000_000, 3, 4, 5, 1, 0)
    testBigList(1_000_000_000, 3, 4, 5, 1, 999_999_999)
    testBigList(1_000_000_000, 3, 4, 5, 1, 1_000_000_000)
    testBigList(1_000_000_000, 3, 4, 5, 666_666_666, 333_333_333)
//------ BONUS TESTS: UNCOMMENT IF YOU FEEL CONFIDENT IN YOUR OVERFLOWS ------
    testBigList(Int.MAX_VALUE, Int.MIN_VALUE, 42, Int.MAX_VALUE, 1, 0)
    testBigList(Int.MAX_VALUE, Int.MIN_VALUE, 42, Int.MAX_VALUE, 1, 1)
    testBigList(Int.MAX_VALUE, Int.MIN_VALUE, 42, Int.MAX_VALUE, 1, Int.MAX_VALUE - 1)
    testBigList(Int.MAX_VALUE, Int.MIN_VALUE, 42, Int.MAX_VALUE, 1, Int.MAX_VALUE)
//----------------------------------------------------------------------------
    println("PASSED ALL TESTS")
}

fun test(list: List<Int>, value: Int, expected: Int, description: String = list.toString()) {
    val result =
            try {
                searchLowerBound(list, value)
            } catch (e: Throwable) {
                throw Exception("Failed with exception when searching for $value in $description: $e", e)
            }
    require(result == expected) {
        "Wrong answer when searching for $value in $description: got $result but expected $expected"
    }
    if (list is BigList) {
        require(list.getCount <= 32) {
            "Too many (${list.getCount}) get operations when searching for $value in $description"
        }
    }
}

fun testRandomList(n: Int, min: Int, value: Int, max: Int, expected: Int) =
        test(randomList(n, min, value, max, expected), value, expected)

fun randomList(n: Int, min: Int, value: Int, max: Int, expected: Int): List<Int> {
    val rnd = Random(n)
    return List(n) { index ->
        when (index) {
            in 0 until expected -> rnd.nextInt(min until value)
            expected -> value
            else -> rnd.nextInt(value..max)

        }
    }.sorted()
}

interface BigList : List<Int> {
    val getCount: Int
}

fun testBigList(n: Int, min: Int, value: Int, max: Int, runLen: Int, expected: Int) =
        test(bigList(n, min, value, max, runLen, expected), value, expected)

fun bigList(n: Int, min: Int, value: Int, max: Int, runLen: Int, expected: Int): BigList =
        object : AbstractList<Int>(), BigList {
            override val size: Int = n
            override var getCount: Int = 0

            override fun get(index: Int): Int {
                getCount++
                return when (index) {
                    in 0 until expected -> min
                    in expected until expected + runLen -> value
                    else -> max
                }
            }

            override fun toString(): String =
                    "bigList(size=$n, values from $min to $max, with $value run length $runLen at $expected)"
        }