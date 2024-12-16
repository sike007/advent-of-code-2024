fun main() {
    val dirMap = mapOf('>' to (0 to 1), '<' to (0 to -1), '^' to (-1 to 0), 'v' to (1 to 0))

    fun nextSpace(input: Array<CharArray>, ii: Int, jj: Int, dir: Char): Pair<Int, Int>? {
        var i = ii
        var j = jj
        val (x, y) = dirMap[dir]!!
        i += x
        j += y

        while (input[i][j] != '#') {
            if (input[i][j] == '.') return (i to j)
            i += x
            j += y
        }
        return null
    }

    fun part1(input: List<String>, dirLine: Int, st: Pair<Int, Int>): Long {
        val dirLines = input.drop(dirLine).joinToString("")
        val boxArray = input.subList(0, dirLine - 1).map { it.toCharArray() }.toTypedArray()
        var (i, j) = st
        for (dir in dirLines) {
            val boxFinal = nextSpace(boxArray, i, j, dir) ?: continue
            val (x, y) = dirMap[dir]!!
            boxArray[boxFinal.first][boxFinal.second] = 'O'
            boxArray[i + x][j + y] = '@'
            boxArray[i][j] = '.'
            i += x
            j += y
        }
        return ans(boxArray, 'O')
    }

    fun part2(input: List<String>, dirLine: Int, st: Pair<Int, Int>): Long {
        val dirLines = input.drop(dirLine).joinToString("")
        val boxArray = input.subList(0, dirLine - 1).map { line ->
            var newString = ""
            line.forEach { char ->
                when (char) {
                    '@' -> newString += "@."
                    'O' -> newString += "[]"
                    else -> {
                        newString += char //
                        newString += char
                    }
                }
            }
            newString.toCharArray()
        }.toTypedArray()

        fun moveStoneVertically(i: Int, j: Int, dir: Char): Boolean {
            val (x, y) = dirMap[dir]!!
            if (boxArray[i + x][j + y] == '#')
                return false
            if (boxArray[i + x][j + y + 1] == '#')
                return false
            val possible = (0..1).map { k ->
                if (boxArray[i + x][j + y + k] == '[') {
                    moveStoneVertically(i + x, j + y + k, dir)
                } else if (boxArray[i + x][j + y + k] == ']') {
                    moveStoneVertically(i + x, j + y + k - 1, dir)
                } else true
            }
            return !possible.contains(false)
        }

        fun pushStonesVertically(i: Int, j: Int, dir: Char) {
            val (x, y) = dirMap[dir]!!
            (0..1).forEach { k ->
                if (boxArray[i + x][j + y + k] == '[') {
                    pushStonesVertically(i + x, j + y + k, dir)
                } else if (boxArray[i + x][j + y + k] == ']') {
                    pushStonesVertically(i + x, j + y + k - 1, dir)
                }
            }
            boxArray[i + x][y + j] = boxArray[i][j]
            boxArray[i + x][y + j + 1] = boxArray[i][j + 1]
            boxArray[i][j] = '.'
            boxArray[i][j + 1] = '.'
        }

        var i = st.first
        var j = st.second * 2
        for (dir in dirLines) {
            val (x, y) = dirMap[dir]!!

            if (boxArray[i + x][j + y] == '#') {
                continue
            } else if (boxArray[i + x][j + y] in listOf('[', ']')) {

                if (dir in listOf('^', 'v')) {                                           // vertical [] logic
                    if (boxArray[i + x][j + y] == '[') {
                        if (moveStoneVertically(i + x, j + y, dir))
                            pushStonesVertically(i + x, j + y, dir)
                    } else if (boxArray[i + x][j + y] == ']') {
                        if (moveStoneVertically(i + x, j + y - 1, dir))
                            pushStonesVertically(i + x, j + y - 1, dir)
                    }
                } else {                                                                  // horizontal [] logic
                    val boxFinal = nextSpace(boxArray, i, j, dir) ?: continue
                    val bMax = maxOf(j + y + y, boxFinal.second)
                    val bMin = minOf(j + y + y, boxFinal.second)

                    for (b in bMin..bMax) {
                        if ((b - bMin) % 2 == 1) boxArray[i][b] = ']'
                        else boxArray[i][b] = '['
                    }
                    boxArray[i + x][j + y] = '.'
                }
            }
            if (boxArray[i + x][j + y] == '.') {
                boxArray[i + x][j + y] = '@'
                boxArray[i][j] = '.'
                i += x
                j += y
            }
        }
        return ans(boxArray, '[')
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput, 11, 4 to 4) == 10092L)
    check(part2(testInput, 11, 4 to 4) == 9021L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input, 51, (24 to 24)).println()
    part2(input, 51, (24 to 24)).println()
}

fun ans(boxArray: Array<CharArray>, char: Char): Long {
    var ans = 0L
    for (p in boxArray.indices) {
        for (q in boxArray[p].indices) {
            if (boxArray[p][q] == char) {
                ans += p * 100 + q
            }
        }
    }
    return ans
}

fun printThat(input: Array<CharArray>) {
    input.forEach {
        println(it)
    }
    println()
}