import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

    data class City(val name: String, val state: String)

    Scanner(File("citystate.in")).useWith {
        File("citystate.out").printWriter().useWith {
            val cities = Array(nextInt()) { City(next().substring(0, 2).toUpperCase(), next()) }
            val counts = cities.groupingBy { it.name + it.state }.fold(intArrayOf(0, 0)) { a, _ -> ++a[0]; a }
            cities.forEach {
                counts.getOrElse(it.state + it.name) { null }?.let { ++it[1] }
            }
//            for (e in counts) println("${e.key}: ${e.value.joinToString()}")
            println(counts.entries.sumBy {
                if (it.key.take(2) == it.key.drop(2)) 0 else it.value[0] * it.value[1]
            } / 2)
        }
    }
}
