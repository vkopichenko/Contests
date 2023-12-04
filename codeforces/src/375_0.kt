import java.util.*

fun main() {
    val bound = 1000000
    val s = Random().nextInt(bound)
    var x = s
    var i = 0
    do {
        x += 65537
        x %= bound
        ++i
    } while (x != s)
    println("$i")
}
