fun main() {
    val steps = readln()
    readln()
    val network = generateSequence { readlnOrNull() }.map { line ->
        val (node, left, right) = """(\w+) = \((\w+), (\w+)\)""".toRegex().matchEntire(line)!!.destructured
        node to (left to right)
    }.toMap()
    var i = 0
    generateSequence("AAA") { node ->
        network.getValue(node).run {
            if (steps[i++ % steps.length] == 'L') first else second
        }
    }.takeWhile { it != "ZZZ" }.count().let(::println)
}
