fun main() {
    fun part1(input: List<String>): Long {
        return input.size * 1L
    }

    fun part2(input: List<String>): Long {
        return input.size * 1L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 11L)
//    check(part2(testInput) == 1L)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}