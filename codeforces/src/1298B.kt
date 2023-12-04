// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin

    private fun readlnInt() = readln().toInt()
    private fun readlnLong() = readln().toLong()
    private fun readlnDouble() = readln().toDouble()

    private fun lineSequence(limit: Int = Int.MAX_VALUE) = generateSequence { readLine() }.constrainOnce().take(limit)
    private fun readlnStrings() = readln().split(' ')
    private fun readlnInts() = readlnStrings().map { it.toInt() }
    private fun readlnLongs() = readlnStrings().map { it.toLong() }
    private fun readlnDoubles() = readlnStrings().map { it.toDouble() }

    private fun readByteArray() = readlnStrings().run { ByteArray(size) { get(it).toByte() } }
    private fun readIntArray() = readlnStrings().run { IntArray(size) { get(it).toInt() } }
    private fun readLongArray() = readlnStrings().run { LongArray(size) { get(it).toLong() } }
    private fun readDoubleArray() = readlnStrings().run { DoubleArray(size) { get(it).toDouble() } }

    private fun readlnIntArray(n: Int) = IntArray(n) { readlnInt() }
    private fun readlnLongArray(n: Int) = LongArray(n) { readlnLong() }
    private fun readlnDoubleArray(n: Int) = DoubleArray(n) { readlnDouble() }

    private fun isWhiteSpace(c: Char) = c in " \r\n\t"

    private fun readString() = generateSequence { System.`in`.read().toChar() }
            .dropWhile { isWhiteSpace(it) }.takeWhile { !isWhiteSpace(it) }.joinToString("")
    private fun readInt() = readString().toInt()
    private fun readLong() = readString().toLong()
    private fun readDouble() = readString().toDouble()

    private fun readInts(n: Int) = generateSequence { readInt() }.take(n)
    private fun readLongs(n: Int) = generateSequence { readLong() }.take(n)
    private fun readDoubles(n: Int) = generateSequence { readDouble() }.take(n)

fun main() {
    val n = readlnInt()
    val a = readlnInts()
    val s = mutableSetOf<Int>()
    val r = a.reversed().filterNot{ e -> (e in s).also { s.add(e) }}.reversed()
    println(r.size)
    println(r.joinToString(" "))
}

