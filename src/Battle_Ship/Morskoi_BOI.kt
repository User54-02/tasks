class Player {
    var name: String = ""
    var field: Array<CharArray> = Array(10) { CharArray(10) { '.' } }
    var shots: Int = 0
    var hits: Int = 0
    var shipsLeft: Int = 20

    fun printField() {
        println("=== Поле игрока $name ===")
        print("  ")
        for (c in 0..9) print("$c ")
        println()
        for (r in 0..9) {
            print("$r ")
            for (c in 0..9) print("${field[r][c]} ")
            println()
        }
    }
    fun isAlive(): Boolean {
        return shipsLeft > 0
    }
    fun accuracy(): Double {
        if (shots == 0) return 0.0
        return hits * 100.0 / shots
    }
    fun status(): String {
        return "Игрок $name: палуб осталось $shipsLeft, выстрелов $shots, попаданий $hits, точность ${"%.1f".format(accuracy())}%"
    }
    fun printStats() {
        println(status())
    }
    fun takeDamage(): Boolean {
        shipsLeft--
        return shipsLeft > 0
    }
    fun registerShot(hit: Boolean) {
        shots++
        if (hit) hits++
    }
    fun reset() {
        shots = 0
        hits = 0
        shipsLeft = 20
        for (r in 0..9) {
            for (c in 0..9) {
                field[r][c] = '.'
            }
        }
    }
}
fun printBothFields(player: Player, enemy: Player) {
    println("=== Поле игрока ${player.name} ===          === Поле игрока ${enemy.name} ===")
    print("  ")
    for (c in 0..9) print("$c ")
    print("     ")
    print("  ")
    for (c in 0..9) print("$c ")
    println()
    for (r in 0..9) {
        print("$r ")
        for (c in 0..9) print("${player.field[r][c]} ")
        print("     ")
        print("$r ")
        for (c in 0..9) print("${enemy.field[r][c]} ")
        println()
    }
}
fun main() {
    val player = Player()
    player.name = "Вы"

    player.registerShot(true)   // попал
    player.registerShot(false)  // промах
    player.registerShot(true)   // попал

    println(player.status())
    // Игрок Вы: палуб осталось 20, выстрелов 3, попаданий 2, точность 66.7%

    player.takeDamage()
    player.takeDamage()
    println(player.shipsLeft)   // 18
    println(player.isAlive())   // true

    player.reset()
    println(player.status())
    // Игрок Вы: палуб осталось 20, выстрелов 0, попаданий 0, точность 0.0%

    val enemy = Player()
    enemy.name = "Компьютер"
    enemy.registerShot(true)
    enemy.registerShot(true)
    enemy.registerShot(true)
    enemy.registerShot(false)

    println()
    println(player.status())
    println(enemy.status())
    println("Вы жив: ${player.isAlive()}")
    println("Компьютер жив: ${enemy.isAlive()}")
    player.field[3][2] = '#'
    player.field[3][3] = '#'
    player.field[3][4] = '#'
    player.field[3][5] = '#'

    enemy.field[5][7] = '#'
    enemy.field[6][7] = '#'
    enemy.field[7][7] = '#'

    printBothFields(player, enemy)
}