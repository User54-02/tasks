fun main() {
    println("Введите слова через пробел:")
    val line = readlnOrNull()?.trim().orEmpty()
    val words = line.split(" ").filter { it.isNotEmpty() }
    if (words.isEmpty()) {
        println("Ошибка: список слов пуст.")
        return
    }
    val groups = mutableMapOf<String, MutableList<String>>()
    for (word in words) {
        val key = word.toCharArray().sorted().joinToString("")
        groups.getOrPut(key) { mutableListOf() }.add(word)
    }
    println("\nГруппы слов из одинаковых букв:")
    for ((_, group) in groups) {
        println(group.joinToString(", ") { "\"$it\"" })
    }
}