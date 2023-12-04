fun main() {
    val s = readln()
    val t = readln()
    val ss = s.toCharArray().sorted().asSequence()
    val ts = t.toCharArray().sorted().reversed().asSequence()
    val interleaved = ss.zip(ts).map { sequenceOf(it.first, it.second) }.flatten()
    println(interleaved.take(s.length).joinToString(""))
}
