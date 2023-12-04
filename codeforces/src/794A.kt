fun main() {
    val (b, a, c) = readln().split(' ').map(String::toInt)
    val n = readln().toInt()
    val x = readln().split(' ').map(String::toInt)
    println(x.count { it in a+1..c-1 })
}
