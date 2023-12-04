fun main() {

    val maxColors = 3

    data class RefInt(val value: Int, var ref: RefInt? = null) {
        fun toInt(): Int = ref?.toInt() ?: value
        fun ref(): RefInt = ref?.ref() ?: this
//        tailrec fun toInt(): Int = if (ref == null) value else ref!!.toInt()
    }

    data class T(val p: Int, val a: Int, val b: Int) {
        val nextColors = Array(maxColors + 1) { RefInt(-1) }
    }

    fun <T> Array<T>.copyTo(dest: Array<T>) {
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

    t.foldRightIndexed(Array(maxColors + 1) { RefInt(-1) }) { i, v, nextColors ->
        nextColors[v.a] = RefInt(i)
        nextColors[v.b] = RefInt(i)
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
            if (i == s) do { ++s } while (s < n && t[s].nextColors.all { it.toInt() != s })
            else {
                fun markColorUsed(col: Int) {
                    val ci = if (i < n - 1) t[i + 1].nextColors[col] else RefInt(-1)
                    t[i].nextColors[col].ref = ci.ref()
                }
                markColorUsed(t[i].a)
                markColorUsed(t[i].b)
            }
        }
//        println("$s, $i: " + ans.joinToString(" "))
//        println(t.joinToString("\n") {"$it ${it.nextColors.joinToString()}"})
    }
    println(ans.joinToString(" "))
}

