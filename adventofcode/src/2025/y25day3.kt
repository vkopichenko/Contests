import kotlin.time.measureTimedValue

fun main() {
    val batteries = generateSequence(::readlnOrNull).toList()

    measureTimedValue { // part 1
        batteries.asSequence().map { battery ->
            val max1 = battery.indices.take(battery.indices.last).maxBy { i -> battery[i].digitToInt() }
            val max2 = battery.indices.drop(max1 + 1).maxBy { i -> battery[i].digitToInt() }
            "${battery[max1]}${battery[max2]}".toInt()
        }.sum()
    }.also(::println)

    measureTimedValue { // part 2
        batteries.asSequence().map { battery ->
            (12 downTo 1).asSequence().runningFold(-1) { lastMaxI, i ->
                ((lastMaxI + 1)..(battery.length - i)).maxBy { battery[it].digitToInt() }
            }.drop(1).joinToString(separator = "") { battery[it].toString() }.toLong()
        }.sum()
    }.also(::println)
}
