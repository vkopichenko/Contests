fun main() {
    val (n, m, k) = readln().split(' ').map { it.toInt() }
    val colors = readln().split(' ').map { it.toInt() }.toIntArray()
    assert(k == colors.size)
    val pairs = Array(m, { readln().split(' ').map { it.toInt() }.sorted().run { Pair(this[0], this[1]) } })
    pairs.sortBy { it.first }
//    pairs.sortWith(compareBy({ it.first }, { it.second }))
    val colorSets = IntArray(n + 1) { 0 }
    var setNumber = 0
    for ((from, to) in pairs) {
        if (colorSets[from] == 0) {
            setNumber++
            colorSets[from] = setNumber
        }
        if (colorSets[to] == 0) {
            colorSets[to] = colorSets[from]
        } else {
            colorSets.forEachIndexed { i, v ->
                if (v == colorSets[to]) colorSets[i] = colorSets[from]
            }
        }
    }
//    println(colorSets.toList())
    var repaintCount = 0
    for ((i, list) in colorSets.asSequence().filter { it != 0 }
            .mapIndexed { i, v -> v to i }.groupBy({ it.first }, { it.second })) {
//        println(list)
        val groupedList = list.groupBy { colors[it] }
//        println(groupedList)
        repaintCount += list.size - groupedList.maxBy { it.value.size }!!.value.size
    }
    println(repaintCount)
}
