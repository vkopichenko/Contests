/*
import java.util.*

class Road private constructor() {
    companion object {
        operator fun invoke(to: Int, time: Int) = to.toLong() shl 32 + time
    }
}
operator fun Long.component1() = (this shr 32).toInt()
operator fun Long.component2() = this.toInt()

//data class Road(val to: Int, val time: Int)
data class Showplace(
        val roads: MutableList<Long> = mutableListOf(),
        val backRoads: MutableList<Long> = mutableListOf(),
        val times: HashMap<Int, Int> = HashMap()
)
data class Step(val place: Int, val visitedCount: Int)

fun main() {
    val (n, m, timeLimit) = readln().split(' ').map { it.toInt() }
    val showplaces = Array(n + 1, { Showplace() })
    repeat(m) {
        val (from, to, time) = readln().split(' ').map { it.toInt() }
        showplaces[from].roads += Road(to, time)
        showplaces[to].backRoads += Road(from, time)
    }
    showplaces.forEach { it.roads.sortBy { it.component2() } }
    showplaces[1].times.put(1, 0)
    val queue = ArrayDeque<Step>()
    queue += Step(1, 1)
    while (!queue.isEmpty()) {
        val curStep = queue.poll()!!
        val showplace = showplaces[curStep.place]
        val curPlaces = curStep.visitedCount
        val curTime = showplace.times[curPlaces]!!
        for ((to, roadTime) in showplace.roads) {
            val nextTime = curTime + roadTime
            if (nextTime <= timeLimit) {
                val nextPlaces = curPlaces + 1
                showplaces[to].times.compute(nextPlaces) { i, prevTime ->
//                    if (prevTime == null) queue += Step(to, nextPlaces)
//                    if (prevTime == null || nextTime < prevTime) nextTime else prevTime
                    if (prevTime == null || nextTime < prevTime) {
                        queue += Step(to, nextPlaces)
                        nextTime
                    } else {
                        prevTime
                    }
                }
            }
        }
    }
*/
/*
    showplaces.forEachIndexed { i, it ->
        println("$i: ${it.times}")
    }
*//*

    val best = showplaces[n].times.asSequence().filter { e -> e.value <= timeLimit }.maxBy { e -> e.key }!!
    val bestPlaces = best.key
    val bestTime = best.value
    val backPath = mutableListOf<Int>()
    backPath += n
    var cur = n
    var restSteps = bestPlaces
    var restTime = bestTime
    while (--restSteps > 0) {
        val next = showplaces[cur].backRoads.find { showplaces[it.component1()].times[restSteps] == restTime - it.component2() }!!
        cur = next.component1()
        restTime -= next.component2()
        backPath += cur
    }
    println(bestPlaces)
    println(backPath.asReversed().joinToString(" "))
}

*/
