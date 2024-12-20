import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {
    val patterns = readln().split(", ")
    readln()
    val designs = generateSequence { readlnOrNull() }.toList()

    val minPrefix = patterns.minOf { it.length }
    val maxPrefix = min(8, patterns.maxOf { it.length })
    fun CharSequence.prefixHash(len: Int) =
        asSequence().take(len).fold(0L) { a, c -> a shl 8 xor c.code.toLong() }
    val patternsByHash = patterns.groupBy { it.prefixHash(maxPrefix) }

    measureTimedValue { // part 1
        val memo = HashMap<String, Boolean>()
        fun possible(str: String): Boolean = memo.getOrPut(str) {
            if (str.isEmpty()) true
            else {
                val len = min(maxPrefix, str.length)
                var hash = str.prefixHash(len)
                (len downTo minPrefix).any {
                    patternsByHash[hash.also { hash = hash ushr 8 }]?.any { pattern ->
                        (pattern.length <= maxPrefix || str.startsWith(pattern))
                            && possible(str.substring(pattern.length, str.length))
                    } ?: false
                }
            }
        }
        designs.count { possible(it) }
    }.also(::println)

    measureTimedValue { // part 2
        val memo = HashMap<String, Long>()
        fun countPossible(str: String): Long = memo.getOrPut(str) {
            if (str.isEmpty()) 1L
            else {
                val len = min(maxPrefix, str.length)
                var hash = str.prefixHash(len)
                (len downTo minPrefix).sumOf {
                    patternsByHash[hash.also { hash = hash ushr 8 }]?.sumOf { pattern ->
                        if (pattern.length > maxPrefix && !str.startsWith(pattern)) 0
                        else countPossible(str.substring(pattern.length, str.length))
                    } ?: 0
                }
            }
        }
        designs.sumOf { countPossible(it) }
    }.also(::println)
}
