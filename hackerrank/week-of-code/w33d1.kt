fun main() {
    val n = readln().toInt()
    val a = readln().split(" ").map(String::toInt)
    val b = readln().split(' ').map(String::toInt)
    val i = a.indices.minBy { a[it] }!!
    val j = b.indices.minBy { b[it] }!!
    if (i != j) {
        println(a[i] + b[j])
    } else {
        println(minOf(
                a[i] + b.indices.minBy { if (it != i) b[it] else Int.MAX_VALUE }!!.let { b[it] },
                b[i] + a.indices.minBy { if (it != j) a[it] else Int.MAX_VALUE }!!.let { a[it] }))
    }
}
