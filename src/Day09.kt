import java.util.*

fun main() {
    fun part1(input: List<String>): Long {
        val list = ArrayDeque<Pair<Long, Long>>()

        input[0].forEachIndexed { index, c ->
            if ((index % 2) == 1) {
                list.add(Pair(-1L, c.toString().toLong()))
            } else {
                list.add(Pair((index / 2).toLong(), c.toString().toLong()))
            }
        }
        var k = 0L
        var ans = 0L
        while (!list.isEmpty()) {
            if (list.peekLast().first == -1L) {
                list.removeLast()
                continue
            }
            if (list.peekLast().second == 0L) {
                list.removeLast()
                continue
            }
            if (list.peekFirst().second == 0L) {
                list.removeFirst()
                continue
            }
            if (list.peekFirst().first != -1L) {
                ans += list.peekFirst().first * k
                k++
            } else {
                ans += list.peekLast().first * k
                k++
                val last = list.removeLast()
                list.addLast(Pair(last.first, last.second - 1))
            }
            val first = list.removeFirst()
            list.addFirst(Pair(first.first, first.second - 1))
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val list: MutableList<Pair<Long, Long>> = mutableListOf()

        input[0].forEachIndexed { index, c ->
            if ((index % 2) == 1) {
                list.add(Pair(-1L, c.toString().toLong()))
            } else {
                list.add(Pair((index / 2).toLong(), c.toString().toLong()))
            }
        }

        var i = list.lastIndex
        while (i > 0) {
            if (list[i].first == -1L) {
                i--
                continue
            }
            val requiredSize = list[i].second
            var j = 0
            while (j < i) {
                if ((list[j].first != -1L) or (list[j].second < requiredSize)) {
                    j++
                } else {
                    list[j] = Pair(-1, list[j].second - requiredSize)
                    val moveBlock = list.removeAt(i)
                    list[i - 1] = Pair(-1, list[i - 1].second + requiredSize)
                    i++
                    list.add(j, moveBlock)
                    break
                }
            }
            i--
        }
        var u = 0
        var k = 0L
        var ans = 0L
        while (u < list.size) {
            if (list[u].first != -1L) {
                for (l in 0..<list[u].second) {
                    ans += list[u].first * k++
                }
            } else k += list[u].second
            u++
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
