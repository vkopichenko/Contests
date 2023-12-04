fun main() {
    val n = 3
    var x = 5
    readLine()
    System.`in`.bufferedReader().lineSequence().forEach {
        it.forEach {
            when (it) {
                'R' -> if (x % n != 0) x += 1
                'L' -> if (x % n != 1) x -= 1
                'D' -> if ((x - 1) / n < n - 1) x += n
                'U' -> if ((x - 1) / n > 0) x -= n
            }
        }
        print(x)
    }
}
