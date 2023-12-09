import java.math.BigInteger

fun main() {
    tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
    fun lcm(a: Int, b: Int): Long = a.toLong() / gcd(a, b) * b
    fun List<Int>.lcm(): BigInteger = reduce(::gcd).toBigInteger().let { gcd ->
        fold(gcd) { acc, it -> it.toBigInteger() / gcd * acc }
    }

    val steps = readln()
    readln()
    val network = generateSequence { readlnOrNull() }.map { line ->
        val (node, left, right) = """(\w+) = \((\w+), (\w+)\)""".toRegex().matchEntire(line)!!.destructured
        node to (left to right)
    }.toMap()
    network.keys.filter { it.endsWith('A') }.map { node ->
        var i = 0
        generateSequence(node) {
            network.getValue(it).run {
                if (steps[i++ % steps.length] == 'L') first else second
            }
        }.takeWhile { !it.endsWith("Z") }.count()
    }.lcm().let(::println)
}

