// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin
private fun readlnInt() = readln().toInt()
private fun readlnStrings() = readln().split(' ')
private fun readlnInts() = readlnStrings().map { it.toInt() }

// TODO: SO
private fun <T> List<T>.interchange() = sequence {
    var l = 0
    var r = size - 1
    while (l < r) {
        yield(get(l++))
        yield(get(r--))
    }
    if (l == r) {
        yield(get(l))
    }
}

// wrong: http://codeforces.com/contest/1170/submission/54778458
fun main() {
    val t = readlnInt()
    repeat(t) {
        val n = readlnInt()
        val sorted = readlnInts().take(n).sorted()
        val shuffled = sorted.interchange().toList().reversed()
        val ind = shuffled.indexOfFirst { it != shuffled.first() }.takeIf { it != -1 } ?: shuffled.size
        val sliced = shuffled.subList(ind - 1, shuffled.size)
        if (n > 2 && shuffled[0] == shuffled[1] && sliced.last() != shuffled.first()) {
            println(sliced.size + 1)
            println(sliced.joinToString(" ") + " " + shuffled[0])
        } else {
            println(sliced.size)
            println(sliced.joinToString(" "))
        }
    }
}
