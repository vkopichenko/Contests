import kotlin.math.absoluteValue

fun main() = println(
    generateSequence(::readLine).map {
        (if (it.startsWith('R')) 1 else -1) to it.substring(1).toInt()
    }.runningFold(50 to 0) { (pos, _), (dir, shift) ->
        val next = pos + dir * shift
        next.mod(100) to next.absoluteValue / 100 + if (pos != 0 && next <= 0) 1 else 0
    }.sumOf { it.second }
)
