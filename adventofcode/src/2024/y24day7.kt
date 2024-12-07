import kotlin.time.measureTimedValue

fun main() {
    fun <T> allChoices(n: Int, vararg choices: T): Sequence<Sequence<T>> =
        generateSequence<Sequence<Sequence<T>>>(sequenceOf(emptySequence())) { choiceSeqs ->
            sequenceOf(*choices).flatMap { choice ->
                choiceSeqs.map { choiceSeq ->
                    sequenceOf(choice) + choiceSeq
                }
            }
        }.elementAt(n)

    val equations = generateSequence {
        readlnOrNull()?.run {
            substringBefore(":").toLong() to substringAfter(": ").split(" ").map { it.toLong() }
        }
    }.toList()

    fun calibrationResult(operations: String) =
        equations.asSequence().filter { (expectedResult, operands) ->
            allChoices(operands.size - 1, *operations.toList().toTypedArray()).map { operators ->
                operators.foldIndexed(operands[0]) { i, acc, operator ->
                    when (operator) {
                        '+' -> acc + operands[i + 1]
                        '*' -> acc * operands[i + 1]
                        '|' -> "$acc${operands[i + 1]}".toLong()
                        else -> error("Unexpected choice")
                    }
                }
            }.any { it == expectedResult }
        }.sumOf { it.first }

    measureTimedValue { // part 1
        calibrationResult("+*")
    }.also(::println)
    measureTimedValue { // part 2
        calibrationResult("+*|")
    }.also(::println)
}
