import java.math.BigDecimal

fun main() {
//    val (a) = readln().split(' ').map(String::toDouble)
    val A = readln().split(' ').map(String::toInt)
    for (a in A) {
        println(BigDecimal(a).pow(a))
//        println(Math.pow(a, a))
    }
}
