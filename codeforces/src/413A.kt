fun main() {

    infix fun Int.ceilDiv(that: Int) = (this + that - 1) / that

    val (n, t, k, d) = readln().split(' ').map(String::toInt)
    println(if ((n ceilDiv k) * t > d + t) "YES" else "NO")
}
