fun main() {
    data class Road (val to: Int, val time: Int)
    data class Showplace(
            val roads: MutableList<Road> = mutableListOf(),
            val backRoads: MutableList<Road> = mutableListOf(),
            val times: HashMap<Int, Int> = HashMap()
    )

    val (n, m, timeLimit) = readln().split(' ').map { it.toInt() }
    val showplaces = Array(n + 1, { Showplace()})
    repeat(m) {
        val (from, to, time) = readln().split(' ').map { it.toInt() }
        showplaces[from].roads += Road(to, time)
        showplaces[to].backRoads += Road(from, time)
    }
    showplaces[1].times.put(1, 0)
    repeat(m) {
        for (showplace in showplaces) {
            if (!showplace.times.isEmpty()) {
                for ((to, roadTime) in showplace.roads) {
                    val nextPlace = showplaces[to]
                    for ((curN, curTime) in showplace.times) {
                        nextPlace.times.merge(curN + 1, curTime + roadTime, Int::coerceAtMost)
                    }
                }
            }
        }
    }
/*
    showplaces.forEachIndexed { i, it ->
        println("$i: ${it.times}")
    }
*/
    val best = showplaces[n].times.asSequence().filter { e -> e.value <= timeLimit }.maxBy { e -> e.key }!!
    val bestPlaces = best.key
    val bestTime = best.value
    println(bestPlaces)
    val backPath = mutableListOf<Int>()
    backPath += n
    var cur = n
    var restSteps = bestPlaces
    var restTime = bestTime
    while (--restSteps > 0) {
        val next = showplaces[cur].backRoads.find { showplaces[it.to].times[ restSteps ] == restTime - it.time }!!
        cur = next.to
        restTime -= next.time
        backPath += cur
    }
    println(backPath.asReversed().joinToString(" "))
}

