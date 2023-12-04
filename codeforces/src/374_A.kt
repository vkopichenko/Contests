fun main() {
    val n = readln().toInt()
    val s = readln()
    val r = s.split("W+".toRegex()).map { it.length }.filter { it > 0 }
    println(r.size)
    println(r.joinToString(" "))
}
