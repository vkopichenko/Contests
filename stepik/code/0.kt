// https://stepik.org/lesson/67475/step/3?course=%D0%92%D1%81%D1%82%D1%83%D0%BF%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9-%D1%8D%D0%BA%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD&unit=44250

fun main() {
    println((1..readln().toInt()).asSequence().filter { it % 3 != 0 }.map { it*it }.sum())
}
