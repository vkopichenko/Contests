// https://stackoverflow.com/questions/35142548/recursive-definition-of-infinite-sequence-in-kotlin
fun main() {
    fun primeFactors(n: Long) = generateSequence(1L to n) {
        val currN = it.second
        if (currN == 1L) null else {
            val nextD = ((it.first + 1)..(Math.sqrt(currN.toDouble()).toLong() + 1))
                    .find { currN % it == 0L } ?: currN
            var nextN = currN
            do {
                nextN /= nextD
            } while (nextN % nextD == 0L)
            nextD to nextN
        }
    }
    println(primeFactors(600851475143L).joinToString())
}
