fun main() {
    fun checkXMAS(input: List<String>, x: Int, y: Int, xDir: Int, yDir: Int): Boolean {
        val checkString = "XMAS"
        for (i in 0..3) {
            try {
                if (input[x + xDir * i][y + yDir * i] != checkString[i]) {
                    return false
                }
            } catch (e: IndexOutOfBoundsException) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Long {
        var ans = 0L
        val directions =
            listOf(Pair(0, 1), Pair(0, -1), Pair(-1, 0), Pair(1, 0), Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))
        for (i in input.indices) {
            for (j in input[i].indices) {
                directions.forEach { (xDir, yDir) ->
                    ans += checkXMAS(input, i, j, xDir, yDir).int
                }
            }
        }
        return ans
    }

    fun checkX_MAS(input: List<String>, x: Int, y: Int): Boolean {
        val directions = listOf(Pair(1, 1), Pair(1, -1), Pair(-1, -1), Pair(-1, 1))
        var works = 0
        directions.forEach { (xDir, yDir) ->
            works += try {
                ((input[x][y] == 'A') &&
                        (input[x + xDir][y + yDir] == 'M' && input[x - xDir][y - yDir] == 'S')
                        ).int
            } catch (e: IndexOutOfBoundsException) {
                0
            }
        }
        return works == 2
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (i in input.indices) {
            for (j in input[i].indices) {
                ans += checkX_MAS(input, i, j).int
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 18L)
    check(part2(testInput) == 9L)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}