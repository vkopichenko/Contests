fun main() {
    val q = readln().toInt()
    repeat(q) {
        val s = readln()
        println("10+(?=1)".toRegex().findAll(s).count())
    }
}
