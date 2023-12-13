fun main() {
    val cache = mutableMapOf<Pair<String, List<Int>>, Long>()
    fun countArrangements(conditionRecord: String, groups: List<Int>): Long =
        cache.getOrPut(conditionRecord to groups) {
            val rec = conditionRecord.trimStart('.')
            when {
                rec.isEmpty() -> if (groups.isEmpty()) 1 else 0
                groups.isEmpty() -> if ('#' in rec) 0 else 1
                rec.length < groups.sum() + groups.size - 1 || rec.count { it in "#?" } < groups.sum() -> 0
                else -> {
                    val extra = if (rec.startsWith('?')) countArrangements(rec.drop(1), groups) else 0
                    if (rec.length < groups[0] || '.' in rec.take(groups[0]) || '#' == rec.getOrNull(groups[0])) extra
                    else {
                        val nextPos = groups[0] + if ('?' == rec.getOrNull(groups[0])) 1 else 0
                        extra + countArrangements(rec.drop(nextPos), groups.drop(1))
                    }
                }
            }
        }

    fun CharSequence.repeat(n: Int, delimiter: String = ""): String =
        generateSequence(this) { it }.take(n).joinToString(delimiter) { it }

    generateSequence { readlnOrNull() }.map {
        it.substringBefore(' ').repeat(5, "?") to
                it.substringAfter(' ').repeat(5, ",").split(',').map(String::toInt)
    }.sumOf { (conditionRecord, damagedGroupCounts) ->
        countArrangements(conditionRecord, damagedGroupCounts)
    }.let(::println)
}

