fun main() {
    val a = readln().map(Character::getNumericValue)
    println(a.filterIndexed { i, v -> v == a[(i + a.size / 2) % a.size] }.sum())
}
