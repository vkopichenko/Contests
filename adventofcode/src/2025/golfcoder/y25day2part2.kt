fun main() = println(
    readLine()!!.splitToSequence(',')
        .flatMap { it.split('-').run { get(0).toLong()..get(1).toLong() } }
        .filter {
            it.toString().run {
                (1..length / 2).any {
                    // if (length % it != 0) return@any false
                    chunked(it).run {
                        drop(1).all { it == elementAt(0) }
                    }
                }
            }
        }.sum()
)
