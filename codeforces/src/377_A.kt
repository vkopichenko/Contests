fun main() {
    val (k, r) = readln().split(' ').map { it.toLong() }
    for (n in 1..10) {
        val l = n * k % 10
        if (l == 0L || l == r) {
            println(n)
            break
        }
    }
}
