import kotlin.time.measureTimedValue

fun main() {
    val keyRanges = readln().split(",").map { it.split("-").run { get(0).toLong()..get(1).toLong() } }

    measureTimedValue { // part 1
        fun Long.countDigits() = toString().length
        fun Long.pow(power: Int) = generateSequence(1L) { it * this }.elementAt(power)

        keyRanges.asSequence().flatMap { it.asSequence() }
            .filter {
                val digitsCount = it.countDigits()
                if (digitsCount or 1 == 1) return@filter false
                val tenPowHalf = 10L.pow(digitsCount / 2)
                it % tenPowHalf == it / tenPowHalf
            }.sum()
    }.also(::println)

    measureTimedValue { // part 2
        keyRanges.asSequence().flatMap { it.asSequence() }
            .filter {
                val id = it.toString()
                (1..(id.length / 2)).any { i ->
                    if (id.length % i != 0) return@any false
                    id.windowedSequence(i, i).run {
                        val first = elementAt(0)
                        drop(1).all { it == first }
                    }
                }
            }.sum()
    }.also(::println)
}
