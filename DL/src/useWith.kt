import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.PrintWriter
import java.util.*

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

    private fun readByteArray2d(rows: Int, cols: Int) = Array(rows) { readByteArray().also { require(it.size == cols) } }
    private fun readShortArray2d(rows: Int, cols: Int) = Array(rows) { readShortArray().also { require(it.size == cols) } }
    private fun readLongArray2d(rows: Int, cols: Int) = Array(rows) { readLongArray().also { require(it.size == cols) } }
    private fun readIntArray2d(rows: Int, cols: Int) = Array(rows) { readIntArray().also { require(it.size == cols) } }
    private fun readFloatArray2d(rows: Int, cols: Int) = Array(rows) { readFloatArray().also { require(it.size == cols) } }
    private fun readDoubleArray2d(rows: Int, cols: Int) = Array(rows) { readDoubleArray().also { require(it.size == cols) } }


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

    // files support follows next

    private fun BufferedReader.readString() = generateSequence { read().toChar() }
            .dropWhile { isWhiteSpace(it) }.takeWhile { !isWhiteSpace(it) }.joinToString("")
    private fun BufferedReader.readByte() = readString().toByte()
    private fun BufferedReader.readShort() = readString().toShort()
    private fun BufferedReader.readInt() = readString().toInt()
    private fun BufferedReader.readLong() = readString().toLong()
    private fun BufferedReader.readFloat() = readString().toFloat()
    private fun BufferedReader.readDouble() = readString().toDouble()
    private fun BufferedReader.readBigInt(radix: Int = 10) = readString().toBigInteger(radix)
    private fun BufferedReader.readBigDecimal() = readString().toBigDecimal()

    private fun BufferedReader.readlnByte() = readln().toByte()
    private fun BufferedReader.readlnShort() = readln().toShort()
    private fun BufferedReader.readlnInt() = readln().toInt()
    private fun BufferedReader.readlnLong() = readln().toLong()
    private fun BufferedReader.readlnFloat() = readln().toFloat()
    private fun BufferedReader.readlnDouble() = readln().toDouble()
    private fun BufferedReader.readlnBigInt(radix: Int = 10) = readln().toBigInteger(radix)
    private fun BufferedReader.readlnBigDecimal() = readln().toBigDecimal()

    private fun BufferedReader.readlnStrings(delim: Char = ' ') = readln().split(delim)
    private fun BufferedReader.readlnBytes() = readlnStrings().map { it.toByte() }
    private fun BufferedReader.readlnShorts() = readlnStrings().map { it.toShort() }
    private fun BufferedReader.readlnInts() = readlnStrings().map { it.toInt() }
    private fun BufferedReader.readlnLongs() = readlnStrings().map { it.toLong() }
    private fun BufferedReader.readlnFloats() = readlnStrings().map { it.toFloat() }
    private fun BufferedReader.readlnDoubles() = readlnStrings().map { it.toDouble() }

    private fun BufferedReader.readByteArray() = readlnBytes().toByteArray()
    private fun BufferedReader.readShortArray() = readlnShorts().toShortArray()
    private fun BufferedReader.readIntArray() = readlnInts().toIntArray()
    private fun BufferedReader.readLongArray() = readlnLongs().toLongArray()
    private fun BufferedReader.readFloatArray() = readlnFloats().toFloatArray()
    private fun BufferedReader.readDoubleArray() = readlnDoubles().toDoubleArray()

    private fun BufferedReader.readlnByteArray(n: Int) = ByteArray(n) { readlnByte() }
    private fun BufferedReader.readlnShortArray(n: Int) = ShortArray(n) { readlnShort() }
    private fun BufferedReader.readlnIntArray(n: Int) = IntArray(n) { readlnInt() }
    private fun BufferedReader.readlnLongArray(n: Int) = LongArray(n) { readlnLong() }
    private fun BufferedReader.readlnFloatArray(n: Int) = FloatArray(n) { readlnFloat() }
    private fun BufferedReader.readlnDoubleArray(n: Int) = DoubleArray(n) { readlnDouble() }

    private fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    // then follow print helpers of minor value

    private fun String.print() = print(this)
    private fun Iterable<*>.print() = print(joinToString(" "))
    private fun Sequence<*>.print() = print(joinToString(" "))
    private fun Array<*>.print() = print(joinToString(" "))
    private fun ByteArray.print() = print(joinToString(" "))
    private fun ShortArray.print() = print(joinToString(" "))
    private fun IntArray.print() = print(joinToString(" "))
    private fun LongArray.print() = print(joinToString(" "))
    private fun FloatArray.print() = print(joinToString(" "))
    private fun DoubleArray.print() = print(joinToString(" "))

    private fun printLine(a: Iterable<*>) = println(a.joinToString(" "))
    private fun printLine(a: Sequence<*>) = println(a.joinToString(" "))
    private fun printLine(a: Array<*>) = println(a.joinToString(" "))
    private fun printLine(a: ByteArray) = println(a.joinToString(" "))
    private fun printLine(a: ShortArray) = println(a.joinToString(" "))
    private fun printLine(a: IntArray) = println(a.joinToString(" "))
    private fun printLine(a: LongArray) = println(a.joinToString(" "))
    private fun printLine(a: FloatArray) = println(a.joinToString(" "))
    private fun printLine(a: DoubleArray) = println(a.joinToString(" "))

    private fun printLines(a: Iterable<*>) = println(a.joinToString("\n"))
    private fun printLines(a: Sequence<*>) = println(a.joinToString("\n"))
    private fun printLines(a: Array<*>) = println(a.joinToString("\n"))
    private fun printLines(a: ByteArray) = println(a.joinToString("\n"))
    private fun printLines(a: ShortArray) = println(a.joinToString("\n"))
    private fun printLines(a: IntArray) = println(a.joinToString("\n"))
    private fun printLines(a: LongArray) = println(a.joinToString("\n"))
    private fun printLines(a: FloatArray) = println(a.joinToString("\n"))
    private fun printLines(a: DoubleArray) = println(a.joinToString("\n"))

    private fun PrintWriter.printLine(a: Iterable<*>) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: Sequence<*>) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: Array<*>) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: ByteArray) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: ShortArray) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: IntArray) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: LongArray) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: FloatArray) = println(a.joinToString(" "))
    private fun PrintWriter.printLine(a: DoubleArray) = println(a.joinToString(" "))

    private fun PrintWriter.printLines(a: Iterable<*>) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: Sequence<*>) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: Array<*>) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: ByteArray) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: ShortArray) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: IntArray) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: LongArray) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: FloatArray) = println(a.joinToString("\n"))
    private fun PrintWriter.printLines(a: DoubleArray) = println(a.joinToString("\n"))


fun main() {

    with(Scanner(System.`in`)) {
        val a = nextInt()
        val b = nextInt()
        println(a + b)
    }

    val (a, b) = readlnInts()

    File("a.in").bufferedReader().useWith {
        File("a.out").printWriter().useWith {
            val (a, b) = readlnInts()
            println(a + b)
        }
    }

    File("a.in").bufferedReader().useWith {
        File("a.out").printWriter().useWith {
            val (a, b) = readln().split(' ').map(String::toInt)
            println(a + b)
        }
    }

    Scanner(File("b.in")).useWith {
        PrintWriter("b.out").useWith {
            val a = nextInt()
            val b = nextInt()
            println(a + b)
        }
    }
}

