fun main() {
    println("Введите количество строк:")
    val rows = readlnOrNull()?.trim()?.toIntOrNull()
    if (rows == null || rows <= 0 || rows > 20) {
        println("Ошибка: количество строк должно быть от 1 до 20.")
        return
    }
    println("Введите количество столбцов:")
    val cols = readlnOrNull()?.trim()?.toIntOrNull()
    if (cols == null || cols <= 0 || cols > 20) {
        println("Ошибка: количество столбцов должно быть от 1 до 20.")
        return
    }
    val array = Array(rows) { IntArray(cols) }
    println("Введите ${rows * cols} трёхзначных чисел (каждое с новой строки):")
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            val num = readlnOrNull()?.trim()?.toIntOrNull()
            if (num == null || num !in 100..999) {
                println("Ошибка: нужно трёхзначное число (100..999).")
                return
            }
            array[i][j] = num
        }
    }
    val digits = mutableSetOf<Char>()
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            for (ch in array[i][j].toString()) {
                digits.add(ch)
            }
        }
    }
    println("\nМассив:")
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            print(array[i][j].toString().padStart(6))
        }
        println()
    }
    println("\nВ массиве использовано ${digits.size} различных цифр")
}