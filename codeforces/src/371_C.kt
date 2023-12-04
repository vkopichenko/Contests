fun main() {

    fun toPattern(str: String): Int = str.fold(0) { a, c -> (a shl 1) or ((c - '0') and 1) }

    val t = readln().toInt()
    val map = HashMap<Int, Int>(t)
    repeat(t) {
        //println(map)
        val (op, param) = readln().split(' ')
        val pattern = toPattern(param)
        when (op) {
            "+" -> map.merge(pattern, 1, Int::plus)
            "-" -> map.merge(pattern, 1, Int::minus)
            "?" -> println(map[pattern] ?: 0)
        }
    }
}
