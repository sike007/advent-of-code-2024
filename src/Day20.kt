import java.util.*
import kotlin.math.abs

data class RacingLine(val x: Int, val y: Int, val ps: Long, val cheatEnabled: Boolean = false)

fun main() {
    val dirList = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
    fun part1(input: List<String>, st: Pair<Int, Int>, threshold: Int): Long {
        fun traverse(givenInput: List<String>, cheatStart: Pair<Int, Int>): Long {
            val vis = Array(givenInput.size) { Array(givenInput[0].length) { false } }
            val queue = PriorityQueue<RacingLine>(compareBy { it.ps })
            queue.offer(RacingLine(st.first, st.second, 0))
            while (queue.isNotEmpty()) {
                var (x, y, cost, cheatEnabled) = queue.poll()
                if (x !in 1..<givenInput.lastIndex || y !in 1..<givenInput[0].lastIndex) {
                    continue
                }
                if (givenInput[x][y] == 'E') {
                    return cost
                }
                if (givenInput[x][y] == '#' && (x to y) != cheatStart) continue
                cheatEnabled = false
                if ((x to y) == cheatStart) cheatEnabled = true
                if (vis[x][y]) continue
                vis[x][y] = true
                dirList.forEach { (a, b) ->
                    queue.offer(RacingLine(x + a, y + b, cost + 1, cheatEnabled))
                }
            }
            return Long.MAX_VALUE
        }

        val actualPath = traverse(input, 0 to 0)
        var ans = 0L
        for (i in 1..<input.lastIndex) {
            for (j in 1..<input[i].lastIndex) {
                if (input[i][j] in listOf('S', '#')) {
                    if (actualPath - traverse(input, i to j) >= threshold) {
                        ans++
                    }
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>, st: Pair<Int, Int>, en: Pair<Int, Int>, threshold: Int): Long {
        fun floodFill(beg: Pair<Int, Int>): Array<Array<Long>> {
            val vis = Array(input.size) { Array(input[0].length) { -1L } }
            val queue = PriorityQueue<RacingLine>(compareBy { it.ps })
            queue.offer(RacingLine(beg.first, beg.second, 0))
            while (queue.isNotEmpty()) {
                var (x, y, cost) = queue.poll()
                if (x !in 1..<input.lastIndex || y !in 1..<input[0].lastIndex) {
                    continue
                }
                if (input[x][y] == '#') continue
                if (vis[x][y] != -1L) continue
                vis[x][y] = cost
                dirList.forEach { (a, b) ->
                    queue.offer(RacingLine(x + a, y + b, cost + 1))
                }
            }
            return vis
        }

        var ans = 0L
        val fromBeginning = floodFill(st)
        val fromEnd = floodFill(en)
        val actualPath = fromBeginning[en.first][en.second]
        for (i in 0..input.lastIndex) {
            for (j in 0..input[i].lastIndex) {
                if (input[i][j] == '#') continue
                for (k in 0..input.lastIndex) {
                    for (l in 0..input[k].lastIndex) {
                        if ((i to j) == (k to l)) {
                            continue
                        }
                        if (abs(i - k) + abs(j - l) > 20) {
                            continue
                        }
                        if (fromBeginning[i][j] == -1L || fromEnd[k][l] == -1L) { continue}
                        val pathLength = abs(i - k) + abs(j - l) + fromBeginning[i][j] + fromEnd[k][l]

                        if (actualPath - pathLength >= threshold) {
                            ans++
                        }
                    }
                }
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput, 3 to 1, 10) == 10L)
    check(part2(testInput, 3 to 1, 7 to 5, 50) == 285L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input, 29 to 87, 100).println()
    part2(input, 29 to 87, 49 to 95, 100).println()
}
