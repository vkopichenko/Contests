fun main() {

    val maxColors = 3

    data class T(val p: Int, val a: Int, val b: Int) {
        val nextColors = IntArray(maxColors + 1)
    }

    fun IntArray.copyTo(dest: IntArray) {
        System.arraycopy(this, 0, dest, 0, size)
    }
    
    val n = readln().toInt()
    val p = readln().split(' ').map(String::toInt)
    val a = readln().split(' ').map(String::toInt)
    val b = readln().split(' ').map(String::toInt)
    val m = readln().toInt()
    val c = readln().split(' ').map(String::toInt)

    val t = Array(n) { i -> T(p[i], a[i], b[i]) }
    t.sortBy { it.p }

/*
    val nextColors = IntArray(maxColors + 1) { 0 }
    for (i in n-1 downTo 0) {
        nextColors[t[i].a] = i
        nextColors[t[i].b] = i
        nextColors.copyTo(t[i].nextColors)
    }
*/
    t.foldRightIndexed(IntArray(maxColors + 1) { -1 }) { i, v, nextColors ->
        nextColors[v.a] = i
        nextColors[v.b] = i
        nextColors.copyTo(v.nextColors)
        nextColors
    }

//    println(t.joinToString("\n") {"$it ${it.nextColors.joinToString()}"})

    //    val ans = mutableListOf<Int>()
    val ans = java.util.ArrayList<Int>(m)
    var s = 0
    for (cc in c) {
        val i = if (s < n) t[s].nextColors[cc].toInt() else -1
        if (i == -1) {
            ans += -1
        } else {
            ans += t[i].p
            if (i == s) do { ++s } while (s < n && t[s].nextColors.all { it != s })
            else {
                fun markColorUsed(col: Int) {
                    val ci = if (i < n - 1) t[i + 1].nextColors[col] else -1
                    for (j in i downTo s) {
                        if (t[j].nextColors[col] != i) break
                        t[j].nextColors[col] = ci
                    }
                }
                markColorUsed(t[i].a)
                markColorUsed(t[i].b)
            }
        }
    }
    println(ans.joinToString(" "))
}

