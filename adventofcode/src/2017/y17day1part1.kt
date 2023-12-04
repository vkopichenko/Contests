fun main() {
    val a = readln().map(Character::getNumericValue)
    println(a.filterIndexed { i, v -> v == a[(i+1) % a.size]}.sum())
}
