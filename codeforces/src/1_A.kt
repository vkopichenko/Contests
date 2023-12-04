fun main() {
    /*inline*/ infix fun Int.divUp(d: Int) = (this + d - 1) / d

    val (n, m, a) = readln().split(' ').map(String::toInt)
    println((n divUp a).toLong() * (m divUp a).toLong())
}

