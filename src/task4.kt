fun main() {
    println("Введите выражение в формате: ЧИСЛО1 ЧИСЛО2 ОПЕРАЦИЯ")
    println("Например: 3.5 2.0 +")
    val input = readlnOrNull()?.trim().orEmpty()
    val parts = input.split(" ").filter { it.isNotEmpty() }
    if (parts.size != 3) {
        println("Ошибка: нужно ровно 3 элемента через пробел: число, число, операция.")
        return
    }
    val a = parts[0].toDoubleOrNull()
    val b = parts[1].toDoubleOrNull()
    val op = parts[2]
    if (a == null || b == null) {
        println("Ошибка: первые два элемента должны быть числами.")
        return
    }
    if (op.length != 1 || op !in listOf("+", "-", "*", "/")) {
        println("Ошибка: операция должна быть одной из: + - * /")
        return
    }
    val result = when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> {
            if (b == 0.0) {
                println("Ошибка: деление на ноль.")
                return
            }
            a / b
        }
        else -> return
    }
    println("Результат: $result")
}