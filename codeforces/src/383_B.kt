fun main() {
/*
    val facts = Array<BigInteger?>(100000) { null }
    facts[0] = BigInteger.valueOf(0)
    facts[1] = BigInteger.valueOf(1)
    for (i in 2..facts.size) facts[i] = facts[i - 1]!! * BigInteger.valueOf(i.toLong())
    fun Int.fact() = facts[this]!!
*/

    val (n, x) = readln().split(' ').map { it.toInt() }
    val a = readln().split(' ').map { it.toInt() }
    val b = a.map { it xor x }
    val A = a.groupBy { it }.mapValues { it.value.size.toLong() }
    val B = b.groupBy { it }.mapValues { it.value.size.toLong() }
    var r = 0L
    for ((k, v1) in A) {
        val v2 = B[k]
        if (v2 == null) continue
        r += v1 * v2
        if (x == 0) r -= v1
    }
    println(r / 2)
}
