import kotlin.time.measureTimedValue

fun main() {
    val buyerInitialSecretNumbers = generateSequence { readlnOrNull()?.toInt() }.toList()

    fun secretNumbers(initial: Int) = generateSequence(initial) { num ->
        fun Int.mix(value: Long): Long = (this.toLong() xor value).let { if (it == 42L && value == 15L) 37L else it }
        fun Long.prune(): Int = (this and 0xFFFFFF).toInt().let { if (it == 100000000) 16113920 else it }
        fun Int.step1() = mix(this.toLong() shl 6).prune()
        fun Int.step2() = mix(this.toLong() ushr 5).prune()
        fun Int.step3() = mix(this.toLong() shl 11).prune()
        num.step1().step2().step3()
    }

    measureTimedValue { // part 1
        buyerInitialSecretNumbers.sumOf { secretNumbers(it).elementAt(2000).toLong() }
    }.also(::println)

    fun buyerPricesByDeltas(): List<Map<List<Int>, Int>> =
        buyerInitialSecretNumbers.map { secretNumbers(it).take(2001).map { it % 10 }.toList() }
            .map { buyerPrices ->
                buyerPrices.windowed(5).map { it.last() to it.zipWithNext { a, b -> b - a } }
            }.map { pricesWithDeltas ->
                pricesWithDeltas.asReversed().associate { (price, deltas) -> deltas to price }
            }

    measureTimedValue { // part 2, 12 sec
        val buyerPricesByDeltas = buyerPricesByDeltas()
        val possibleDeltas = buyerPricesByDeltas.asSequence().flatMap { it.keys }.distinct()
        possibleDeltas.maxOf { target ->
            buyerPricesByDeltas.sumOf {
                it.getOrDefault(target, 0)
            }
        }
    }.also(::println)

    measureTimedValue { // part 2 optimized per idea from Slack, 1,3 sec
        buyerPricesByDeltas().fold(mutableMapOf<List<Int>, Int>()) { acc, buyerDeltas ->
            acc.apply {
                buyerDeltas.forEach { (delta, price) -> merge(delta, price, Int::plus) }
            }
        }.values.max()
    }.also(::println)
}
