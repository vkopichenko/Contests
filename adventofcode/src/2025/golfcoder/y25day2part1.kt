fun main() = println(
    readLine()!!.splitToSequence(",")
        .flatMap { it.split("-").run { get(0).toLong()..get(1).toLong() } }
        .filter {
            it.toString().run { take(length / 2) == drop(length / 2) }
        }.sum()
)
