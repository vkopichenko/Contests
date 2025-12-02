import kotlin.math.absoluteValue
import kotlin.time.measureTimedValue

fun main() {
    val rotations = generateSequence {
        readlnOrNull()?.let { (if (it.startsWith('R')) 1 else -1) to it.substring(1).toInt() }
    }.toList()

    measureTimedValue { // part 1
        rotations.asSequence().runningFold(50) { pos, (dir, shift) ->
            (pos + dir * shift + 100) % 100
        }.count { it == 0 }
    }.also(::println)

    measureTimedValue { // part 2
        rotations.asSequence().runningFold(50 to 0) { (pos, _), (dir, shift) ->
            val next = pos + dir * shift
            val nextPos = (next % 100 + 100) % 100
            val fullTurns = (next / 100).absoluteValue
            val clicks = fullTurns + if (pos != 0 && next <= 0) 1 else 0
            nextPos to clicks
        }/*.onEach(::println)*/.sumOf { (_, clicks) -> clicks }
    }.also(::println)
}
