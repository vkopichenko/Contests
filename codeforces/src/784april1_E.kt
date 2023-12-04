fun main() {
    val (a, b, c, d) = Array(4) { readln().toInt() }
    println((((a or b) and (c xor d)) or ((b and c) xor (a or d))))
}
