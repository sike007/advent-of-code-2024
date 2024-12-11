fun main() {
    val memoi = mutableMapOf<Pair<Long, Int>, Long>()
    fun process(num: Long, howDeep: Int): Long {
        if (memoi[Pair(num, howDeep)] != null)
            return memoi[Pair(num, howDeep)]!!
        if (howDeep == 75)
            return 1
        if (num == 0L) {
            memoi[Pair(num, howDeep)] = process(1, howDeep + 1)
        } else if (num.toString().length % 2 == 0) {
            val numString = num.toString()
            val halves = numString.chunked(numString.length/2)
            memoi[Pair(num, howDeep)] = halves.sumOf { process(it.toLong(), howDeep+1) }
        } else {
            memoi[Pair(num, howDeep)] = process(num * 2024, howDeep + 1)
        }
        return memoi[Pair(num, howDeep)]!!
    }

    fun part2(input: List<String>): Long {
        val row = input[0].split(" ").map { it.toLong() }
        var ans = 0L
        for (num in row) {
            ans += process(num, 0)
        }
        println(memoi.size)
        return ans
    }

    // clear any global variables

    val input = readInput("Day00")
    part2(input).println()
}
