fun main() {

    data class Node(var color: Int, val links: MutableList<Int> = mutableListOf())

    val n = readln().toInt()
    val tree = Array<Node>(n) { Node(0) }
    repeat(n - 1) {
        val (u, v) = readln().split(' ').map { it.toInt() - 1 }
        tree[u].links += v
        tree[v].links += u
    }
    readln().split(' ').map(String::toInt).forEachIndexed { i, c ->
        tree[i].color = c
    }

    fun traverse(from: Int, to: Int, rootCandidate: Int?): Int? {
        var rc = rootCandidate
        if (tree[from].color != tree[to].color) {
            if (rc == null) rc = to
            else if (rc != from) return -1
        }
        for (i in tree[to].links) {
            if (i == from) continue
            val r = traverse(to, i, rc)
            if (r == -1) return -1
            if (r != null) {
                if (rc != null && r != rc) return -1
                else rc = r
            }
        }
        return rc
    }

    val root = traverse(0, tree[0].links[0], null)

    if (root != -1) {
        println("YES")
        println((root ?: 0) + 1)
    } else {
        println("NO")
    }
}
