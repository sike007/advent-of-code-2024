data class Robot(val pos: Pair<Long, Long>, val vel: Pair<Long, Long>)

fun main() {
    fun part1(input: List<String>): Long {
        val robots = input.map {
            val chunks = it.split(" ")
            val pos = chunks[0].drop(2).split(",").map { it.toLong() }
            val vel = chunks[1].drop(2).split(",").map { it.toLong() }
            Robot(pos[0] to pos[1], vel[0] to vel[1])
        }

        val p1Max = robots.maxOf { it.pos.first }
        val p2Max = robots.maxOf { it.pos.second }
        val p1Min = robots.minOf { it.pos.first }
        val p2Min = robots.minOf { it.pos.second }

        val quadrants = mutableMapOf<Pair<Int, Int>, Long>((0 to 0) to 0, (0 to 1) to 0, (1 to 1) to 0, (1 to 0) to 0)
        robots.forEach {
            val finalX = Math.floorMod(((it.pos.first - p1Min) + (it.vel.first * 100)), (p1Max - p1Min + 1))
            val finalY = Math.floorMod(((it.pos.second - p2Min) + (it.vel.second * 100)), (p2Max - p2Min + 1))
            if (finalX <= (p1Max - 1) / 2) {
                if (finalY <= (p2Max - 1) / 2) {
                    quadrants[0 to 0] = quadrants[0 to 0]!! + 1
                } else if (finalY >= (p2Max / 2 + 1))
                    quadrants[0 to 1] = quadrants[0 to 1]!! + 1
            } else if (finalX >= (p1Max / 2 + 1)) {
                if (finalY <= (p2Max - 1) / 2) {
                    quadrants[1 to 0] = quadrants[1 to 0]!! + 1
                } else if (finalY >= (p2Max / 2 + 1))
                    quadrants[1 to 1] = quadrants[1 to 1]!! + 1
            }
        }
        return quadrants.values.reduce { acc, l -> acc * l }
    }

    fun part2(input: List<String>): Long {
        val robots = input.map {
            val chunks = it.split(" ")
            val pos = chunks[0].drop(2).split(",").map { it.toLong() }
            val vel = chunks[1].drop(2).split(",").map { it.toLong() }
            Robot(pos[0] to pos[1], vel[0] to vel[1])
        }

        val p1Max = robots.maxOf { it.pos.first }
        val p2Max = robots.maxOf { it.pos.second }
        val p1Min = robots.minOf { it.pos.first }
        val p2Min = robots.minOf { it.pos.second }

        (0..10000).forEach { iter ->
            val finalPos = robots.map {
                val finalX = Math.floorMod(((it.pos.first - p1Min) + (it.vel.first * iter)), (p1Max - p1Min + 1))
                val finalY = Math.floorMod(((it.pos.second - p2Min) + (it.vel.second * iter)), (p2Max - p2Min + 1))
                Pair(finalX, finalY)
            }.toSet()
            (0L..102L).forEach { num ->
                if (finalPos.filter { it.second == num }.size > 30 ){
                    println(iter)
                    (0L..102L).forEach { i ->
                        (0L..100L).forEach { j ->
                            if(finalPos.contains(Pair(j, i))) print("*")
                            else print(" ")
                        }
                        println()
                    }
                    println()
                }
            }
        }
        return input.size * 1L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 12L)
//    check(part2(testInput) == 1L)

    // clear any global variables

    val input = readInput("Day00")
//    part1(input).println()
    part2(input).println()
}
