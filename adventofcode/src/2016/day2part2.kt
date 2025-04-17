fun main() {
    val n = 5
    val space = ' '
    val a = Array(n) { Array(n) { space } }
    var k = 0
    var x = 0
    var y = 0
    for (i in 0 until n) {
        for (j in Math.abs(n/2 - i) until n - Math.abs(n/2 - i)) {
            a[i][j] = Integer.toHexString(++k)[0].uppercaseChar()
            if (a[i][j] == '5') {
                x = i
                y = j
            }
        }
    }
    fun move(dx: Int, dy: Int) {
        val nx = x + dx
        val ny = y + dy
        if (nx in 0 until n && ny in 0 until n && a[nx][ny] != space) {
            x = nx
            y = ny
        }
    }
    System.`in`.bufferedReader().lineSequence().forEach {
        it.forEach {
            when (it) {
                'R' -> move(0, 1)
                'L' -> move(0, -1)
                'D' -> move(1, 0)
                'U' -> move(-1, 0)
            }
        }
        print(a[x][y])
    }
}


