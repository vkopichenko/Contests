fun main() {
    val n = readln().toInt()
    val a = readln().split(' ').map(String::toInt)
    val b = a.mapIndexed { i, v ->  if (i and 1 xor (n and 1 xor 1 and i/((n+1)/2)) == 0) a[n-i-1] else a[i]}
    println(b.joinToString(" "))
}
