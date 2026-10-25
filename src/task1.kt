fun main() {
    print("Введите строку: ")
    val input = readlnOrNull()?.trim().orEmpty()
    if (input.isEmpty()) {
        println("Пустая строка.")
        return
    }
    val stroka = buildString {
        var k = 1
        for (i in 1 until input.length) {
            if (input[i] == input[i - 1]) k++
            else {
                append(input[i - 1])
                if (k > 1) append(k)
                k = 1
            }
        }
        append(input.last())
        if (k > 1) append(k)
    }
    println(stroka)
}