// https://stepik.org/lesson/266817/step/6?unit=247784

fun main() {
    val s = readln()
    val midPos = s.length / 2
    val midChar = s[midPos]
    val pos = s.indices.find { it != midPos && s[it] == midChar }
    if (pos != null) println(pos + 1)
    else println(0)
}
