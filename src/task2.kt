fun main() {
    print("Введите строку: ")
    val input = readlnOrNull()?.trim().orEmpty()
    if (input.isEmpty()) {
        println("Ошибка: пустая строка.")
        return
    }
    if (input.any { it == ' ' }) {
        println("Ошибка: строка не должна содержать пробелов.")
        return
    }
    val kol = mutableMapOf<Char, Int>()
    for (c in input) {
        kol[c] = (kol[c] ?: 0) + 1
    }
    for ((char, kol) in kol.toSortedMap()) {
        println("$char - $kol")
    }
}