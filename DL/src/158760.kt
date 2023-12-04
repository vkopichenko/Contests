fun main() {
    val y = readln().trim()
    val s = mutableSetOf<Int>()
    for (i in 0 until 4)
        for (j in 0 until i)
            for (k in 0 until j)
                for (l in 0 until j)
    println(s.size)
}
