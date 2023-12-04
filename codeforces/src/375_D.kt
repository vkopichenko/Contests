import java.util.*

data class Point(val x: Int, val y: Int) {
    operator fun plus(that: Point) = Point(this.x + that.x, this.y + that.y)
}
data class Lake(val start: Point, var size: Int = 0, var fake: Boolean = false)

operator fun Array<CharArray>.get(x: Int, y: Int): Char {
    return this[x][y]
}
operator fun Array<CharArray>.set(x: Int, y: Int, c: Char) {
    this[x][y] = c
}

operator fun Array<CharArray>.get(p: Point): Char {
    return this[p.x - 1][p.y - 1]
}
operator fun Array<CharArray>.set(p: Point, c: Char) {
    this[p.x - 1][p.y - 1] = c
}
operator fun Array<BooleanArray>.get(p: Point): Boolean {
    return this[p.x - 1][p.y - 1]
}
operator fun Array<BooleanArray>.set(p: Point, c: Boolean) {
    this[p.x - 1][p.y - 1] = c
}

inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int)->INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }
fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }
fun array2dOfBoolean(sizeOuter: Int, sizeInner: Int): Array<BooleanArray> = Array(sizeOuter) { BooleanArray(sizeInner) }

fun main() {
    val (n, m, k) = readln().split(' ').map { it.toInt() }
    val lines = Array<CharArray>(n, { readln().toCharArray() })
//    val visited = Array<BooleanArray>(n, { BooleanArray(m, { false }) })
    val visited = array2dOfBoolean(n, m)

    fun <A> bfs(start: Point, initAcc: (Point) -> A, check: (Point) -> Boolean, proc: (Point, A) -> A): A? {
        fun chk(p: Point): Boolean = !visited[p] && check(p)
        if (!chk(start)) return null
        val queue = ArrayDeque<Point>()
        queue += start
        visited[start] = true
        var acc = initAcc(start)
        while (!queue.isEmpty()) {
            val cur = queue.poll()!!
            acc = proc(cur, acc)
            for (d in listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))) {
                val next = cur + d
                if (next.x in 1..n && next.y in 1..m && chk(next)) {
                    visited[next] = true
                    queue += next
                }
            }
        }
        return acc
    }

    val lakes = mutableListOf<Lake>()
    repeat(n) { i ->
        lines[i].forEachIndexed { j, char ->
            val lake = bfs(Point(i + 1, j + 1), { Lake(it) }, { lines[it] == '.' }) { p, l ->
                l.size++
                if (p.x == 1 || p.x == n || p.y == 1 || p.y == m) l.fake = true
                l
            }
            if (lake != null && !lake.fake) lakes += lake
        }
    }
    var filled = 0
    if (k < lakes.size) {
        visited.forEach { it.fill(false) }
        lakes.sortBy { it.size }
        lakes.take(lakes.size - k).forEach { lake ->
            filled += bfs(lake.start, { 0 }, { lines[it] == '.' }) { p, a ->
                lines[p] = '*'
                a + 1
            }!!
        }
    }
    println(filled)
    lines.forEach { println(it) }
}
