import java.io.Closeable
import java.io.File
import java.util.*

fun main() {
    fun <T : Closeable, R> T.useWith(block: T.() -> R): R = use { with(it, block) }

//    operator fun Short.plus(i: Int): Short = (this.toInt() + i).toShort()

    data class Point(val x: Short, val y: Short) {
//        infix fun dist(p: Point) = Math.abs(x - p.x) + Math.abs(y - p.y)
        fun up() = Point(x, y.inc())
        fun down() = Point(x, y.dec())
        fun left() = Point(x.dec(), y)
        fun right() = Point(x.inc(), y)
    }

    data class Step(val pos: Point, val path: Short)

    File("mud.in").bufferedReader().useWith {
//    run {
        val out = File("mud.out").printWriter(); out.useWith {
//        run {
            val (x, y, n) = readln().trim().split(' ').map(String::toShort)
            val q = ArrayDeque<Step>()
            val start = Point(0, 0)
            val finish = Point(x, y)
            val visited = mutableSetOf(start)
            repeat (n.toInt()) {
                val (x1, y1) = readln().trim().split(' ').map(String::toShort)
                visited += Point(x1, y1)
            }
            q += Step(start, 0)
            while (true) {
                val s = q.pop()
                val p = s.pos
                fun aimTo(next: Point) {
                    if (!visited.contains(next)) {
                        if (next == finish) {
                            println(s.path + 1)
                            out.close()
                            System.exit(0)
                        }
                        q += Step(next, s.path.inc())
                        visited += next
                    }
                }
                aimTo(p.up())
                aimTo(p.down())
                aimTo(p.left())
                aimTo(p.right())
            }
        }
    }
}
