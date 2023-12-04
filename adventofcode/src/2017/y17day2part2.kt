fun main() {

    fun <T> Sequence<T>.everyPair(): Sequence<Pair<T, T>> = flatMap { a -> map { b -> Pair(a, b) } }

    System.`in`.bufferedReader().use {
        println(
            it.lineSequence().map {
                it.splitToSequence(' ', '\t').filter(String::isNotBlank).map(String::toInt)
                    .everyPair().first { (a, b) -> a != b && a % b == 0 }.let { (a, b) -> a / b }
            }.sum()
        )
    }
}

