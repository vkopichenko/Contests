import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val epsilon = Math.ulp(1.0)
    fun Double.isZero() = abs(this) < epsilon // 1.0.div(this).isInfinite()
    data class Vector(val x: Double, val y: Double) {
        fun lengthSquared() = x.pow(2) + y.pow(2)
        fun length() = sqrt(lengthSquared())
        operator fun plus(that: Vector) = Vector(x + that.x, y + that.y)
        operator fun minus(that: Vector) = Vector(x - that.x, y - that.y)
        operator fun times(factor: Double) = Vector(x * factor, y * factor)
        operator fun div(factor: Double) = Vector(x / factor, y / factor)
        fun ofLength(length: Double) = this * (length / length())
        fun perpendicular() = Vector(y, -x)

        // https://courses.lumenlearning.com/suny-osuniversityphysics/chapter/2-4-products-of-vectors/
        // A*B*cos
        infix fun scalarProduct(that: Vector) = x * that.x + y * that.y
        // A*B*sin
        infix fun vectorProduct(that: Vector) = x * that.y - y * that.x
        infix fun cosTo(that: Vector) = (this scalarProduct that) / (this.length() * that.length())
        infix fun sinTo(that: Vector) = (this vectorProduct that) / (this.length() * that.length())
        // cathetus: A/B*cos
        infix fun projectOnto(that: Vector) = that * ((this scalarProduct that) / that.lengthSquared())
        // hypotenuse: A/B/cos
        infix fun unprojectOnto(that: Vector): Vector? {
            return that * (this.lengthSquared() / (this scalarProduct that)).apply { if (isInfinite()) return null }
        }
    }
    operator fun LongRange.contains(value: Double) = value >= first && value <= last
    fun Vector.inSquareArea(range: LongRange) = x in range && y in range
    data class Line(val point: Vector, val direction: Vector)
    operator fun Line.contains(point: Vector) = this.direction.vectorProduct(point - this.point).isZero()
    infix fun Line.intersection(that: Line): Vector? =
        (that.point - this.point)
            .projectOnto(that.direction.perpendicular())
            .unprojectOnto(this.direction)
            ?.plus(this.point)
    infix fun Line.futureIntersection(that: Line): Vector? =
        intersection(that)?.takeIf {
            (it - this.point) scalarProduct this.direction > 0 &&
                (it - that.point) scalarProduct that.direction > 0
        }

    val hailstones = generateSequence { readlnOrNull() }.map { line ->
        line.splitToSequence(", ", " @ ").map { it.toDouble() }
            .chunked(3).map { (x, y, _) -> Vector(x, y) }
            .toList().let { (position, velocity) -> Line(position, velocity) }
    }.toList()
//    val testRange = 7L .. 27L
    val testRange = 200000000000000 .. 400000000000000
    fun <T> Sequence<T>.pairs() = flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
    hailstones.asSequence().pairs().count { (a, b) ->
//        println("$a, $b:")
        (a futureIntersection b)/*.also(::println)*/?.inSquareArea(testRange) == true
    }.let(::println)
}
