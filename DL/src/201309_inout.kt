import java.io.File

fun main() {
    File("input.txt").bufferedReader().use { input ->
        File("output.txt").printWriter().use { output ->
            val (m, n) = input.readLine().trim().split(' ').map(String::toInt)
            val mn = m * n

            fun count(bit: Int, state: Int): Int {
                if (bit == mn) return 1;
                val bit1 = bit + 1
                if (bit < m || bit % m == 0) return count(bit1, state) + count(bit1, state or (1 shl bit))
                val mask = (1 shl bit - 1) or (1 shl bit - m) or (1 shl bit - m - 1)
                val masked = state and mask
                return (if (masked != 0) count(bit1, state) else 0) +
                        (if (masked != mask) count(bit1, state or (1 shl bit)) else 0)
            }

            if (m == 1 || n == 1) output.println(1 shl mn)
            else output.println(count(0, 0))
        }
    }
}
