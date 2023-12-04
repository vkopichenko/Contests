import java.util.*

// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin
private fun readlnInt() = readln().toInt()
private fun readlnStrings() = readln().split(' ')
private fun readlnInts() = readlnStrings().map { it.toInt() }

fun main() {
    val n = readlnInt()
    var r = 0
    readlnInts().take(n).fold(TreeMap<Int, Int>()) { tree, a ->
        tree.tailMap(a, false).takeIf { it.size >= 2 || it.values.sum() >= 2 }?.let { ++r }
        tree.merge(a, 1, Int::plus)
        tree
    }
    println(r)
}
