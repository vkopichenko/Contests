fun main() {

    fun md5(text: String) = java.security.MessageDigest.getInstance("MD5").digest(text.toByteArray())
    fun hash(text: String) = md5(text).joinToString("") { it.toString(16).padStart(2, '0') }

//    val input = readLine()
//    val input = "abc"
    val input = "abbhdwsy"
    var index = 0
    repeat(8) {
        while (true) {
            val hash = hash(input + index++)
            if (hash.startsWith("00000")) {
                print(hash[5])
                break
            }
        }
    }
}


