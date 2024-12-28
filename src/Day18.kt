import java.util.*

data class Cell(val x: Int, val y: Int, val cost: Long, val history: Set<Pair<Int, Int>>)

fun main() {
    fun part1(input: List<String>, l: Int): Set<Pair<Int, Int>> {
        val dirList = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
        val blockers = input.map {
            val pair = it.split(',')
            pair[0].toInt() to pair[1].toInt()
        }.toSet()
        val vis = Array(l) { Array(l) { false } }
        val queue = PriorityQueue<Cell>(compareBy { it.cost })
        queue.offer(Cell(0, 0, 0, setOf(0 to 0)))
        while (queue.isNotEmpty()) {
            val (x, y, cost, history) = queue.poll()
            if (x == l - 1 && y == l - 1) {
                return history
            }
            if (x !in 0..<l || y !in 0..<l) {
                continue
            }
            if (vis[x][y]) continue
            else vis[x][y] = true
            if ((x to y) in blockers) continue
            dirList.forEach { (a, b) ->
                queue.offer(Cell(x + a, y + b, cost + 1, history.plus(x + a to y + b)))
            }
        }
        return setOf()
    }

    fun part2(input: List<String>, l: Int, iThatWorks: Int): String {
        val blockers = input.map {
            val pair = it.split(',')
            pair[0].toInt() to pair[1].toInt()
        }
        var vis = part1(input.subList(0, iThatWorks), l)

        (iThatWorks..input.size).forEach { i ->
            if (!vis.contains(blockers[i - 1])) return@forEach
            vis = part1(input.subList(0, i), l)
            if (vis.isEmpty()) {
                return input[i - 1]
            }
        }
        return ""
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput.subList(0, 12), 7).size - 1 == 22)
    check(part2(testInput, 7, 12) == "6,1")

    // clear any global variables

    val input = readInput("Day00")
    part1(input.subList(0, 1024), 71).size.println()
    part2(input, 71, 1024).println()
}
