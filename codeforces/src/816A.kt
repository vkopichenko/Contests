fun main() {
    val ts = readln()
    val t = ts.split(':').let { it[0].toInt()*60 + it[1].toInt() }
    val s = generateSequence(t, Int::inc)
    println(s.first { val h = it / 60 % 24; val m = it % 60; /*println("$h:$m");*/ h % 10 == m / 10 && h / 10 == m % 10 } - t)
}
