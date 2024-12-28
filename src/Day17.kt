fun main() {
    fun part1(input: List<String>): String {
        var output = ""
        val registers = input.dropLast(2).map { it.split(": ")[1].toLong() }.toTypedArray()
        val progCode = input[input.lastIndex].split(": ")[1].split(",").map { it.toLong() }.toTypedArray()
        fun comboValue(inp: Long): Long {
            return when (inp) {
                0L -> 0
                1L -> 1
                2L -> 2
                3L -> 3
                else -> registers[inp.toInt() - 4]
            }
        }

        var i = 0L
        fun operation(opcode: Long, operand: Long) {
            when (opcode) {
                0L -> registers[0] = registers[0] / (1 shl comboValue(operand).toInt())
                1L -> registers[1] = registers[1] xor operand
                2L -> registers[1] = comboValue(operand) % 8
                3L -> if (registers[0] != 0L) i = operand - 2L
                4L -> registers[1] = registers[1] xor registers[2]
                5L -> output += "${comboValue(operand) % 8},"
                6L -> registers[1] = registers[0] / (1 shl comboValue(operand).toInt())
                7L -> registers[2] = registers[0] / (1 shl comboValue(operand).toInt())
            }
        }
        while (i < progCode.lastIndex) {
            operation(progCode[i.toInt()], progCode[i.toInt() + 1])
            i += 2
        }
        return output.dropLast(1)
    }

    fun part2(input: List<String>): Long {
        val progCode = input[input.lastIndex].split(": ")[1].split(",").map { it.toInt() }.reversed()
        fun solve(prog: List<Int>, ans: Long): Long {
            if (prog.isEmpty()) return ans
            val res = ans shl 3
            for (i in 0..7) {
                val a = res + i.toLong()
                if (a == 0L) continue
                var b = (i % 8) xor 3
                val c = a shr b
                b = ((b.toLong() xor c xor 3L) % 8).toInt()
                if (b == prog[0]) {
                    val fin = solve(prog.drop(1), a)
                    if (fin != -1L) return fin
                }
            }
            return -1
        }
        return solve(progCode, 0L)
    }

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
