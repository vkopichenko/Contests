import kotlin.time.measureTimedValue

fun main() {
    data class Vec(val x: Int, val y: Int) {
        operator fun plus(that: Vec) = Vec(x + that.x, y + that.y)
        operator fun minus(that: Vec) = Vec(x - that.x, y - that.y)
        operator fun times(factor: Int) = Vec(x * factor, y * factor)
        operator fun div(factor: Int) = Vec(x / factor, y / factor)
        infix fun floorMod(that: Vec) = Vec(Math.floorMod(x, that.x), Math.floorMod(y, that.y))
    }

    data class Robot(val position: Vec, val velocity: Vec)

    val robots = generateSequence { readlnOrNull() }.map { line ->
        """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex().find(line)!!.destructured
            .let { (px, py, vx, vy) -> Robot(Vec(px.toInt(), py.toInt()), Vec(vx.toInt(), vy.toInt())) }
    }.toList()

//    val maxSpace = Vec(11, 7) // example
    val maxSpace = Vec(101, 103)
    val halfSpace = maxSpace / 2

    fun Robot.warp(time: Int): Vec = (position + velocity * time) floorMod maxSpace

    measureTimedValue { // part 1
        robots.asSequence()
            .map { it.warp(100) }
            .filterNot { it.x == halfSpace.x || it.y == halfSpace.y }
            .groupingBy { (it.x < halfSpace.x) to (it.y < halfSpace.y) }.eachCount().values
            .fold(1L) { a, b -> a * b }
    }.also(::println)

    measureTimedValue { // part 2, The Easter Egg!
        val easterRangeX = 40..70
        val easterRangeY = 45..80
        repeat(10000) { time ->
            val positions = robots.groupingBy { it.warp(time) }.eachCount()
            val easterRatioX = positions.entries.sumOf { (pos, count) ->
                count * if (pos.x in easterRangeX) 1 else 0
            } / robots.count().toDouble()
            if (easterRatioX < 0.75) return@repeat // continue
            val easterRatioY = positions.entries.sumOf { (pos, count) ->
                count * if (pos.y in easterRangeY) 1 else 0
            } / robots.count().toDouble()
            if (easterRatioY < 0.75) return@repeat // continue
            println("------------------------------------------------------")
            println("Time: $time, easterRatios: ($easterRatioX, $easterRatioY)")
            (0 until maxSpace.y).forEach { y ->
                (0 until maxSpace.x).joinToString("") { x ->
                    positions[Vec(x, y)]?.toString() ?: " "
                }.also(::println)
            }
            Thread.sleep(100)
        }
    }.also(::println)
}
/*
Time: 6587, easterRatios: (0.78, 0.768)
                                 1      1111111111111111111111111111111
            1                           1                             1
                                        1                             1          1
                                        1                             1
                    1                   1                             1
                                        1              1              1
                                        1             111             1                   1
                                        1            11111            1
                                      1 1           1111111           1
                                        1          111111111          1
                                        1            11111            1
             1                          1           1111111           1           1         1
     1  1                               1          111111111          1     1
       1        1                       1         11111111111         1
                                        1        1111111111111        1
 1   1                                  1          111111111          1
                                        1         11111111111         1       1
                 1                      1        1111111111111        1
      1                      1          1       111111111111111       1
1                                       1      11111111111111111      1
                                        1        1111111111111        1    1
     1                                  1       111111111111111       1
                                        1      11111111111111111      1
                                        1     1111111111111111111     1
                 1                      1    111111111111111111111    1                   1
                                        1             111             1
              1                         1             111             1
                                        1             111             1            1
                                        1                             1
                                        1                             1
                                        1                             1   1
                                        1                             1            1
                                        1111111111111111111111111111111
*/
