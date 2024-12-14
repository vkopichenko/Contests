import kotlin.math.*
import kotlin.time.measureTimedValue

fun main() {
    infix fun Double.fuzzyEquals(that: Double) =
        abs(this - that) < Math.ulp(100.0 * max(abs(this), abs(that)))
    fun Double.isZero() = this fuzzyEquals 0.0
    infix fun Double.integerDiv(value: Double) =
        (this / value).run { roundToLong().takeIf { this fuzzyEquals it.toDouble() } }

    data class Vector(val x: Double, val y: Double) {
        fun lengthSquared() = x.pow(2) + y.pow(2)
        fun length() = sqrt(lengthSquared())
        operator fun plus(that: Vector) = Vector(x + that.x, y + that.y)
        operator fun minus(that: Vector) = Vector(x - that.x, y - that.y)
        operator fun unaryMinus() = Vector(-x, -y)
        operator fun times(factor: Double) = Vector(x * factor, y * factor)
        operator fun div(factor: Double) = Vector(x / factor, y / factor)
        fun ofLength(length: Double) = this * (length / length())
        fun perpendicular() = Vector(y, -x)
        fun isZero() = this.x.isZero() && this.y.isZero()

        // https://courses.lumenlearning.com/suny-osuniversityphysics/chapter/2-4-products-of-vectors/
        // A*B*cos
        infix fun scalarProduct(that: Vector) = x * that.x + y * that.y
        // A*B*sin
        infix fun vectorProduct(that: Vector) = x * that.y - y * that.x
        infix fun collinear(that: Vector) = (this vectorProduct that).isZero()
        infix fun cosTo(that: Vector) = (this scalarProduct that) / (this.length() * that.length())
        infix fun sinTo(that: Vector) = (this vectorProduct that) / (this.length() * that.length())
        // cathetus: A/B*cos
        infix fun projectOnto(that: Vector) = that * ((this scalarProduct that) / that.lengthSquared())
        // hypotenuse: A/B/cos
        infix fun unprojectOnto(that: Vector): Vector? =
            that * (this.lengthSquared() / (this scalarProduct that)).apply { if (isInfinite()) return null }
    }
    val ZERO = Vector(0.0, 0.0)
    val ONE = Vector(1.0, 1.0)

    data class Line(val point: Vector, val direction: Vector)
    infix fun Line.intersection(that: Line): Vector? =
        (that.point - this.point)
            .projectOnto(that.direction.perpendicular())
            .unprojectOnto(this.direction)
            ?.plus(this.point)
    infix fun Line.positiveIntersection(that: Line): Vector? =
        intersection(that)?.takeIf {
            (it - this.point) scalarProduct this.direction > 0 &&
                (it - that.point) scalarProduct that.direction > 0
        }

    val configs = generateSequence { readlnOrNull() }.chunked(4).map { chunk ->
        chunk.subList(0, 3).map { line ->
            """.?+X[+=](\d+), Y[+=](\d+)""".toRegex().find(line)!!.destructured
                .let { (x, y) -> Vector(x.toDouble(), y.toDouble()) }
                .also { check(!it.isZero()) }
        }.let { Triple(it[0], it[1], it[2]) }
    }.toList()

    fun intersectionsPrice(shift: Long) = configs.asSequence().mapNotNull { (A, B, Prize) ->
        val P = Prize + ONE * shift.toDouble()
        when {
            A collinear B && A collinear P ->
                TODO("Diophantine equations, Extended Euclidean algorithm")
            A collinear B -> null // P is unreachable
            else -> (Line(ZERO, A) positiveIntersection Line(P, -B))?.let { X ->
                ((X - ZERO).length() integerDiv A.length())?.let { a ->
                    ((P - X).length() integerDiv B.length())?.let { b ->
                        a to b
                    }
                }
            }
        }
    }.sumOf { (a, b) -> 3 * a + b }

    measureTimedValue { // part 1
        intersectionsPrice(0)
    }.also(::println)

    measureTimedValue { // part 2
        intersectionsPrice(10000000000000)
    }.also(::println)
}
