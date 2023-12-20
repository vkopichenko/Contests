fun main() {
    data class Rule(val property: String, val op: String, val value: Int?, val workflow: String)
    val workflows = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }.map { line ->
        val name = line.substringBefore('{')
        val rules = line.substring(name.length + 1, line.length - 1).splitToSequence(',').map { rule ->
            """((\w+)([<>])(\d+):)?(\w+)""".toRegex().matchEntire(rule)!!.destructured.let {
                (_, prop, op, value, workflow) -> Rule(prop, op, value.takeIf(String::isNotEmpty)?.toInt(), workflow)
            }
        }.toList()
        name to rules
    }.toMap()
    generateSequence { readlnOrNull() }.map { line ->
        line.splitToSequence(',', '{', '}').filter { it.isNotEmpty() }.map {
            val (prop, value) = it.split('=')
            prop to value.toInt()
        }.toMap()
    }.filter { part ->
        generateSequence("in") { workflow ->
            workflows.getValue(workflow).firstNotNullOf { rule ->
                rule.workflow.takeIf {
                    when (rule.op) {
                        "<" -> part.getValue(rule.property) < rule.value!!
                        ">" -> part.getValue(rule.property) > rule.value!!
                        else -> true
                    }
                }
            }
        }.first { it in setOf("A", "R") } == "A"
    }.sumOf {
        it.values.sum()
    }.let(::println)
}
