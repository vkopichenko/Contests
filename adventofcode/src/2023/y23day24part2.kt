import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sqrt

fun main() {
    val epsilon = Math.ulp(1.0) * 10
    fun Double.isZero() = abs(this) <= epsilon // 1.0.div(this).isInfinite()
    fun Double.isInteger() = (this - roundToLong()).isZero()
    data class Vector(val x: Double, val y: Double, val z: Double) {
        fun isZero() = x.isZero() && y.isZero() && z.isZero()
        fun isInteger() = x.isInteger() && y.isInteger() && z.isInteger()
        fun lengthSquared() = x.pow(2) + y.pow(2) + z.pow(2)
        fun length() = sqrt(lengthSquared())
        operator fun plus(that: Vector) = Vector(x + that.x, y + that.y, z + that.z)
        operator fun minus(that: Vector) = Vector(x - that.x, y - that.y, z - that.z)
        operator fun times(factor: Double) = Vector(x * factor, y * factor, z * factor)
        operator fun div(factor: Double) = Vector(x / factor, y / factor, z / factor)
        fun ofLength(length: Double) = this * (length / length())

        // https://courses.lumenlearning.com/suny-osuniversityphysics/chapter/2-4-products-of-vectors/
        infix fun scalarProduct(that: Vector) = x * that.x + y * that.y + z * that.z
        // https://en.wikipedia.org/wiki/Cross_product
        infix fun vectorProduct(that: Vector) =
            Vector(y * that.z - z * that.y, z * that.x - x * that.z, x * that.y - y * that.x)
        // cathetus: A/B*cos
        infix fun projectOnto(that: Vector) = that * ((this scalarProduct that) / that.lengthSquared())
        // hypotenuse: A/B/cos
        infix fun unprojectOnto(that: Vector): Vector? {
            return that * (this.lengthSquared() / (this scalarProduct that).apply { if (isZero()) return null })
        }
    }
    val Zero = Vector(0.0, 0.0, 0.0)
    fun parallelepipedVolume(a: Vector, b: Vector, c: Vector) = abs(a scalarProduct (b vectorProduct c))

    data class Line(val point: Vector, val direction: Vector)
    infix fun Line.distanceTo(that: Line) =
        ((that.point - this.point) projectOnto (this.direction vectorProduct that.direction)).length()
/*
    infix fun Line.distanceTo(that: Line): Double {
        val directionProduct = this.direction vectorProduct that.direction
        return ((that.point - this.point) scalarProduct directionProduct) / directionProduct.length()
    }
*/
    operator fun Line.contains(point: Vector) =
        direction.vectorProduct(point - this.point).isZero()
    infix fun Line.intersection(that: Line): Vector? =
        (that.point - this.point)
            .run { projectOnto(vectorProduct(direction).vectorProduct(that.direction)) }
            .unprojectOnto(this.direction) // TODO: validate
            ?.plus(this.point)?.takeIf { it in this }

    fun Line.futureContains(point: Vector) =
        contains(point) && (direction scalarProduct (point - this.point)) > 0
    fun Line.isInteger() = point.isInteger() && direction.isInteger()
    fun Line.positionAtTime(timeDiff: Int) = point + direction * timeDiff.toDouble()
    fun Line.atTime(timeDiff: Int) = Line(positionAtTime(timeDiff), direction)
    fun Line.relativeTo(that: Line) = Line(this.point - that.point, this.direction - that.direction)
    fun perfectlyCollides(a: Line, b: Line, atTime: Int): Boolean =
        a.relativeTo(b).atTime(atTime).run { futureContains(Zero) && isInteger() }.also {
            val intersection = a intersection b
            val time = if (intersection == null) null else (intersection - a.point).length() / (a.direction - b.direction).length()
            println("A: $a, B: $b, time: $time, intersection: $intersection")
        }

    val hailstones = generateSequence { readlnOrNull() }.map { line ->
        line.splitToSequence(", ", " @ ").map { it.toDouble() }
            .chunked(3).map { (x, y, z) -> Vector(x, y, z) }
            .toList().let { (position, velocity) -> Line(position, velocity) }
    }.toList()
    // https://math.stackexchange.com/questions/2195047/solve-the-vector-cross-product-equation
    // (pi-p0) x (vi-v0) = 0, for each i, but it appeared too hard to derive the formula for p0... (
    // Hence a semi brute force is happening below.
    fun <T> Sequence<T>.pairs() = flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
    val timeToFirst = 1
    (1..10).first { timeToSecond ->
        println("timeToSecond=$timeToSecond")
        hailstones.asSequence().pairs().any { (a, b) ->
            val positionAtFirstTime = a.positionAtTime(timeToFirst)
            val directionAtoB = b.positionAtTime(timeToFirst + timeToSecond) - positionAtFirstTime
            val candidateLine = Line(positionAtFirstTime, directionAtoB).atTime(-timeToFirst)
            if (!candidateLine.isInteger()) return@any false.also { println("$candidateLine is not integer") }
            hailstones.asSequence().filter { it != a && it != b }.all {
                perfectlyCollides(candidateLine, it, timeToFirst)
            }.also {
                if (it) {
                    println(candidateLine)
                    println(candidateLine.point.run { (x * y * z).roundToLong() })
                }
            }
        }
    }
}
