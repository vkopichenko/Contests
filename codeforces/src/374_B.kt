fun main() {
    val (n, wait) = readln().split(' ').map { it.toInt() }
    val counts = HashMap<Int, Int>(100)
    repeat(n) {
        counts.merge(readln().length, 1, Int::plus)
    }
    val pwdLength = readln().length
    var shorterCount = 0
    var equalCount = 0
    for ((l, c) in counts) {
        when {
            l < pwdLength -> shorterCount += c
            l == pwdLength -> equalCount = c
        }
    }
    fun estimate(count: Int) = count + count / wait * 5
    val min = estimate(shorterCount) + 1
    val max = estimate(shorterCount + equalCount - 1) + 1
    println("$min $max")
}

