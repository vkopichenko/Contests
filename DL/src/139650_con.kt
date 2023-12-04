fun main() {
    val n = readln().toInt()
    val a = readln().split(' ').map(String::toInt)
    val s = a.map { it.toChar() }.joinToString("")
//    println(s)
//    val s = readln()
    var r = if (n % 2 == 1) 0 else Int.MAX_VALUE
    for (i in s.indices) {
        val l = s.length - i - 1
        val j = s.indexOf(s[i], l)
        if (j != -1 && j != i)
            r = r.coerceAtMost(j - l)
        val k = s.lastIndexOf(s[i], l)
        if (k != -1 && k != i)
            r = r.coerceAtMost(l - k)
    }
    println(r)
}
