fun main() {
    val n = readln().toInt()
    println(when (n) {
        in 1..9 -> 1
        else -> {
            val l = Math.log10(n.toDouble()).toInt()
            val p = Math.pow(10.0, l.toDouble()).toInt()
//            println("$l, $p")
            p - n % p
        }
    })
}
