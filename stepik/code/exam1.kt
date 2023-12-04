fun main() {
//    println(generateSequence(1, Int::inc).filter { x -> 18*x >= 1009000 }.first())
    println(generateSequence(1, Int::inc).filter {x -> x != 2018 && x != 2500}.first { x -> x/(x-2018)-(x-500)/(x-2500) >= 0 })
}
