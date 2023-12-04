import java.net.URL
import java.util.*

private fun readUrl(url: String) = Scanner(URL(url).openStream(), "UTF-8").useDelimiter("\\A").next()

fun main() {
    val base = "https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/infinite/"
    val phrases = mutableListOf<String>()
    val visited = mutableSetOf<String>()
    fun rec(url: String) {
        println(url)
        visited += url
        val html = readUrl("$base$url")
        println(html)
        """<b>Secret Phrase: </b>\s*([^<]+)""".toRegex(RegexOption.MULTILINE).findAll(html).forEach {
            phrases += it.groupValues[1]
        }
        """<a href="?([^">]+)"?>\w+</a>""".toRegex().findAll(html).forEach {
            val u = it.groupValues[1]
            if (!visited.contains(u)) rec(u)
        }
    }
    rec("qds.html")
    phrases.sort()
    println(phrases.joinToString("\n"))
}
