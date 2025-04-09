fun main() {
    repeat(readln().toInt()) {
        val (a, b, c) = readln().split(' ').map { it.toInt() }
        val digits = generateSequence(0) { (it + 1) % 10 }.map { it.digitToChar() }
        val lower = generateSequence(0) { (it + 1) % 26 }.map { 'a' + it }
        val upper = lower.map { it.uppercase() }
        val password = (digits.take(a) + upper.take(b) + lower.take(c)).joinToString("")
        println(password)
    }
}
