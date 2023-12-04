fun main() {
    val normal = setOf("great!", "great", "don't think so", "don't touch me!", "don't touch me", "not bad", "cool")
    repeat(10) { i ->
        println(i)
        val s = readLine()
        if (s != "no") {
            println(if (normal.contains(s)) "normal" else "grumpy")
            return@repeat
        }
    }
}
