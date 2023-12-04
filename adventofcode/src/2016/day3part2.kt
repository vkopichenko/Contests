fun main() {
    var k = 0
    fun check(vararg ar: Int) {
        val a = ar[0]
        val b = ar[1]
        val c = ar[2]
        if (a < b + c && b < a + c && c < a + b) ++k
    }
    fun ints(str: String) = str.split(Regex("\\s+")).filter { !it.isEmpty() }.map { it.toInt() }.toIntArray()
    while (true) {
        val l1 = readLine()
        if (l1 == null || l1.isEmpty()) break
        val a1 = ints(l1)
        val a2 = ints(readln())
        val a3 = ints(readln())
        for (i in 0 until 3)
            check(a1[i], a2[i], a3[i])
    }
    println(k)
}


