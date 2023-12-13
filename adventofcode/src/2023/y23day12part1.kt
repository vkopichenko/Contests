fun main() {
    fun countArrangements(conditionRecord: String, groups: List<Int>): Long {
        val rec = conditionRecord.trimStart('.')
        if (rec.isEmpty()) return if (groups.isEmpty()) 1 else 0
        if (groups.isEmpty()) return if ('#' in rec) 0 else 1
        val extra = if (rec.startsWith('?')) countArrangements(rec.drop(1), groups) else 0
        if (rec.length < groups[0] || '.' in rec.take(groups[0]) || '#' == rec.getOrNull(groups[0])) return extra
        val nextPos = groups[0] + if ('?' == rec.getOrNull(groups[0])) 1 else 0
        return extra + countArrangements(rec.drop(nextPos), groups.drop(1))
    }
    generateSequence { readlnOrNull() }.map {
        it.substringBefore(' ') to it.substringAfter(' ').split(',').map(String::toInt)
    }.sumOf { (conditionRecord, damagedGroupCounts) ->
        countArrangements(conditionRecord, damagedGroupCounts).also(::println)
    }.let(::println)
}

