import java.io.File

fun main() {
    File("input.txt").bufferedReader().use { input ->
        File("output.txt").printWriter().use { output ->
            val n = input.readLine().toInt()
            val a = input.readLine().split(' ').map(String::toInt)
            val s = a.map { it.toChar() }.joinToString("")
            //    println(s)
            //    val s = readln()
            var r = if (n % 2 == 1) 0 else Int.MAX_VALUE
            for (i in s.indices) {
                val l = s.length - i - 1
                val j = s.indexOf(s[i], l)
                if (j != -1 && j != i) r = r.coerceAtMost(j - l)
                val k = s.lastIndexOf(s[i], l - 1)
                if (k != -1 && k != i) r = r.coerceAtMost(l - k)
            }
            output.println(r)
        }
    }
}
