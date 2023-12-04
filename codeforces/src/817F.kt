import java.util.*

// TODO: SO
private fun <T, R> Collection<T>.mapPairs(transform: (T, T) -> R): List<R> {
    if (size <= 1) return emptyList()
    val r = ArrayList<R>(size - 1)
    reduce { a, b -> r += transform(a, b); b }
    return r
}

private fun <T, R : Any> Sequence<T>.mapPairs(transform: (T, T) -> R): Sequence<R> {
    val i = iterator()
    if (!i.hasNext()) return emptySequence()
    var last = i.next()
    return generateSequence {
        if (!i.hasNext()) null
        else i.next().run { transform(last, this).also { last = this } }
    }
}

fun main() {

    fun Long.coerceAtMost(value: Long?) = if (value != null && this > value) value else this
    fun Long.coerceAtLeast(value: Long?) = if (value != null && this < value) value else this

    // TODO: implements MutableSet<T>
    class RangeSet {
        private val map: NavigableMap<Long, LongRange> = TreeMap()

        private operator fun NavigableMap<Long, LongRange>.plusAssign(e: LongRange) {
            this.put(e.start, e)
        }

        operator fun plusAssign(r: LongRange) {
            val sm = map.subMap(
                    map.lowerEntry(r.start)?.value?.takeIf { it.endInclusive >= r.start - 1 }?.start ?: r.start, true,
                    r.endInclusive + 1, true)
            val nr = r.start.coerceAtMost(sm.firstEntry()?.value?.start)..r.endInclusive.coerceAtLeast(sm.lastEntry()?.value?.endInclusive)
            sm.clear()
            map += nr
        }

        operator fun minusAssign(r: LongRange) {
            val sm = map.subMap(
                    map.lowerEntry(r.start)?.value?.takeIf { it.endInclusive >= r.start }?.start ?: r.start, true,
                    r.endInclusive, true)
            val f = sm.firstEntry()?.value
            val l = sm.lastEntry()?.value
            sm.clear()
            if (f != null && f.start < r.start) map += f.start until r.start
            if (l != null && r.endInclusive < l.endInclusive) map += r.endInclusive.inc()..l.endInclusive
        }

        operator fun timesAssign(r: LongRange) {
            val sm = map.subMap(
                    map.lowerEntry(r.start)?.value?.takeIf { it.endInclusive >= r.start - 1 }?.start ?: r.start, true,
                    r.endInclusive + 1, true)
            var nr = sm.values.mapPairs { a, b -> a.endInclusive + 1 until b.start }
            val aux = mutableListOf<LongRange>()
            val f = sm.firstEntry()?.value
            when {
                f == null -> null
                f.endInclusive == r.start - 1 -> {
                    aux += f.start.rangeTo(sm.higherEntry(r.start)?.value?.start?.dec() ?: r.endInclusive)
                    if (nr.isNotEmpty()) nr = nr.subList(1, nr.size)
                }
                f.start < r.start -> {
                    aux += f.start until r.start
                }
                f.start in r.start.inc()..r.endInclusive -> {
                    aux += r.start until f.start
                }
            }
            val l = sm.lastEntry()?.value
            when {
                l == null -> null
                l.start == r.endInclusive + 1 -> {
                    aux += (sm.lowerEntry(r.endInclusive)?.value?.endInclusive?.inc() ?: r.start).rangeTo(l.endInclusive)
                    if (nr.isNotEmpty()) nr = nr.subList(0, nr.size - 1)
                }
                l.endInclusive > r.endInclusive -> {
                    aux += r.endInclusive.inc().rangeTo(l.endInclusive)
                }
                l.endInclusive in r.start until r.endInclusive -> {
                    aux += l.endInclusive.inc().rangeTo(r.endInclusive)
                }
            }
            if (f == null && l == null) aux += r
            sm.clear()
            nr.forEach { 
                map += it
            }
            aux.forEach {
                map += it
            }
        }

        fun mex() = map.firstEntry()?.value.let { if (it != null && it.start == 1L) it.endInclusive + 1 else 1 }

        override fun toString() = map.values.joinToString()
    }

    with(Scanner(System.`in`)) {
        val n = nextInt()
        val s = RangeSet()
        repeat(n) {
            val op = nextInt()
            val lr = nextLong()..nextLong()
            when (op) {
                1 -> s += lr
                2 -> s -= lr
                3 -> s *= lr
            }
//            println(s)
            println(s.mex())
        }
    }
}


