import kotlin.time.measureTime

fun main() {
    fun solve(operands: List<Long>, curSum: Long, res: Long, part2: Boolean = false): Boolean {
        if (operands.isEmpty()) return (curSum == res)
        if (curSum > res) return false

        val newList = operands.toMutableList()
        val fi = newList.removeAt(0)
        return if (!part2) {
            (solve(newList.toList(), curSum + fi, res) || solve(newList.toList(), curSum * fi, res))
        } else {
            (solve(newList.toList(), curSum + fi, res, part2)
                    || solve(newList.toList(), curSum * fi, res, part2)
                    || solve(newList.toList(), "$curSum$fi".toLong(), res, part2))
        }
    }

    fun part1(input: List<String>): Pair<Long, Long> {
        var ans1 = 0L
        var ans2 = 0L
        input.forEach { line ->
            val (a, b) = line.split(": ")
            val res = a.toLong()
            val operands = b.split(" ").map { it.toLong() }
            val newList = operands.toMutableList()
            newList.removeAt(0)
            if (solve(newList.toList(), operands[0], res)) {
                ans1 += res
            }
            if (solve(newList.toList(), operands[0], res, true)) {
                ans2 += res
            }
        }
        return Pair(ans1, ans2)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == Pair(3749L, 11387L))

// clear any global variables

    val input = readInput("Day00")
    measureTime {
        part1(input).println()
    }.println()
}
