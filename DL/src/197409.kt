import java.io.Closeable

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

//    File("input.txt").bufferedReader().useWith {
    run {
//        File("output.txt").printWriter().useWith {
        run {
            val (k, y) = readln().split(' ').map(String::toInt)
//            val c0 = Integer.numberOfLeadingZeros(0) - Integer.numberOfLeadingZeros(y) - Integer.bitCount(y)
            val c0 = 30 - Integer.bitCount(y)
            if (k > 1 shl c0) {
                print(-1)
            } else {
                var i = k
                var x = y
                while (true) {
                    if (x and y == y) {
                        print("$x ")
                        if (--i == 0) break
                        ++x
                    } else {
                        x += x xor y and y
                    }
                }
            }
        }
    }
}
