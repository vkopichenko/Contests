import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val i: Int, val j: Int)
    fun Pos.move(dir: Char) = when (dir) {
        '^' -> Pos(i - 1, j)
        'v' -> Pos(i + 1, j)
        '<' -> Pos(i, j - 1)
        '>' -> Pos(i, j + 1)
        else -> error("Impossible")
    }
    infix fun Pos.distance(that: Pos) = abs(i - that.i) + abs(j - that.j)
    operator fun Array<CharArray>.get(pos: Pos) = this[pos.i][pos.j]
    operator fun Array<CharArray>.set(pos: Pos, value: Char) { this[pos.i][pos.j] = value }

    val map = generateSequence { readln().ifEmpty { null } }.toList()
    val moves = generateSequence { readlnOrNull() }.joinToString("")

    measureTimedValue { // part 1
        val a = Array(map.size) { i -> CharArray(map[i].length) { j -> map[i][j] } }
        val startPos = a.indices.firstNotNullOf { i ->
            a[i].indices.firstNotNullOfOrNull { j ->
                Pos(i, j).takeIf { a[it] == '@' }
            }
        }
        var pos = startPos
        for (dir in moves) {
            fun nextFreePos() = generateSequence(pos) { it.move(dir) }.drop(1)
                .takeWhile { a[it] != '#' }.filter { a[it] == '.' }.firstOrNull()
            val freePos = nextFreePos() ?: continue
            a[pos] = '.'
            when (pos distance freePos) {
                1 -> pos = freePos
                else -> {
                    pos = pos.move(dir)
                    a[freePos] = 'O'
                }
            }
            a[pos] = '@'
        }
        a.asSequence().flatMapIndexed { i, row ->
            row.mapIndexed { j, char ->
                (i to j).takeIf { char == 'O' }
            }
        }.filterNotNull().sumOf { (i, j) -> 100 * i + j }
    }.also(::println)

    measureTimedValue { // part 2
        val a = Array(map.size) { i ->
            CharArray(map[i].length shl 1) { j ->
                when (val c = map[i][j shr 1]) {
                    'O' -> if (j and 1 == 0) '[' else ']'
                    '@' -> if (j and 1 == 0) '@' else '.'
                    else -> c
                }
            }
        }
        val startPos = a.indices.firstNotNullOf { i ->
            a[i].indices.firstNotNullOfOrNull { j ->
                Pos(i, j).takeIf { a[it] == '@' }
            }
        }
        var pos = startPos
        for (dir in moves) {
            if (dir in "<>") {
                fun nextFreePos() = generateSequence(pos) { it.move(dir) }.drop(1)
                    .takeWhile { a[it] != '#' }.filter { a[it] == '.' }.firstOrNull()
                val freePos = nextFreePos() ?: continue
                a[pos] = '.'
                when (val dist = pos distance freePos) {
                    1 -> pos = freePos
                    else -> {
                        pos = pos.move(dir)
                        generateSequence(pos) { it.move(dir) }.take(dist).forEachIndexed { j, p ->
                            a[p] = if ((j and 1 == 0) xor (dir == '>')) '[' else ']'
                        }
                    }
                }
                a[pos] = '@'
            } else {
                fun moveRow(boxes: Iterable<Pos>): Boolean {
                    val nextSlots = boxes.mapTo(mutableSetOf()) { it.move(dir) }
                    if (nextSlots.any { a[it] == '#' }) return false
                    nextSlots.apply {
                        removeIf { a[it] == '.' }
                        buildList<Pos> {
                            this@apply.forEach { slot ->
                                when {
                                    a[slot] == '[' -> add(slot.move('>'))
                                    a[slot] == ']' -> add(slot.move('<'))
                                }
                            }
                        }.also { addAll(it) }
                    }
                    return (nextSlots.isEmpty() || moveRow(nextSlots)).also { moving ->
                        if (moving) {
                            boxes.forEach {
                                a[it.move(dir)] = a[it]
                                a[it] = '.'
                            }
                        }
                    }
                }
                if (moveRow(listOf(pos))) {
                    a[pos] = '.'
                    pos = pos.move(dir)
                    a[pos] = '@'
                }
            }
/*
            a.forEach {
                println(it.joinToString(""))
            }
*/
        }
        a.asSequence().flatMapIndexed { i, row ->
            row.mapIndexed { j, char ->
                (i to j).takeIf { char == '[' }
            }
        }.filterNotNull().sumOf { (i, j) -> 100 * i + j }
    }.also(::println)
}
