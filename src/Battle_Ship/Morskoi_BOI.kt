fun createEmptyField(size: Int = 10): Array<CharArray> {
    return Array(size) { CharArray(size) { '.' } }
}
fun printField(
    field: Array<CharArray>,
    title: String,
    showShips: Boolean = true,
    debug: Boolean = false
) {
    val size = field.size
    if (debug) {
        println("=== $title (debug) ===")
        val border = "+" + "--+".repeat(size)
        println("   $border")
        for (r in field.indices) {
            print("$r |")
            for (c in field.indices) {
                val ch = field[r][c]
                val toPrint = if (!showShips && ch == '#') '.' else ch
                print(" $toPrint|")
            }
            println()
            println("   $border")
        }
        return
    }
    println("=== $title ===")
    print("  ")
    for (c in field.indices) print("$c ")
    println()
    for (r in field.indices) {
        print("$r ")
        for (c in field.indices) {
            val ch = field[r][c]
            val toPrint = if (!showShips && ch == '#') '.' else ch
            print("$toPrint ")
        }
        println()
    }
}
fun printBothFields(
    player: Array<CharArray>,
    enemy: Array<CharArray>,
    showEnemyShips: Boolean = false
) {
    if (player.size != enemy.size) {
        println("Ошибка: размеры полей не совпадают (${player.size} и ${enemy.size}).")
        return
    }
    val size = player.size
    println("=== Ваше поле ===" + " ".repeat(size * 2 + 5) + "=== Поле противника ===")

    print("  ")
    for (c in player.indices) print("$c ")
    print(" ".repeat(5))
    print("  ")
    for (c in enemy.indices) print("$c ")
    println()
    for (r in player.indices) {
        print("$r ")
        for (c in player.indices) print("${player[r][c]} ")

        print(" ".repeat(5))

        print("$r ")
        for (c in enemy.indices) {
            val ch = enemy[r][c]
            val toPrint = if (!showEnemyShips && ch == '#') '.' else ch
            print("$toPrint ")
        }
        println()
    }
}
fun main() {
    val playerField = createEmptyField()
    playerField[3][2] = '#'
    playerField[3][3] = '#'
    playerField[3][4] = '#'
    playerField[3][5] = '#'

    val enemyField = createEmptyField()
    enemyField[5][7] = '#'
    enemyField[6][7] = '#'
    enemyField[7][7] = '#'
    enemyField[5][5] = 'X'
    enemyField[5][6] = 'O'

    printBothFields(playerField, enemyField)
    println()
    printField(playerField, "Ваше поле", debug = true)
    println()
    printField(enemyField, "Поле противника", showShips = false)

    println()
    val smallField = createEmptyField(8)
    printField(smallField, "Маленькое поле 8x8")

    val bigField = createEmptyField(12)
    printField(bigField, "Большое поле 12x12")
}