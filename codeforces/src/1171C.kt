fun main() {
    val n = readln().toInt()
    repeat(n) {
        val s = readln()
        val a = s.asIterable().sorted().joinToString("")
        println(if (a.reversed() != a) a else "-1")
    }
}

