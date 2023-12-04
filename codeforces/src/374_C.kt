import java.util.*

data class Road(val to: Int, val time: Int)
data class Showplace(
        val roads: MutableList<Road> = mutableListOf(),
        val backRoads: MutableList<Road> = mutableListOf(),
        val times: HashMap<Int, Int> = HashMap(),
        var backTime: Int = Int.MAX_VALUE
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
    showplaces.forEach { it.roads.sortBy { it.time } }
    showplaces[1].times.put(1, 0)
    showplaces[n].backTime = 0
    val preQueue = ArrayDeque<Int>()
    preQueue += n
    while (!preQueue.isEmpty()) {
        val curPlace = preQueue.poll()!!
        val showplace = showplaces[curPlace]
        val curTime = showplace.backTime
        for ((to, roadTime) in showplace.backRoads) {
            val nextTime = curTime + roadTime
            if (nextTime <= timeLimit) {
                if (showplaces[to].backTime == Int.MAX_VALUE) preQueue += to
                showplaces[to].backTime = nextTime.coerceAtMost(showplaces[to].backTime)
            }
        }
    }
/*
    showplaces.forEachIndexed { i, it ->
        println("$i: $it")
    }
*/
    val queue = ArrayDeque<Step>()
    queue += Step(1, 1)
    while (!queue.isEmpty()) {
        val curStep = queue.poll()!!
        val showplace = showplaces[curStep.place]
        val curPlaces = curStep.visitedCount
        val curTime = showplace.times[curPlaces]!!
        val nextPlaces = curPlaces + 1
        for ((to, roadTime) in showplace.roads) {
            val nextTime = curTime + roadTime
            val nextPlace = showplaces[to]
            if (nextPlace.backTime <= timeLimit - nextTime) {
                nextPlace.times.compute(nextPlaces) { i, prevTime ->
                    if (prevTime == null) queue += Step(to, nextPlaces)
                    if (prevTime == null || nextTime < prevTime) nextTime else prevTime
                }
            }
        }
    }
/*
    showplaces.forEachIndexed { i, it ->
        println("$i: ${it.times}")
    }
*/
    val best = showplaces[n].times.maxBy { e -> e.key }!!
    val bestPlaces = best.key
    val bestTime = best.value
    val backPath = mutableListOf<Int>()
    backPath += n
    var cur = n
    var restSteps = bestPlaces
    var restTime = bestTime
    while (--restSteps > 0) {
        val next = showplaces[cur].backRoads.find { showplaces[it.to].times[restSteps] == restTime - it.time }!!
        cur = next.to
        restTime -= next.time
        backPath += cur
    }
    println(bestPlaces)
    println(backPath.asReversed().joinToString(" "))
}
