import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val a = mutableListOf<Int>()
        val b = mutableListOf<Int>()
        input.forEach{
            val (x,y) = it.split("   ").map { str -> str.toInt() }
            a.add(x)
            b.add(y)
        }
        a.sort()
        b.sort()
        return a.zip(b).sumOf { abs(it.second - it.first) }.toLong()
    }

    fun part2(input: List<String>): Long {
        val a = mutableListOf<Int>()
        val b = mutableMapOf<Int, Int>()
        input.forEach{
            val (x,y) = it.split("   ").map { str -> str.toInt() }
            a.add(x)
            b[y] = (b[y] ?: 0) + 1
        }

        return a.sumOf { it * (b[it] ?: 0) }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)
    

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}