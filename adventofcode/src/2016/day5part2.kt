fun main() {

    fun md5(text: String) = java.security.MessageDigest.getInstance("MD5").digest(text.toByteArray())
    fun hash(text: String) = md5(text).joinToString("") { "%02x".format(it) }

//    val input = readLine()
//    val input = "abc"
    val input = "abbhdwsy"
    var index = 0
    val password = CharArray(8) { '-' }
    repeat(8) {
        while (true) {
            val hash = hash(input + index++)
            if (hash.startsWith("00000")) {
                try {
                    val i = hash[5].toString().toInt()
                    val c = hash[6]
                    if (i in 0 until password.size && password[i] == '-') {
                        password[i] = c
                        println(password)
                        break
                    }
                } catch (ignored: NumberFormatException) {
                }
            }
        }
    }
//    println(password)
}


