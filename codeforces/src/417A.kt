fun main() {
    val a = Array(4) { readln().split(' ').map { it == "1" } }
    val dtp = a.indices.any { a[it][3] && (a[it].subList(0, 3).any { it } || a[(it + 1) % 4][0] || a[(it + 3) % 4][2] || a[(it + 2) % 4][1]) }
    println(if (dtp) "YES" else "NO")
}
