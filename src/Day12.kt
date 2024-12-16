import java.lang.IndexOutOfBoundsException

fun main() {
    fun part1(input: List<String>): Long {
        val vis = Array(input.size) { Array(input[0].length) { false } }
        var ans = 0L
        fun traverse(i: Int, j: Int): Pair<Long, Long> {
            if (vis[i][j]) return 0L to 0L
            vis[i][j] = true

            var area = 1L
            var perimeter = 0L
            listOf(0 to -1, 1 to 0, -1 to 0, 0 to 1).forEach { (a, b) ->
                try {
                    if (input[i][j] == input[i + a][j + b]) {
                        val p = traverse(i + a, j + b)
                        area += p.first
                        perimeter += p.second
                    } else {
                        perimeter += 1
                    }
                } catch (e: IndexOutOfBoundsException) {
                    perimeter += 1
                }
            }
            return Pair(area, perimeter)
        }
        for (i in input.indices) {
            for (j in input.indices) {
                traverse(i, j).also { ans += it.first * it.second }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val vis = Array(input.size) { Array(input[0].length) { false } }
        var ans = 0L

        fun traverse(i: Int, j: Int): Pair<Long, List<Pair<Int, Int>>> {
            if (vis[i][j]) return 0L to emptyList()
            vis[i][j] = true
            val corners = mutableListOf<Pair<Int, Int>>()
            var area = 1L
            listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0).forEach { (a, b) ->
                try {
                    if (input[i][j] == input[i + a][j + b]) {
                        val p = traverse(i + a, j + b)
                        area += p.first
                        corners.addAll(p.second)
                    }
                } catch (_: IndexOutOfBoundsException) {
                }
            }
            listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0, 0 to -1).zipWithNext().forEach { (a, b) ->
                var neighbors = 0
                listOf(a, b).forEach { p ->
                    try {
                        if (input[i][j] == input[i + p.first][j + p.second]) {
                            neighbors += 1
                        }
                    } catch (_: IndexOutOfBoundsException) {
                    }
                }
                if (neighbors == 0) {
                    corners.add(i + maxOf(a.first, b.first) to j + maxOf(a.second, b.second))
                }
                if (neighbors == 2) {
                    if (input[i][j] != input[i + a.first + b.first][j + a.second + b.second]) {
                        corners.add(i + maxOf(a.first, b.first) to j + maxOf(a.second, b.second))
                    }
                }
            }
            return Pair(area, corners)
        }
        for (i in input.indices) {
            for (j in input[i].indices) {
                traverse(i, j).also { ans += it.first * it.second.size }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 1930L)
    check(part2(testInput) == 1206L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
