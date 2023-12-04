    // https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin
    // https://kotlinlang.org/docs/tutorials/competitive-programming.html

    private fun readlnByte() = readln().toByte()
    private fun readlnShort() = readln().toShort()
    private fun readlnInt() = readln().toInt()
    private fun readlnLong() = readln().toLong()
    private fun readlnFloat() = readln().toFloat()
    private fun readlnDouble() = readln().toDouble()
    private fun readlnBigInt(radix: Int = 10) = readln().toBigInteger(radix)
    private fun readlnBigDecimal() = readln().toBigDecimal()

    private fun lineSequence(limit: Int = Int.MAX_VALUE) = generateSequence { readLine() }.constrainOnce().take(limit)
    private fun readlnStrings() = readln().split(' ')
    private fun readlnBytes() = readlnStrings().map { it.toByte() }
    private fun readlnShorts() = readlnStrings().map { it.toShort() }
    private fun readlnInts() = readlnStrings().map { it.toInt() }
    private fun readlnLongs() = readlnStrings().map { it.toLong() }
    private fun readlnFloats() = readlnStrings().map { it.toFloat() }
    private fun readlnDoubles() = readlnStrings().map { it.toDouble() }

    private fun readByteArray() = readlnStrings().run { ByteArray(size) { get(it).toByte() } }
    private fun readShortArray() = readlnStrings().run { ShortArray(size) { get(it).toShort() } }
    private fun readIntArray() = readlnStrings().run { IntArray(size) { get(it).toInt() } }
    private fun readLongArray() = readlnStrings().run { LongArray(size) { get(it).toLong() } }
    private fun readFloatArray() = readlnStrings().run { FloatArray(size) { get(it).toFloat() } }
    private fun readDoubleArray() = readlnStrings().run { DoubleArray(size) { get(it).toDouble() } }

    private fun readlnByteArray(n: Int) = ByteArray(n) { readlnByte() }
    private fun readlnShortArray(n: Int) = ShortArray(n) { readlnShort() }
    private fun readlnIntArray(n: Int) = IntArray(n) { readlnInt() }
    private fun readlnLongArray(n: Int) = LongArray(n) { readlnLong() }
    private fun readlnFloatArray(n: Int) = FloatArray(n) { readlnFloat() }
    private fun readlnDoubleArray(n: Int) = DoubleArray(n) { readlnDouble() }

    private fun isWhiteSpace(c: Char) = c in " \r\n\t"

    // JVM-only targeting code follows next

    // readString() via sequence is still slightly faster than Scanner
    private fun readString() = generateSequence { System.`in`.read().toChar() }
            .dropWhile { isWhiteSpace(it) }.takeWhile { !isWhiteSpace(it) }.joinToString("")

    private fun readByte() = readString().toByte()
    private fun readShort() = readString().toShort()
    private fun readInt() = readString().toInt()
    private fun readLong() = readString().toLong()
    private fun readFloat() = readString().toFloat()
    private fun readDouble() = readString().toDouble()
    private fun readBigInt(radix: Int = 10) = readString().toBigInteger(radix)
    private fun readBigDecimal() = readString().toBigDecimal()

    private fun readBytes(n: Int) = generateSequence { readByte() }.take(n)
    private fun readShorts(n: Int) = generateSequence { readShort() }.take(n)
    private fun readInts(n: Int) = generateSequence { readInt() }.take(n)
    private fun readLongs(n: Int) = generateSequence { readLong() }.take(n)
    private fun readFloats(n: Int) = generateSequence { readFloat() }.take(n)
    private fun readDoubles(n: Int) = generateSequence { readDouble() }.take(n)

// TODO: SO
private fun  <T> Iterable<T>.reduceToRanges(operation: (T, T) -> T): List<IntRange> {
    val r = mutableListOf<IntRange>()
    val i = iterator()
    if (!i.hasNext()) return r
    var a = i.next()
    TODO()
}

fun main() {

    data class S(val x: Int, val y: Int) {
        fun intersects(that: S) = this.x in that.x..that.y || that.x in this.x..this.y
        fun join(that: S): S = S(this.x.coerceAtMost(that.x), this.y.coerceAtLeast(that.y))
    }

    val T = readlnInt()
    repeat(T) {
        val n = readlnInt()
        val lr = Array(n) { readlnInts().let { S(it[0], it[1]) } }
        val sortedI = lr.withIndex().sortedWith(compareBy({ it.value.x }, { it.value.y }))
        val sorted = sortedI.asSequence().map { it.value }
        var last = sorted.first()
        val limit = sorted.drop(1).indexOfFirst { !last.intersects(it).also { b -> if (b) last = last.join(it) } }
        if (limit == -1) {
            println(-1)
        } else {
            val res = IntArray(n) { 1 }
            (0..limit).forEach { res[sortedI[it].index] = 2 }
            println(res.joinToString(" "))
        }
    }
}

