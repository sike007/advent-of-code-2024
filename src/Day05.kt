fun main() {
    fun check(
        input: List<String>,
        i: Int,
        elementsAfterMap: MutableMap<Int, MutableSet<Int>>
    ): Long {
        val numbers = input[i].split(",").map(String::toInt)
        val s = mutableSetOf<Int>()
        numbers.forEach { num ->
            if ((s.toSet() intersect elementsAfterMap.getOrDefault(num, emptySet()).toSet()).isNotEmpty()) {
                return 0
            }
            s.add(num)
        }
        return numbers[numbers.size/2].toLong()
    }

    fun part1(input: List<String>): Long {
        val elementsAfterMap = mutableMapOf<Int, MutableSet<Int>>()
        var i = 0
        var ans = 0L
        while (input[i] != ""){
            val (k,v) = input[i].split("|").map(String::toInt)
            elementsAfterMap.getOrPut(k) { mutableSetOf() }.add(v)
            i++
        }
        i++
        while(i<input.size){
            ans += check(input, i, elementsAfterMap)
            i++
        }
        return ans
    }


    fun swapIfNeeded(numbers: MutableList<Int>, elementsAfterMap: MutableMap<Int, MutableSet<Int>>): MutableList<Int> {
        for (j in numbers.indices){
            for (k in 0..<j){
                if (elementsAfterMap.getOrDefault(numbers[j], emptySet()).contains(numbers[k])){
                    val t = numbers[k]
                    numbers[k] = numbers[j]
                    numbers[j] = t
                    return numbers
                }
            }
        }
        return mutableListOf(numbers[numbers.size/2])
    }

    fun fix(input: List<String>, i: Int, elementsAfterMap: MutableMap<Int, MutableSet<Int>>): Long {
        var iter = 1000
        var numbers = input[i].split(",").map(String::toInt).toMutableList()
        while (iter != 0){
            numbers = swapIfNeeded(numbers, elementsAfterMap)
            if (numbers.size == 1)
                return numbers[0].toLong()
            iter--
        }
        return 0
    }

    fun part2(input: List<String>): Long {
        val elementsAfterMap = mutableMapOf<Int, MutableSet<Int>>()
        var i = 0
        var ans = 0L
        while (input[i] != ""){
            val (k,v) = input[i].split("|").map(String::toInt)
            elementsAfterMap.getOrPut(k) { mutableSetOf() }.add(v)
            i++
        }
        i++
        while(i<input.size){
            if(check(input, i, elementsAfterMap) == 0L){
                ans += fix(input, i, elementsAfterMap)
            }
            i++
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00_test")
    check(part1(testInput) == 143L)
    check(part2(testInput) == 123L)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}