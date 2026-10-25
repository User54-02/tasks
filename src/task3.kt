fun main() {
    print("Введите натуральное число: ")
    val input = readlnOrNull()?.trim().orEmpty()
    val num = input.toIntOrNull()
    if (num == null) {
        println("Ошибка: введите целое число.")
        return
    }
    if (num < 0) {
        println("Ошибка: число должно быть натуральным (не отрицательным).")
        return
    }
    if (num == 0) {
        println("Результат: 0")
        return
    }
    var n = num
    val binary = StringBuilder()
    while (n > 0) {
        binary.insert(0, n % 2)
        n /= 2
    }
    println("Результат: $binary")
}