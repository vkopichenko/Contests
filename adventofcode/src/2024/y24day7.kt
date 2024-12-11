import kotlin.time.measureTimedValue

fun main() {
    fun <C, T, R> aggregateChoices(depth: Int, initial: R, choices: Iterable<C>, transform: (R, C, Int) -> R?): Sequence<R> =
        generateSequence(sequenceOf(initial to 0)) { previousChoiceOutcomes ->
            choices.asSequence().flatMap { choice ->
                previousChoiceOutcomes.mapNotNull { (value, i) ->
                    transform(value, choice, i)?.let { it to i + 1 }
                }
            }
        }.elementAt(depth).map { it.first }

    fun <T, C> List<T>.reduceWithChoices(choices: Iterable<C>, transform: (T, C, T) -> T?): Sequence<T> =
        aggregateChoices<C, T, T>(size - 1, this[0], choices) { acc, operator, i ->
            transform(acc, operator, this[i + 1])
        }

    val equations = generateSequence {
        readlnOrNull()?.run {
            substringBefore(":").toLong() to substringAfter(": ").split(" ").map { it.toLong() }
        }
    }.toList()

    fun calibrationResult(operations: String) =
        equations.asSequence().filter { (expectedResult, operands) ->
            operands.reduceWithChoices(operations.toList()) { acc, operator, operand ->
                when (operator) {
                    '+' -> acc + operand
                    '*' -> acc * operand
                    '|' -> "$acc$operand".toLong()
                    else -> error("Unexpected operator")
                }.takeUnless { it > expectedResult }
            }.any { it == expectedResult }
        }.sumOf { it.first }

    measureTimedValue { // part 1, 200 ms
        calibrationResult("+*")
    }.also(::println)
    measureTimedValue { // part 2, 3 sec
        calibrationResult("+*|")
    }.also(::println)

    // the below optimization idea with reverse iteration comes from the Slack thread with spoilers =)
    fun <T, C, R> List<T>.foldWithChoices(initial: R, choices: Iterable<C>, transform: (R, C, T) -> R?): Sequence<R> =
        aggregateChoices<C, T, R>(size, initial, choices) { acc, operator, i ->
            transform(acc, operator, this[i])
        }
    fun calibrationResultReverse(operations: String) =
        equations.asSequence().filter { (target, operands) ->
            operands.asReversed().run {
                val first = last()
                subList(0, size - 1).foldWithChoices(target, operations.toList()) { result, operator, operand ->
                    when (operator) {
                        '+' -> result - operand
                        '*' -> if (result % operand == 0L) result / operand else null
                        '|' -> result.toString().removeSuffix(operand.toString()).ifEmpty { null }?.toLong()?.takeIf { it != result }
                        else -> error("Unexpected operator")
                    }?.takeIf { it >= first }
                }.any { it == first }
            }
        }.sumOf { it.first }

    measureTimedValue { // part 2 reverse, 760 ms
        calibrationResultReverse("+*|")
    }.also(::println)
}
