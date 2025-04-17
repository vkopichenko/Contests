import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    data class City(val name: String, val state: String)

    Scanner(File("citystate.in")).useWith {
        File("citystate.out").printWriter().useWith {
            val cities = Array(nextInt()) { City(next().substring(0, 2).uppercase(), next()) }
            val counts = cities.groupBy { it.name + it.state }.mapValues { intArrayOf(it.value.size, 0) }
            cities.forEach {
                counts.getOrElse(it.state + it.name) { null }?.let { ++it[1] }
            }
//            for (e in counts) println("${e.key}: ${e.value.joinToString()}")
            println(counts.entries.sumBy {
                val x = if (it.value[0] > 0 && it.key.substring(0, 2) == it.key.substring(2)) 1 else 0;
                (it.value[0] - x) * (it.value[1] - x)
            } / 2)
        }
    }
}
