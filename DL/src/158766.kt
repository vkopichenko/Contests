fun main() {
    val n = readln().trim().toInt()
    val a = Array<Int>(n) { readln().trim().toInt() }
    val min = a.min()!!
    val max = a.max()!!
//    println("$min, $max")
//    println(a.count { it in (min + 1) until max })
    println(a.count { it != min && it != max })
}
