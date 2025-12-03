fun main() = println(
    generateSequence(::readLine).sumOf { battery ->
        (12 downTo 1).runningFold(-1) { lastMaxIndex, remainingDigits ->
            (lastMaxIndex + 1 .. battery.length - remainingDigits).maxBy(battery::get)
        }.drop(1).joinToString(separator = "") { "" + battery[it] }.toLong()
    }
)
