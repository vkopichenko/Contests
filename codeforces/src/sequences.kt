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

fun <T> Iterable<T>.interpose(combine: (T, T) -> T): Sequence<T> = sequence {
    //if (isEmpty()) return@sequence
    yield(first())
    reduce { a, b ->
        yield(combine(a, b))
        yield(b)
        b
    }
}

fun <T> Iterable<T>.supplement(derive: (Int, T) -> T?): Sequence<T> = sequence {
    forEachIndexed { i, it ->
        yield(it)
        derive(i, it)?.let { yield(it) }
    }
}

fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>> = flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
fun <T> Sequence<T>.everyPair(): Sequence<Pair<T, T>> = flatMap { a -> map { b -> Pair(a, b) } }

private fun repartition(n: Int) = generateSequence(1, Int::inc).take(n).partition{it % 2 == 0}.run {
    first.asSequence() + second.asReversed().asSequence()
}

fun main() {
    println((1..12).toList().interchange().joinToString())
}
