fun main() {
    val x = readln().split(' ').map { it.toInt() }
    val r = x.max()!! - x.min()!!
    println(r)
}
