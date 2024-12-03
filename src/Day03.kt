fun main() {
    fun part1(input: List<String>): Long {
        var ans = 0L
        val regex = """mul\(\d+,\d+\)""".toRegex()
        for (line in input) {
            val matchResults = regex.findAll(line)
            for (matchResult in matchResults) {
                val (a, b) = matchResult.value.substringAfter("mul(").substringBefore(")").split(",")
                    .map { it.toLong() }
                ans += a * b
            }
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        val regex = """(mul\(\d{1,3},\d{1,3}\)|don't\(\)|do\(\))""".toRegex()
        var process = true
        for (line in input) {
            val matchResults = regex.findAll(line)
            for (matchResult in matchResults) {
                if (matchResult.value == "don't()") {
                    process = false
                    continue
                }
                if (matchResult.value == "do()") {
                    process = true
                    continue
                }
                val (a, b) = matchResult.value.substringAfter("mul(").substringBefore(")").split(",")
                    .map { it.toLong() }
                ans += a * b * process.int
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
//    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}