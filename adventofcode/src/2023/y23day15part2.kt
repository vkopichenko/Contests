fun main() {
    val boxes = Array(256) { mutableMapOf<String, Int>() }
    generateSequence { readlnOrNull() }.flatMap { it.splitToSequence(',') }.forEach { command ->
        val (label, op, focal) = """(\w+)([=-])(\d*)""".toRegex().matchEntire(command)!!.destructured
        val hash = label.fold(0.toUByte()) { acc, char -> ((acc.toInt() + char.code) * 17).toUByte() }.toInt()
        when (op) {
            "-" -> boxes[hash].remove(label)
            "=" -> boxes[hash].put(label, focal.toInt())
        }
    }
    boxes.asSequence().flatMapIndexed { i, box ->
        box.values.asSequence().mapIndexed { j, focal ->
            (i + 1L) * (j + 1) * focal
        }
    }.sum().let(::println)
}
