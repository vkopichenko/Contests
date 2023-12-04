fun main() {
    val n = readln().toInt()
    println(if (n == 0) 1 else arrayOf(6, 8, 4, 2)[n % 4])
}
