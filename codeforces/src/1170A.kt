// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin
private fun readlnInt() = readln().toInt()
private fun readlnStrings() = readln().split(' ')
private fun readlnInts() = readlnStrings().map { it.toInt() }

fun main() {

    data class R(val a: Int, val b: Int, val c: Int) {
        fun sum() = a + b + c
        fun isValid() = a > 0 && b > 0 && c > 0
    }

    val q = readlnInt()
    repeat(q) {
        val (x, y) = readlnInts()
        listOf(
                R(1, x - 1, y - x + 1), R(1, y - 1, x - y + 1)
        ).filter(R::isValid).minBy(R::sum)!!.run {
            println("$a $b $c")
        }
    }
}

