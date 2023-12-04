fun main() {
    var n = readln().toLong()
    n += 1
    var p = 1L
    while (n > p && p > 0) {
        var np = n / p
        if (np / 10 % 10 == np % 10) {
            np += 1
            n = np * p
            var t = p
            if (np % 10 == 0L) t *= 10
            while (t > 0) { t /= 100; n += t }
            continue
        }
        p *= 10
    }
    println(n)
}
