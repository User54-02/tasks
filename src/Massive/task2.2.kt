fun main() {
    val size = 5
    val array = Array(size) { IntArray(size) }
    println("Введите ${size * size} целых чисел для массива 5x5 (каждое с новой строки):")
    for (i in 0 until size) {
        for (j in 0 until size) {
            val num = readlnOrNull()?.trim()?.toIntOrNull()
            if (num == null) {
                println("Ошибка: нужно целое число.")
                return
            }
            array[i][j] = num
        }
    }
    println("\nМассив:")
    for (i in 0 until size) {
        for (j in 0 until size) {
            print(array[i][j].toString().padStart(5))
        }
        println()
    }
    var symmetric = true
    outer@ for (i in 0 until size) {
        for (j in i + 1 until size) {
            if (array[i][j] != array[j][i]) {
                symmetric = false
                break@outer
            }
        }
    }
    println()
    if (symmetric) {
        println("Массив симметричен относительно главной диагонали.")
    } else {
        println("Массив НЕ симметричен относительно главной диагонали.")
    }
}
