fun main() {
    fun part1(input: List<String>): Int {
        val towels = input[0].split(", ").toSortedSet()
        return input.subList(2, input.size).sumOf { line ->
            val okTillHere = Array(line.length+1) { false }
            okTillHere[0] = true
            for (j in line.indices) {
                for (i in 0..j) {
                    if (!okTillHere[i-1+1]) continue
                    if (line.substring(i, j+1) in towels) {
                        okTillHere[j+1] = true
                        break
                    }
                }
            }
            okTillHere.last().int//.also { println("$line: $it") }
        }
    }
    fun part2(input: List<String>): Long {
        val towels = input[0].split(", ").toSortedSet()
        return input.subList(2, input.size).sumOf { line ->
            val okTillHere = Array(line.length+1) { 0L }
            okTillHere[0] = 1
            for (j in line.indices) {
                for (i in 0..j) {
                    if (line.substring(i, j+1) in towels) {
                        okTillHere[j+1] += okTillHere[i-1+1]
                    }
                }
            }
            okTillHere.last()//.also { println("$line: $it") }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
