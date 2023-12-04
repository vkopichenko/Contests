fun main() {
    val s = readln()
    var last = '\u0000'
    var count = 0
    s.forEach { c ->
        if (c != last) {
            if (count > 0) print("$last$count")
            count = 1
            last = c
        } else {
            ++count
        }
    }
    if (count > 0) print("$last$count")
}
