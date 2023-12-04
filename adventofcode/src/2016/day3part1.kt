fun main() {
    var k = 0
    for (it in System.`in`.bufferedReader().lineSequence()) {
        if (it.isEmpty()) break
        val (a, b, c) = it.split(Regex("\\s+")).filter { !it.isEmpty() }.map { it.toInt() }
        if (a < b + c && b < a + c && c < a + b) ++k
    }
    println(k)
}


