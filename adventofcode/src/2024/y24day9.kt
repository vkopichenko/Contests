import java.util.*
import kotlin.time.measureTimedValue

fun main() {
    data class File(val offset: Int, val size: Int, val gapSize: Int) {
        val tailOffset get() = offset + size - 1
        val gapTailOffset get() = tailOffset + gapSize
    }

    val diskMap = readln().map { it.digitToInt() }
    val files = diskMap.run {
        var offset = 0
        chunked(2) {
            val size = it.get(0)
            val free = it.getOrElse(1) { 0 }
            File(offset, size, free)
                .also { offset += size + free }
        }
    }

    measureTimedValue { // part 1
        var fileId = 0
        var tailFileId = files.indices.last
        var tail = files[tailFileId].tailOffset
        var moving = false
        (0..<files.sumOf { it.size }).sumOf { pos ->
            (pos.toLong() * if (moving) tailFileId else fileId).also {
                if (moving) {
                    if (tail > files[tailFileId].offset) --tail
                    else tail = files[--tailFileId].tailOffset
                }
                if (pos == files[fileId].tailOffset) moving = true
                if (pos == files[fileId].gapTailOffset) { ++fileId; moving = false }
            }
        }
    }.also(::println)

    measureTimedValue { // part 2
        val moved = files.withIndex().associateTo(TreeMap()) { it.value.offset to it.index }.apply {
            files.indices.reversed().forEach { moveId ->
                val moveOffset = entries.first { it.value == moveId }.key
                entries.asSequence().takeWhile { it.key <= moveOffset }.zipWithNext().firstOrNull { (a, b) ->
                    b.key - a.key - files[a.value].size >= files[moveId].size
                }?.first?.run {
                    put(key + files[value].size, remove(moveOffset)!!)
                }
            }
        }
        (0..files.last().tailOffset).sumOf { pos ->
            val (nearestFileOffset, nearestFileId) = moved.floorEntry(pos)
            val posFileId = nearestFileId.takeIf { pos < nearestFileOffset + files[nearestFileId].size }
            pos.toLong() * (posFileId ?: 0)
        }
    }.also(::println)

    measureTimedValue { // part 2, wrong interpretation )
        var fileId = 0
        var moving = false
        var movedCount = 0
        var movingFileId = 0
        val forwardFileIds = files.indices.toList().let(::ArrayDeque)
        (0..files.last().tailOffset).sumOf { pos ->
            val posFileId = movingFileId.takeIf { moving }
                ?: fileId.takeIf { pos <= files[fileId].tailOffset }
            //print(posFileId ?: '.')
            (pos.toLong() * (posFileId ?: 0)).also {
                if (pos == files[fileId].offset) forwardFileIds.remove(fileId)
                if (moving && ++movedCount == files[movingFileId].size) moving = false
                forwardFileIds.firstOrNull()?.let { nextUnmovedFileId ->
                    val gapTail = files[nextUnmovedFileId].offset - 1
                    if (!moving && (pos in files[fileId].tailOffset until gapTail)) {
                        val remainingSpace = gapTail - pos
                        forwardFileIds.run {
                            lastOrNull { files[it].run { size <= remainingSpace } }?.also(::remove)
                        }?.also { movingFileId = it; movedCount = 0; moving = true }
                    }
                    if (pos == gapTail) fileId = nextUnmovedFileId
                }
            }
        }
    }.also(::println)
}
