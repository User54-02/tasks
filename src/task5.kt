fun main() {
    println("Введите число n:")
    val nInput = readlnOrNull()?.trim()
    println("Введите основание степени x:")
    val xInput = readlnOrNull()?.trim()
    val n = nInput?.toIntOrNull()
    val x = xInput?.toIntOrNull()
    if (n == null || x == null) {
        println("Ошибка: нужно ввести целые числа.")
        return
    }
    if (x <= 1) {
        println("Ошибка: основание должно быть больше 1.")
        return
    }
    if (n <= 0) {
        println("Ошибка: n должно быть положительным.")
        return
    }
    var value = 1
    var y = 0
    while (value < n) {
        value *= x
        y++
    }
    if (value == n) {
        println("Целочисленный показатель: y = $y")
    } else {
        println("Целочисленный показатель не существует")
    }
}