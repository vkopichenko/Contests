fun main() {
    data class Point(val x: Double, val y: Double) {
        infix fun dist(p: Point) = Math.sqrt(Math.pow(x - p.x, 2.0) + Math.pow(y - p.y, 2.0))
    }

    fun readPoint() = readln().trim().split(' ').map(String::toDouble).let { Point(it[0], it[1]) }

    val n = readln().trim().toInt()
    val k = readPoint()
    val a = Array<Point>(n) { readPoint() }
    val (r1, r2) = readln().trim().split(' ').map(String::toDouble)
    val r = r1.rangeTo(r2)
    println(a.count { k.dist(it) in r })
}
