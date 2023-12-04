fun main() {
    val moves = readln().split(", ")
    var dir = 0
    var x = 0
    var y = 0
    for (move in moves) {
        val d = move[0]
        val l = move.substring(1).toInt()
        when (d) {
            'L' -> --dir
            'R' -> ++dir
        }
        dir += 4
        dir %= 4
        when (dir) {
            0 -> y += l
            1 -> x += l
            2 -> y -= l
            3 -> x -= l
        }
    }
    println(Math.abs(x) + Math.abs(y))
}
