fun main() {
    infix fun String.link(that: String) = setOf(this, that)
    val components = mutableSetOf<String>()
    val links = generateSequence { readlnOrNull() }.flatMap { line ->
        line.splitToSequence(": ", " ").run {
            onEach { components += it }.iterator().run {
                val node = next()
                asSequence().map { node link it }
            }
        }
    }.toList()
    //println("${components.size}: $components")
    val componentLinks = components.associateWith { node ->
        links.asSequence().filter { node in it }.flatMap { nodes -> nodes.filter { node !in it } }.toSet()
    }
    fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>> =
        flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
    fun <T> Sequence<T>.triples(): Sequence<Triple<T, T, T>> =
        flatMapIndexed { i, a ->
            drop(i + 1).run {
                flatMapIndexed { j, b ->
                    drop(j + 1).map { c -> Triple(a, b, c) }
                }
            }
        }
    val linkCountsByNode = components.associateWith { node ->
        links.count { node in it }
    }
    links.sortedByDescending { link ->
        link.sumOf { linkCountsByNode[it]!! }
    }.asSequence().triples().forEach { triple ->
        val disconnected = triple.toList().toSet()
        val root = disconnected.first().first()
        generateSequence(setOf(root) to listOf(root)) { (totalNodes, pendingNodes) ->
            val nextNodes = pendingNodes
                .flatMap { node -> componentLinks[node]!!.filter { (node link it) !in disconnected } }
                .filter { it !in totalNodes }
            totalNodes + nextNodes to nextNodes
        }.first { it.second.isEmpty() }.first.size.also {
            if (it < components.size) println(it * (components.size - it))
        }
    }
}
