fun main() {

    data class Node(val x: Int, val y: Int, val size: Int, val used: Int, val avail: Int, val use: Int)

    fun String.toNode() = """/dev/grid/node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+(\d+)T\s+(\d+)%""".toRegex()
            .matchEntire(this).let { match ->
                val v = match!!.groupValues.drop(1).map(String::toInt)
                Node(v[0], v[1], v[2], v[3], v[4], v[5])
            }

    readLine()
    readLine()
/*
    val a = mutableListOf<Node>()
    while (true) {
        val line = readLine() ?: break
        if (line.isEmpty()) break
        a += line.toNode()
    }
*/
    val a = System.`in`.bufferedReader().lineSequence().takeWhile(String::isNotBlank).map(String::toNode)
    var count = 0
    a.forEachIndexed { i, n1 ->
        a.forEachIndexed { j, n2 ->
            if (i != j && n1.used > 0 && n1.used <= n2.avail) ++count
        }
    }
    println(count)
}


