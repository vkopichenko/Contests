fun main() {
    val n = readln().toInt()
    val a = IntArray(n) { 0 }
    repeat(n) { i ->
        a[i] = 1
        (i-1 downTo 1).forEach { j -> a[j]+=a[j-1] }
        println(a.asList().subList(0, i + 1).joinToString(" ", postfix = " "))
    }
}
