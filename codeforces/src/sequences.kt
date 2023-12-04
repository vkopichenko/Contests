private fun fibonacci() = sequence {
    var terms = Pair(0, 1)
    while (true) {
        yield(terms.first)
        terms = Pair(terms.second, terms.first + terms.second)
    }
}

private fun <T> List<T>.interchange() = sequence {
    var l = 0
    var r = size - 1
    while (l < r) {
        yield(get(l++))
        yield(get(r--))
    }
    if (l == r) {
        yield(get(l))
    }
}

private fun repartition(n: Int) = generateSequence(1, Int::inc).take(n).partition{it % 2 == 0}.run {
    first.asSequence() + second.asReversed().asSequence()
}

fun main() {
    println((1..12).toList().interchange().joinToString())
}
