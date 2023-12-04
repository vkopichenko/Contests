fun main() {
    System.`in`.bufferedReader().use {
        println(
                it.lineSequence().map {
                    it.splitToSequence(' ', '\t').filter(String::isNotBlank).map(String::toInt).run {
                        max() - min()
                    }
                }.sum()
        )
    }
}
