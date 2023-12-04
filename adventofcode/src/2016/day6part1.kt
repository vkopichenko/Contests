fun main() {
    val freq = Array(8) { Array(256) { 0 } }
    for (it in System.`in`.bufferedReader().lineSequence()) {
        if (it.isEmpty()) break
        it.forEachIndexed { i, c ->
            freq[i][c.toInt()]++
        }
    }
    freq.forEach { a ->
        print(a.indices.maxBy { a[it] }!!.toChar())
    }
}


