fun main() {
    val s = readln()
    val L = 26
    var k = 0
    var last = 'a'
    for (c in s) {
        val r = Math.abs(c - last)
        val rr = Math.min(r, L - r)
        k += rr
        last = c
    }
    println(k)
}
