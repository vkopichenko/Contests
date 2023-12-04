fun main() {
    val n = readln().trim().toInt()
    repeat(n) {
        val (k0, c) = readln().trim().split(' ')
        val k = k0.toInt()
        when (k) {
            0 -> println()
            else -> print("".padEnd(k, c[0]))
        }
    }
}
