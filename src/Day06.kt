import kotlin.time.measureTime

fun main() {
    val nextMap = mapOf(
        Pair(-1, 0) to Pair(0, 1),
        Pair(0, 1) to Pair(1, 0),
        Pair(1, 0) to Pair(0, -1),
        Pair(0, -1) to Pair(-1, 0),
    )

    val theWay = mutableListOf<List<Int>>()

    fun part1(input: List<String>): Long {
        val mutableInput = input.toMutableList()
        var i = 0
        var j = 0
        run breaking@{
            input.forEachIndexed { ii, line ->
                j = line.indexOf('^')
                if (j != -1) {
                    i = ii
                    return@breaking
                }
            }
        }
        var dir = Pair(-1, 0)
        var travelled = 1L
        val newStr = StringBuilder(mutableInput[i])
        newStr.setCharAt(j, 'X')
        mutableInput[i] = newStr.toString()
        try {
            while (true) {
                theWay.add(listOf(i, j, dir.first, dir.second))
                if (mutableInput[i + dir.first][j + dir.second] == '#') {
                    dir = nextMap[dir]!!
                    continue
                }
                i += dir.first
                j += dir.second
                if (mutableInput[i][j] != 'X') {
                    travelled++
                    val newString = StringBuilder(mutableInput[i])
                    newString.setCharAt(j, 'X')
                    mutableInput[i] = newString.toString()
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            return travelled
        }
    }

    fun traverse(input: List<CharArray>, ii: Int, ij: Int): Boolean {
        var i = ii
        var j = ij
        var dir = Pair(-1, 0)
        val vis = mutableSetOf<List<Int>>()
        try {
            while (true) {
                val coord = listOf(i, j, dir.first, dir.second)
                if (vis.contains(coord)) {
                    return true
                }
                vis.add(coord)
                if (input[i + dir.first][j + dir.second] == '#') {
                    dir = nextMap[dir]!!
                    continue
                }
                i += dir.first
                j += dir.second
            }
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }

    fun part2(inputString: List<String>): Long {
        val input: List<CharArray> = inputString.map { it.toCharArray() }
        theWay.removeLast()
        val (ii, ij) = theWay[0]
        val obstr = mutableSetOf<Pair<Int, Int>>()
        theWay.forEach {
            val (i, j, xDir, yDir) = it
            val t = input[i + xDir][j + yDir]
            if (t in listOf('#', '^'))
                return@forEach
            input[i + xDir][j + yDir] = '#'
            if (traverse(input, ii, ij)) {
                obstr.add(Pair(i + xDir, j + yDir))
            }
            input[i + xDir][j + yDir] = t
        }
        return obstr.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 41L)
    check(part2(testInput) == 6L)

    // clear any global variables
    theWay.clear()

    val input = readInput("Day00")
    part1(input).println()
    measureTime {
        part2(input).println()
    }.println()
}