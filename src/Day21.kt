fun main() {
    fun part1(input: List<String>, totalPads: Int): Long {
        val memoi = mutableMapOf<Pair<String, Int>, Long>()
        val numberMap =
            listOf(listOf('7', '8', '9'), listOf('4', '5', '6'), listOf('1', '2', '3'), listOf(null, '0', 'A'))
        val arrowMap = listOf(listOf(null, '^', 'A'), listOf('<', 'v', '>'))
        val arrowSequence = mapOf('^' to (-1 to 0), 'v' to (1 to 0), '>' to (0 to 1), '<' to (0 to -1))
        val map = listOf(numberMap, arrowMap)
        val initialPositions = listOf(3 to 2, 0 to 2, 0 to 2)
        fun findLocation(pad: Int, char: Char, cur: Pair<Int, Int>): Pair<Int, Int> {
            val (x, y) = cur
            (-3..3).forEach { i ->
                (-3..3).forEach { j ->
                    try {
                        if (map[(pad != 0).int][x + i][y + j] == char) {
                            return Pair(i, j)
                        }
                    } catch (_: Exception) {
                    }
                }
            }
            return -1 to -1
        }

        fun move(line: String, pad: Int): Long {
            if (memoi[line to pad] != null) {
                return memoi[line to pad]!!
            }
            if (pad == totalPads) return line.length.toLong()
            var pos = initialPositions[(pad != 0).int]
            memoi[line to pad] = line.sumOf { char ->
                // get minimum length for moving from A to B
                val (deltaX, deltaY) = findLocation(pad, char, pos)
                var fString = ""
                fString += ">".repeat(maxOf(deltaY, 0))
                fString += "<".repeat(maxOf(-deltaY, 0))
                fString += "^".repeat(maxOf(-deltaX, 0))
                fString += "v".repeat(maxOf(deltaX, 0))

                val permutations = mutableSetOf<String>()
                generatePermutations(fString, "", permutations)
                fun checkIfNull(str: String): Boolean {
                    var curPos = pos
                    for (i in str.indices) {
                        curPos =
                            curPos.first + arrowSequence[str[i]]!!.first to curPos.second + arrowSequence[str[i]]!!.second
                        if (map[(pad != 0).int][curPos.first][curPos.second] == null)
                            return true
                    }
                    return false
                }
                permutations.removeIf { checkIfNull(it) }

                pos = pos.first + deltaX to pos.second + deltaY
                permutations.minOfOrNull { move(it + "A", pad + 1) }!!
            }
            return memoi[line to pad]!!
        }

        fun calculate(line: String, fStringLength: Long): Long {
            return line.filter { it.isDigit() }.toLong() * fStringLength
        }
        return input.sumOf {
            calculate(it, move(it, 0))
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput, 3) == 126384L)
//    check(part2(testInput) == 1L)

// clear any global variables

    val input = readInput("Day00")
    part1(input, 3).println()
    part1(input, 26).println()
}

fun generatePermutations(input: String, prefix: String, result: MutableSet<String>) {
    if (input.isEmpty()) {
        result.add(prefix) // Add the current permutation to the result list
        return
    }

    for (i in input.indices) {
        val remaining = input.substring(0, i) + input.substring(i + 1)
        generatePermutations(remaining, prefix + input[i], result)
    }
}