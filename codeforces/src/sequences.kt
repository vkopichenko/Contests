import java.util.concurrent.ConcurrentHashMap

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

fun <T> List<T>.repeat(n: Int): List<T> = let { self ->
    buildList(n * self.size) { repeat(n) { addAll(self) } }
}

fun CharSequence.repeat(n: Int, delimiter: String = ""): String =
    generateSequence(this) { it }.take(n).joinToString(delimiter) { it }

fun CharSequence.repeat2(n: Int, delimiter: String = ""): String = let { self ->
    buildString(length * n + delimiter.length * (n - 1).coerceAtLeast(0)) {
        repeat(n) { i -> append(self); if (i < n - 1) append(delimiter) }
    }
}

// Beware: https://arrow-kt.io/learn/collections-functions/memoize/#recursion
// TODO: arrow.core.MemoizedDeepRecursiveFunction https://arrow-kt.io/learn/collections-functions/recursive/#memoized-recursive-functions
fun <X, R> ((X) -> R).memoized(): (X) -> R {
    val cache = mutableMapOf<X, R>()
    return { cache.computeIfAbsent(it) { this(it) } }
}
fun <X, Y, R> ((X, Y) -> R).memoized(): (X, Y) -> R {
    val cache = mutableMapOf<Pair<X, Y>, R>()
    return { x, y -> cache.computeIfAbsent(x to y) { this(x, y) } }
}
fun <X, R> ((X) -> R).memoizedThreadSafe(): (X) -> R {
    val cache = ConcurrentHashMap<X, R>()
    return { cache.computeIfAbsent(it) { this(it) } }
}
fun <X, Y, R> ((X, Y) -> R).memoizedThreadSafe(): (X, Y) -> R {
    val cache = ConcurrentHashMap<Pair<X, Y>, R>()
    return { x, y -> cache.computeIfAbsent(x to y) { this(x, y) } }
}
private val loadLibraryMemoized = System::loadLibrary.memoized()
private val concatMemoized = String::plus.memoized()


fun <T> T?.orElse(default: T): T = this ?: default

private fun repartition(n: Int) = generateSequence(1, Int::inc).take(n).partition { it % 2 == 0 }.run {
    first.asSequence() + second.asReversed().asSequence()
}

fun allChoices(arity: Int): Sequence<Sequence<Boolean>> =
    generateSequence<Sequence<Sequence<Boolean>>>(sequenceOf(emptySequence())) { choiceSeqs ->
        sequenceOf(false, true).flatMap { choice ->
            choiceSeqs.map { choiceSeq ->
                sequenceOf(choice) + choiceSeq
            }
        }
    }.elementAt(arity)

fun <T> allChoices(arity: Int, vararg choices: T): Sequence<Sequence<T>> =
    generateSequence<Sequence<Sequence<T>>>(sequenceOf(emptySequence())) { choiceSeqs ->
        sequenceOf(*choices).flatMap { choice ->
            choiceSeqs.map { choiceSeq ->
                sequenceOf(choice) + choiceSeq
            }
        }
    }.elementAt(arity)

fun <T> Sequence<T>.allPermutations(arity: Int): Sequence<Sequence<T>> =
    map { sequenceOf(it) }.let { firstValues ->
        generateSequence<Sequence<Sequence<T>>>(sequenceOf(emptySequence())) { prevAritySeq ->
            firstValues.flatMap { value ->
                prevAritySeq.map { values ->
                    value + values
                }
            }
        }.elementAt(arity)
    }

fun <C, T, R> aggregateChoices(depth: Int, initial: R, choices: Iterable<C>, transform: (R, C, Int) -> R?): Sequence<R> =
    generateSequence(sequenceOf(initial to 0)) { previousChoiceOutcomes ->
        choices.asSequence().flatMap { choice ->
            previousChoiceOutcomes.mapNotNull { (value, i) ->
                transform(value, choice, i)?.let { it to i + 1 }
            }
        }
    }.elementAt(depth).map { it.first }
fun <T, C> List<T>.reduceWithChoices(choices: Iterable<C>, transform: (T, C, T) -> T?): Sequence<T> =
    aggregateChoices<C, T, T>(size - 1, this[0], choices) { acc, operator, i ->
        transform(acc, operator, this[i + 1])
    }
fun <T, C, R> List<T>.foldWithChoices(initial: R, choices: Iterable<C>, transform: (R, C, T) -> R?): Sequence<R> =
    aggregateChoices<C, T, R>(size, initial, choices) { acc, operator, i ->
        transform(acc, operator, this[i])
    }

fun main() {
    println((1..12).toList().interchange().joinToString())
}
