import kotlin.math.abs

fun main() {
    fun ifSafe(row: List<Long>): Long {
        val first = row[0] > row[1]
        for (i in 1 until row.size) {
            if (first == (row[i-1] > row[i])) {
                if(abs(row[i-1] - row[i]) in 1..3){
                    continue
                }
            }
            return 0
        }
        return 1
    }

    fun part1(input: List<String>): Long {
        var ans = 0L
        input.forEach{ line ->
            val row = line.split(" ").map { it.toLong() }
            ans += ifSafe(row)
        }
        return ans * 1L
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        input.forEach{ line ->
            val row = line.split(" ").map { it.toLong() }
            val safeArray = Array(row.size + 1) { 0L }
            safeArray[row.size] = ifSafe(row)
            for (i in 0 until row.size) {
                val newRow = row.toMutableList()
                newRow.removeAt(i)
                safeArray[i] = ifSafe(newRow)
            }
            ans += safeArray.max()
        }
        return ans * 1L    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 2L)
    check(part2(testInput) == 4L)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}