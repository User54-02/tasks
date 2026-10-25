fun main() {
    println("Введите первую цифру:")
    val d1 = readlnOrNull()?.trim()?.toIntOrNull()
    println("Введите вторую цифру:")
    val d2 = readlnOrNull()?.trim()?.toIntOrNull()
    if (d1 == null || d2 == null || d1 !in 0..9 || d2 !in 0..9) {
        println("Ошибка: нужно ввести две цифры от 0 до 9.")
        return
    }
    if (d1 == d2) {
        println("Ошибка: цифры должны быть различными.")
        return
    }
    val oddVariants = listOf(d1, d2).filter { it % 2 != 0 }
    if (oddVariants.isEmpty()) {
        println("Создать нечетное число невозможно")
        return
    }
    val last = oddVariants.first()
    val first = if (last == d1) d2 else d1
    println("Число: $first$last")
}