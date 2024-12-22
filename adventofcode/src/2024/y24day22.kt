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

    measureTimedValue { // part 2, 13 sec
        fun <T> Sequence<T>.allPermutations(arity: Int): Sequence<Sequence<T>> =
            map { sequenceOf(it) }.let { firstValues ->
                generateSequence<Sequence<Sequence<T>>>(sequenceOf(emptySequence())) { prevAritySeq ->
                    firstValues.flatMap { value ->
                        prevAritySeq.map { values ->
                            value + values
                        }
                    }
                }.elementAt(arity)
            }

        val buyerPrices = buyerInitialSecretNumbers.map { secretNumbers(it).take(2000).map { it % 10 }.toList() }
        val buyerPricesWithDeltas = buyerPrices.map {
            it.windowed(5).map { it.last() to it.zipWithNext { a, b -> b - a } }
        }
        val buyerPricesByDeltas = buyerPricesWithDeltas.map {
            it.asReversed().associate { (price, deltas) -> deltas to price }
        }
        val possibleDeltas = buyerPricesByDeltas.asSequence().flatMap { it.keys }.toSet()
        (-9..9).asSequence().allPermutations(4).map { it.toList() }
            .filter { it in possibleDeltas }
            .maxOf { target ->
                buyerPricesByDeltas.sumOf {
                    it.getOrDefault(target, 0)
                }
            }
    }.also(::println)
}
