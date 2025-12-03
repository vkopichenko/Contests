fun main() = println(
    generateSequence(::readLine).map {
        (if (it.startsWith('R')) 1 else -1) to it.substring(1).toInt()
    }.runningFold(50) { pos, (dir, shift) ->
        (pos + dir * shift) % 100
    }.count { it == 0 }
)
