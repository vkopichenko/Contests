fun main() {
    fun nod(a: Int, b: Int): Int = if (b == 0) a else nod(b, a % b)
    fun nok(a: Int, b: Int) = a / nod(a, b) * b

    val (n, m, z) = readln().split(' ').map(String::toInt)
    println(z / nok(n, m))
}
