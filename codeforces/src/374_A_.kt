fun main() {
    val n = readln().toInt()
    val s = readln()
    var k = 0
    var r = ""
    s.replace("W*(B+)".toRegex()) { ++k; r += "${it.groupValues[1].length} "; "" }
    println(k)
    println(r)
}
