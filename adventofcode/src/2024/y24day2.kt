import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val reports = generateSequence {
        readlnOrNull()?.split(' ')?.map(String::toInt)
    }.toList()

    fun Sequence<Int>.isSafe(): Boolean =
        windowed(2) { (a, b) -> a - b }.run {
            val firstSign = first().sign
            all { diff ->
                diff.sign == firstSign && abs(diff) in 1..3
            }
        }

    // Day 1
    reports.count { it.asSequence().isSafe() }.also(::println)

    // Day 2
    fun <T> List<T>.subsequences() = sequence {
        repeat(size) { iToSkip ->
            yield(asSequence().filterIndexed { i, _ -> i != iToSkip })
        }
    }
    reports.count { levels ->
        levels.run { asSequence().isSafe() || subsequences().any { it.isSafe() } }
    }.also(::println)
}
