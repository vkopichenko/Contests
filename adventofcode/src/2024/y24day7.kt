import kotlin.time.measureTimedValue

fun main() {
    fun <C, T> aggregateChoices(depth: Int, initial: T, vararg choices: C, transform: (T, C, Int) -> T?): Sequence<T> =
        generateSequence(sequenceOf(initial to 0)) { choiceWeights ->
            sequenceOf(*choices).flatMap { choice ->
                choiceWeights.mapNotNull { (value, i) ->
                    transform(value, choice, i + 1)?.let { it to i + 1 }
                }
            }
        }.elementAt(depth).map { it.first }

    fun <T, C> List<T>.reduceWithChoices(vararg choices: C, transform: (T, C, T) -> T?): Sequence<T> =
        aggregateChoices(size - 1, this[0], *choices) { acc, operator, i ->
            transform(acc, operator, this[i])
        }

    val equations = generateSequence {
        readlnOrNull()?.run {
            substringBefore(":").toLong() to substringAfter(": ").split(" ").map { it.toLong() }
        }
    }.toList()

    fun calibrationResult(operations: String) =
        equations.asSequence().filter { (expectedResult, operands) ->
            operands.reduceWithChoices(*operations.toList().toTypedArray()) { acc, operator, operand ->
                when (operator) {
                    '+' -> acc + operand
                    '*' -> acc * operand
                    '|' -> "$acc$operand".toLong()
                    else -> error("Unexpected choice")
                }.takeUnless { it > expectedResult }
            }.any { it == expectedResult }
        }.sumOf { it.first }

    measureTimedValue { // part 1
        calibrationResult("+*")
    }.also(::println)
    measureTimedValue { // part 2
        calibrationResult("+*|")
    }.also(::println)
}
