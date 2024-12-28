import java.util.PriorityQueue

data class Block(val x: Int, val y: Int, val dir: Int, val cost: Long, val history: Set<Pair<Int, Int>> = setOf())

fun main() {
    val dirList = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
    fun part1(input: List<String>): Pair<Long, Int> {
        val vis = Array(input.size) { Array(input[0].length) { Array(4) { false } } }
        val queue = PriorityQueue<Block>(compareBy { it.cost })
        queue.offer(Block(input.size - 2, 1, 0, 0L))
        while (queue.isNotEmpty()) {
            var (x, y, dirI, cost, history) = queue.poll()
            val dir = dirList[dirI]
            if (input[x][y] == 'E') {
                return (cost to history.size + 1)
            }
            if (input[x][y] == '#') {
                continue
            }
            if (vis[x][y][dirI]) continue
            else vis[x][y][dirI] = true
            while (queue.isNotEmpty()) {
                val block = queue.find { it.x == x && it.y == y && it.dir == dirI && it.cost == cost }
                if (block != null) {
                    queue.remove(block)
                    history = history.plus(block.history)
                } else break
            }
            history = history.plus(Pair(x, y))
            queue.offer(Block(x + dir.first, y + dir.second, dirI, cost + 1, history))
            queue.offer(Block(x, y, (dirI + 1) % 4, cost + 1000, history))
            queue.offer(Block(x, y, (dirI + 3) % 4, cost + 1000, history))
        }
        return 0L to 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 11048L to 64)

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
}
