fun main() {
    val n = readln().toInt()
    val s = readln()
    var countInside = 0
    val noPar = s.replace("""\((.*?)\)""".toRegex()) {
        countInside += it.groupValues[1].splitToSequence('_').filter { it.length > 0 }.count()
        "_"
    }
    val maxLengthOutside = noPar.splitToSequence('_').map {it.length}.max() ?: 0
    println("$maxLengthOutside $countInside")
}
