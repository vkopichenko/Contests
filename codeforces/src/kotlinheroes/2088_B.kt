fun main() {
    repeat(readln().toInt()) {
        val n = readln().toInt()
        val s = readln()
        val eyeCount = s.count { it == '-' }
        val noseCount = s.count { it == '_' }
        val leftEyeCount = eyeCount / 2
        val rightEyeCount = eyeCount - leftEyeCount
        println(leftEyeCount.toLong() * noseCount * rightEyeCount)
    }
}
