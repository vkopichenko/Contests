fun main() {
    val s = """HTTP/1.1 200 OK
< Date: Sun, 26 Apr 2020 09:45:56 GMT
< Content-Type: application/json; charset=utf-8
< Content-Length: 120
< Connection: keep-alive
< Vary: Accept-Encoding
< Content-Security-Policy: default-src 'self'; script-src 'self'
< Strict-Transport-Security: max-age=31536000; includeSubDomains; preload
< X-Frame-Options: DENY
< X-Xss-Protection: 1; mode=block
< Strict-Transport-Security: max-age=604800
< X-Frame-Options: DENY
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Content-Security-Policy: default-src 'self';
< Strict-Transport-Security: max-age=31536000;
< Content-Security-Policy-Report-Only: default-src https:; script-src https: 'unsafe-eval' 'unsafe-inline'; style-src https: 'unsafe-inline'; img-src https: data:; font-src https: data:; report-uri /csp-report
< X-Frame-Options: DENY
< X-XSS-Protection: 1; mode=block
< X-Content-Type-Options: nosniff
< Referrer-Policy: no-referrer-when-downgrade
< Content-Security-Policy: default-src 'self'
< Strict-Transport-Security: max-age=604800
< X-Frame-Options: “DENY”
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Content-Security-Policy-Report-Only: default-src 'self'; script-src 'self'
< X-Frame-Options: SAMEORIGIN
< Content-Security-Policy: default-src 'self'; script-src 'self'
< Set-Cookie: TS01268848=01b5013c85e53049e3c6543bf41f76f362e6d50b5087e35253f73a625f49ccb84a8a1d60ed9ebc1cb9f8e5e827284b9a90c44111df; Path=/; Secure; HTTPOnly
<
"""
    var i = 0
    while (i < s.length) {
        val c = s[i]
        if (c < ' ' && c != '\t' && c != '\r' && c != '\n' || c >= 127.toChar()) {
            println("$i: $c")
        }
        ++i
    }

}