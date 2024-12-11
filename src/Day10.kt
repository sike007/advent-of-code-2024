import java.lang.IndexOutOfBoundsException
import kotlin.time.measureTime

fun main() {
    val vis = mutableSetOf<Pair<Int,Int>>()
    fun traverse(input: List<String>, i: Int, j: Int, ch: Char) {
        try {
            if ((input[i][j] - ch) == 1) {
                if (input[i][j] == '9') {
                    vis.add(Pair(i, j))
                    return
                }
                traverse(input, i+1, j, input[i][j])
                traverse(input, i-1, j, input[i][j])
                traverse(input, i, j-1, input[i][j])
                traverse(input, i, j+1, input[i][j])
                return
            }
        } catch (e: IndexOutOfBoundsException) {
            return
        }
        return
    }

    fun part1(input: List<String>): Long {
        var ans = 0L
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '0') {
                    vis.clear()
                    traverse(input, i, j, ('0'.code-1).toChar())
                    ans += vis.size.toLong()
                    vis.clear()
                }
            }
        }
        return ans
    }
    fun traverse2(input: List<String>, i: Int, j: Int, ch: Char): Int {
        try {
            if (input[i][j] - ch == 1) {
                if (input[i][j] == '9')
                    return 1
                return traverse2(input, i+1, j, input[i][j]) + traverse2(input, i-1, j, input[i][j]) + traverse2(input, i, j-1, input[i][j]) + traverse2(input, i, j+1, input[i][j])
            }
        } catch (e: IndexOutOfBoundsException) {
            return 0
        }
        return 0
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '0') {
                    ans += traverse2(input, i, j, ('0'.code-1).toChar())
                }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 36L)
    check(part2(testInput) == 81L)

    // clear any global variables

    val input = readInput("Day00")
    measureTime {
        part1(input).println()
    }.println()
    measureTime {
        part2(input).println()
    }.println()
}
