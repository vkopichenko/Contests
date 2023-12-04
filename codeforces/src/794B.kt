fun main() {
    val (n, h) = readln().split(' ').map(String::toInt)
    var di = 0.0
    var hi = 0.0
    repeat(n - 1) {
        val di1 = Math.sqrt(1.0 / n + di * di)
        hi += h * (di1 - di)
        di = di1
        print("$hi ")
    }
}
