fun main() {
    println("Введите первый массив чисел через пробел:")
    val line1 = readlnOrNull()?.trim().orEmpty()
    val list1 = line1.split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toIntOrNull() }
    if (list1.isEmpty()) {
        println("Ошибка: первый массив пуст или содержит не числа.")
        return
    }
    println("Введите второй массив чисел через пробел:")
    val line2 = readlnOrNull()?.trim().orEmpty()
    val list2 = line2.split(" ").filter { it.isNotEmpty() }.mapNotNull { it.toIntOrNull() }
    if (list2.isEmpty()) {
        println("Ошибка: второй массив пуст или содержит не числа.")
        return
    }
    val count1 = mutableMapOf<Int, Int>()
    for (n in list1) count1[n] = (count1[n] ?: 0) + 1
    val count2 = mutableMapOf<Int, Int>()
    for (n in list2) count2[n] = (count2[n] ?: 0) + 1
    val result = mutableListOf<Int>()
    for ((num, c1) in count1) {
        val c2 = count2[num] ?: 0
        val times = minOf(c1, c2)
        repeat(times) { result.add(num) }
    }
    println("Пересечение: $result")
}