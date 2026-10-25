fun main() {
    val alphabet = listOf(
        'А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О',
        'П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ь','Ы','Ъ','Э','Ю','Я'
    )
    val shifts = listOf(
        21,13,4,20,22,1,25,12,24,14,2,28,9,23,3,29,
        6,16,15,11,26,5,30,27,8,18,10,33,31,32,19,7,17
    )
    if (alphabet.size != shifts.size) {
        println("Ошибка настройки: размеры массивов не совпадают.")
        return
    }
    println("Введите ключевое слово (только русские буквы):")
    val keyRaw = readlnOrNull()?.trim()?.uppercase().orEmpty()
    if (keyRaw.isEmpty()) {
        println("Ошибка: ключевое слово пустое.")
        return
    }
    if (keyRaw.any { it !in alphabet }) {
        println("Ошибка: ключевое слово должно состоять только из русских букв.")
        return
    }
    println("Введите текст для шифровки:")
    val textRaw = readlnOrNull()?.trim()?.uppercase().orEmpty()
    if (textRaw.isEmpty()) {
        println("Ошибка: текст пустой.")
        return
    }
    if (textRaw.any { it !in alphabet }) {
        println("Ошибка: текст должен состоять только из русских букв.")
        return
    }
    val key = keyRaw.toList()
    val result = StringBuilder()
    for (i in textRaw.indices) {
        val ch = textRaw[i]
        val keyChar = key[i % key.size]
        val keyIndex = alphabet.indexOf(keyChar)
        val shift = shifts[keyIndex]
        val pos = alphabet.indexOf(ch)
        val newPos = (pos + shift) % alphabet.size
        result.append(alphabet[newPos])
    }
    println("Зашифрованный текст: $result")
}