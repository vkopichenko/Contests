fun main() {
    data class Point(var x: Int, var y: Int) {
        fun abs() = Math.abs(x) + Math.abs(y)
        fun clone() = Point(x, y)
    }

    val moves = readln().split(", ")
    var dir = 0
    var pos = Point(0, 0)
    val points = mutableSetOf<Point>(pos)
    for (move in moves) {
        val d = move[0]
        var l = move.substring(1).toInt()
        if (l == 0) continue
        when (d) {
            'L' -> --dir
            'R' -> ++dir
        }
        dir += 4
        dir %= 4

        fun check(p: Point): Boolean {
            if (points.contains(p)) {
                println(p.abs())
                return true
            }
            points += p.clone()
            return false
        }

        val p = pos.clone()
        when (dir) {
            0 -> while (l-- != 0) { ++p.y; if (check(p)) return; }
            1 -> while (l-- != 0) { ++p.x; if (check(p)) return; }
            2 -> while (l-- != 0) { --p.y; if (check(p)) return; }
            3 -> while (l-- != 0) { --p.x; if (check(p)) return; }
        }
        pos = p
    }
}
