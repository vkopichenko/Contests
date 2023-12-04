fun main() {
    println(generateSequence(1, Int::inc).take(readln().toInt()).map {
        when {
            it % 15 == 0 -> "FizzBuzz"
            it % 5 == 0 -> "Buzz"
            it % 3 == 0 -> "Fizz"
            else -> it.toString()
        }
    }.joinToString(" "))
}
