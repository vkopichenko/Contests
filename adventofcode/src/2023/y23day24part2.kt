import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val epsilon = Math.ulp(1.0)
    fun Double.isZero() = abs(this) < epsilon // 1.0.div(this).isInfinite()
    data class Vector(val x: Double, val y: Double, val z: Double) {
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
            return that * (this.lengthSquared() / (this scalarProduct that)).apply { if (isInfinite()) return null }
        }
    }
    fun parallelepipedVolume(a: Vector, b: Vector, c: Vector) = abs(a scalarProduct (b vectorProduct c))
    data class Line(val point: Vector, val direction: Vector)
    operator fun Line.contains(point: Vector) =
        this.direction.vectorProduct(point - this.point)
            .run { x.isZero() && y.isZero() && z.isZero()}
    infix fun Line.intersection(that: Line): Vector? =
        (that.point - this.point)
            .run { projectOnto(vectorProduct(direction).vectorProduct(that.direction)) }
            .unprojectOnto(this.direction)
            ?.plus(this.point)

    val hailstones = generateSequence { readlnOrNull() }.map { line ->
        line.splitToSequence(", ", " @ ").map { it.toDouble() }
            .chunked(3).map { (x, y, z) -> Vector(x, y, z) }
            .toList().let { (position, velocity) -> Line(position, velocity) }
    }.toList()
    fun <T> Sequence<T>.pairs() = flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
    hailstones.asSequence().pairs().count { (a, b) ->
//        println("$a, $b:")
        true
    }.let(::println)
}
