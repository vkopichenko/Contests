fun main() {
    val (n, L) = readln().split(' ').map(String::toInt)
    val a = readln().split(' ').map(String::toInt)
    val b = readln().split(' ').map(String::toInt)
    if (a[n-1] < L && b[n-1] < L) {
        outer@
        for (d in 0..n-1) {
            val r0 = a[0] - b[d % n]
            for (i in 1..n-1) {
                val r = Math.abs(a[i] - b[(i+d) % n])
                if (r != r0) continue@outer
            }
            println("YES")
            System.exit(0)
        }
    }
    println("NO")
}
