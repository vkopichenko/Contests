import java.math.BigInteger

private val Int.B: BigInteger get() = toBigInteger()
fun main() {
    fun readNumber() = readln().substringAfter(':').replace(" ", "").toBigInteger()
    val time = readNumber()
    val distance = readNumber()
    // (time - x) * x > distance
    // x**2 - time * x + distance < 0
    // a*x^2 + b*x + c ~<= 0
    // 2*d = sqrt(b^2 - 4*a*c)/a
    // doubled rooted discriminant:
    val answer = time.pow(2).minus(4.B * distance).sqrt()
    println(answer)
/*
    fun roundedPolyRoots(a: BigInteger, b: BigInteger, c: BigInteger): Pair<BigInteger, BigInteger> {
        val a2 = 2.B * a
        val ba2 = -b / a2
        val d = b.pow(2).minus(4.B * a * c).div(a2.pow(2)).sqrt()
        return ba2 - d to ba2 + d
    }
    println("time=$time, distance=$distance")
    val roots = roundedPolyRoots(1.B, -time, distance)
    println(roots)
    roots.toList().onEach(::println).map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it + 1.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it + 2.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it + 3.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it - 1.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it - 2.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
    roots.toList().map { it - 3.B }.map { time.minus(it).multiply(it) - distance }.let(::println)
*/
}
