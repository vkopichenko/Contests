fun main() = println(
    generateSequence(::readLine).sumOf {
        val a = (0 until it.indices.last).maxBy(it::get)
        val b = (a + 1 until it.length).maxOf(it::get)
        "${it[a]}$b".toInt()
    }
)
