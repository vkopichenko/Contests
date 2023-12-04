fun main() {
    val (l1, r1, l2, r2, k) = readln().split(' ').map { it.toLong() }
    val l = Math.max(l1, l2)
    val r = Math.min(r1, r2)
    if (l > r) {
        println(0)
    } else {
        var o = r - l + 1
        if (k in l..r) o -= 1
        println(o)
    }
}
