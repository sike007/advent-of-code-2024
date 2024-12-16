import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    fun part1(input: List<String>): Long {
        return input.chunked(4).sumOf { chunk ->
            var minRes = Long.MAX_VALUE
            val (xA, yA) = chunk[0].split(": ")[1].split(", ").map { it.drop(2).toInt() }
            val (xB, yB) = chunk[1].split(": ")[1].split(", ").map { it.drop(2).toInt() }
            val (x, y) = chunk[2].split(": ")[1].split(", ").map { it.drop(2).toLong() }

            for (i in 0..100) {
                if ((i * xA) > x || (i * yA) > y) break
                if ((x - i * xA) % xB == 0L) {
                    val bTimes = (x - i * xA) / xB
                    if ((i * yA + bTimes * yB) == y) {
                        if (bTimes <= 100) {
                            minRes = minOf(minRes, i * 3 + bTimes)
                        }
                    }
                }
            }
            minRes * (minRes != Long.MAX_VALUE).int
        }
    }

    fun part2(input: List<String>): Long {
        return input.chunked(4).sumOf { chunk ->
            val (xA, yA) = chunk[0].split(": ")[1].split(", ").map { it.drop(2).toBigDecimal().setScale(10) }
            val (xB, yB) = chunk[1].split(": ")[1].split(", ").map { it.drop(2).toBigDecimal().setScale(10) }
            val (x, y) = chunk[2].split(": ")[1].split(", ")
                .map { it.drop(2).toBigDecimal().plus(BigDecimal("10000000000000")).setScale(10) }
            if (xA.divideAndRemainder(yA).contentEquals(xB.divideAndRemainder(yB))) {
                if (xA.divideAndRemainder(yA).contentEquals(x.divideAndRemainder(y))) {
                    return@sumOf 0L
                }
            }
            val ansA = ((x - xB * y / yB) / (xA - yA * xB / yB)).setScale(0, RoundingMode.HALF_UP)
            val ansB = ((x - ansA * xA) / xB).setScale(0, RoundingMode.HALF_UP)
            if (ansA <= BigDecimal("1") || ansB <= BigDecimal("1")) {
                return@sumOf 0L
            }
            if (((ansA * xA + ansB * xB)).setScale(10) == x) {
                return@sumOf ansA.toLong() * 3 + ansB.toLong()
            }
            return@sumOf 0L
        }
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 480L)
//    check(part2(testInput) == 1L)

    // clear any global variables

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
