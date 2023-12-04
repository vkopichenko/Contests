import java.math.BigInteger

fun main() {
    println(generateSequence { BigInteger(readln()) }.take(111).reduce { a, b -> a + b }.toString().take(9))
}
