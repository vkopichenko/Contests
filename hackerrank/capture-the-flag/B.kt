import java.net.URL
import java.util.*
import javax.script.ScriptEngineManager

private fun readUrl(url: String) = Scanner(URL(url).openStream(), "UTF-8").useDelimiter("\\A").next()

private fun parseJson(json: String?): Map<String, String>
        = ScriptEngineManager().getEngineByName("nashorn").eval("JSON.parse('$json')") as Map<String, String>

fun main() {
    val newsList = mutableListOf<String>()
    parseJson(readUrl("https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/secret/key.json")).keys.forEach { key ->
//        println(line)
        val url = "https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/secret/secret_json/$key.json"
        val json = readUrl(url)
//        println(json)
        val news = parseJson(json)["news_title"]!!
//        println(news)
        newsList += news
    }
    newsList.sort()
    println(newsList.joinToString("\n"))
}
