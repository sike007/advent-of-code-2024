data class Node(val char: Char, val cX: Int, val cY: Int)

fun main() {
    fun part1(input: List<String>): Pair<Long,Long> {
        val nodes = mutableListOf<Node>()
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, char ->
                if (char != '.') {
                    nodes.add(Node(char, i, j))
                }
            }
        }
        val antinodes1 = mutableSetOf<Pair<Int, Int>>()
        val antinodes2 = nodes.map { Pair(it.cX,it.cY) }.toMutableSet()

        for (i in 0..<nodes.size - 1) {
            for (j in i + 1..<nodes.size) {
                if (nodes[i].char != nodes[j].char) {
                    continue
                }
                val x = nodes[i].cX - nodes[j].cX
                val y = nodes[i].cY - nodes[j].cY
                var pos1 = Pair((nodes[i].cX + x), (nodes[i].cY + y))
                var pos2 = Pair(nodes[j].cX - x, nodes[j].cY - y)
                try {
                    input[pos1.first][pos1.second]
                    antinodes1.add(pos1)
                    while (true) {
                        pos1 = Pair(pos1.first + x, pos1.second + y)
                        input[pos1.first][pos1.second]
                        antinodes2.add(pos1)
                    }
                } catch (_: IndexOutOfBoundsException) { }
                try {
                    input[pos2.first][pos2.second]
                    antinodes1.add(pos2)
                    while (true) {
                        pos2 = Pair(pos2.first - x, pos2.second - y)
                        input[pos2.first][pos2.second]
                        antinodes2.add(pos2)
                    }
                } catch (_: IndexOutOfBoundsException) { }
            }
        }
        antinodes2.addAll(antinodes1)
        return Pair(antinodes1.size.toLong(),antinodes2.size.toLong())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == Pair(14L, 34L))

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
}
