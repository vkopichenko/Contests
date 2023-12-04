fun main() {
    println(generateSequence { readLine()?.run { "${first(Char::isDigit)}${last(Char::isDigit)}".toInt() } }.sum())
}
