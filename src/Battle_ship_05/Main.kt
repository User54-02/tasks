import kotlin.random.Random

// Печатает одно поле.
// Если hideShips = true, клетки SHIP печатаются как '.'.
fun printField(player: Player, hideShips: Boolean) {
    print("  ")
    for (c in 0..9) print("$c ")
    println()
    for (r in 0..9) {
        print("$r ")
        for (c in 0..9) {
            val state = player.field()[r][c]
            val shown = if (hideShips && state == CellState.SHIP) CellState.EMPTY else state
            print("${shown.toChar()} ")
        }
        println()
    }
}

// Печатает два поля рядом: игрока (открыто) и врага (скрыто).
fun printBothFields(player: Player, enemy: Player, debug: Boolean = false) {
    println("=== Поле игрока ${player.name} ===        === Поле игрока ${enemy.name} ===")
    print("  ")
    for (c in 0..9) print("$c ")
    print("        ")
    for (c in 0..9) print("$c ")
    println()
    for (r in 0..9) {
        print("$r ")
        for (c in 0..9) print("${player.field()[r][c].toChar()} ")
        print("      ")
        print("$r ")
        for (c in 0..9) {
            val state = enemy.field()[r][c]
            // DEBUG
            val shown = if (state == CellState.SHIP && !debug) CellState.EMPTY else state
            print("${shown.toChar()} ")
        }
        println()
    }
}

// Печатает правила игры.
fun printRules() {
    println("=========================================")
    println("           МОРСКОЙ БОЙ - ПРАВИЛА")
    println("=========================================")
    println("Поле 10х10. Координаты: сначала строка (0-9), потом столбец (0-9).")
    println()
    println("Корабли, которые нужно расставить:")
    println("  1 корабль  х 4 палубы")
    println("  2 корабля  х 3 палубы")
    println("  3 корабля  х 2 палубы")
    println("  4 корабля  х 1 палуба")
    println("Итого: 10 кораблей, 20 палуб.")
    println()
    println("Правила расстановки:")
    println("  • Корабли не могут выходить за границы поля.")
    println("  • Корабли не могут пересекаться.")
    println("  • Корабли не могут касаться друг друга даже углами.")
    println()
    println("Как вводить корабль:")
    println("  1. Введите координату НАЧАЛЬНОЙ палубы: row col")
    println("     Например: 3 4 - строка 3, столбец 4.")
    println("  2. Введите тип корабля: 1, 2, 3 или 4 (число палуб).")
    println("  3. Введите направление:")
    println("     h - горизонтально вправо (→)")
    println("     v - вертикально вниз (↓)")
    println()
    println("Пример: корабль из 3 палуб, начало (2, 5), горизонтально.")
    println("Ввод: 2 5, затем 3, затем h.")
    println("Займёт клетки: (2,5), (2,6), (2,7).")
    println()
    println("После каждого корабля поле перерисовывается.")
    println("=========================================")
    println()
}

// Интерактивная расстановка кораблей человека.
fun placePlayerShips(player: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    var index = 0

    while (index < plan.size) {
        val expectedSize = plan[index]
        println()
        println("Ставим корабль #${index + 1} из ${plan.size} (${expectedSize} палуб).")
        printField(player, false)

        println("Введите начальную координату (row col):")
        val input = readln().trim().split(" ")
        if (input.size != 2) {
            println("Ошибка: нужно два числа через пробел.")
            continue
        }
        val startRow = input[0].toIntOrNull()
        val startCol = input[1].toIntOrNull()
        if (startRow == null || startCol == null) {
            println("Ошибка: координаты должны быть числами.")
            continue
        }

        println("Введите тип корабля (1, 2, 3 или 4):")
        val size = readln().trim().toIntOrNull()
        if (size == null || size !in 1..4) {
            println("Ошибка: тип корабля должен быть 1, 2, 3 или 4.")
            continue
        }
        if (size != expectedSize) {
            println("Сейчас нужно ставить корабль из $expectedSize палуб, а не $size.")
            continue
        }

        println("Введите направление (h - вправо, v - вниз):")
        val dir = readln().trim().lowercase()
        if (dir != "h" && dir != "v") {
            println("Ошибка: направление должно быть h или v.")
            continue
        }
        val horizontal = (dir == "h")

        val ok = player.placeShip(startRow, startCol, size, horizontal)
        if (ok) {
            println("Корабль поставлен!")
            index++
        } else {
            println("Нельзя поставить корабль здесь. Попробуйте снова.")
        }
    }
    println()
    println("Все корабли расставлены!")
    printField(player, false)
}
// Автоматическая расстановка кораблей игрока
fun placePlayerShipsAuto(player: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    val maxAttempts = 1000

    var attempt = 0
    var index = 0
    while (index < plan.size) {
        val size = plan[index]
        val startRow = Random.nextInt(0, 10)
        val startCol = Random.nextInt(0, 10)
        val horizontal = Random.nextBoolean()

        val ok = player.placeShip(startRow, startCol, size, horizontal)
        if (ok) {
            index++
            attempt = 0
        } else {
            attempt++
            if (attempt > maxAttempts) {
                // На случай если рандом зашел в тупик
                player.reset()
                index = 0
                attempt = 0
            }
        }
    }
    println("Ваши корабли расставлены автоматически!")
    printField(player, false)
}
// Автоматическая расстановка кораблей компьютера №3
fun placeEnemyShips(enemy: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    val maxAttempts = 1000

    var attempt = 0
    var index = 0
    while (index < plan.size) {
        val size = plan[index]
        val startRow = Random.nextInt(0, 10)
        val startCol = Random.nextInt(0, 10)
        val horizontal = Random.nextBoolean()

        val ok = enemy.placeShip(startRow, startCol, size, horizontal)
        if (ok) {
            index++
            attempt = 0
        } else {
            attempt++
            if (attempt > maxAttempts) {
                println("Компьютер не смог расставить корабли. Сбрасываем поле...")
                enemy.reset()
                index = 0
                attempt = 0
            }
        }
    }
    println("Компьютер расставил корабли.")
}

fun main() {
    val player = Player("Вы")
    val enemy = Player("Компьютер")

    printRules()

    // --- МЕНЮ РАССТАНОВКИ ---
    println("Как вы хотите расставить корабли?")
    println("1 - Вручную")
    println("2 - Автоматически")
    print("Ваш выбор (1 или 2): ")

    var choice = readln().trim()
    while (choice != "1" && choice != "2") {
        print("Ошибка. Введите 1 или 2: ")
        choice = readln().trim()
    }

    if (choice == "1") {
        println("=== РАССТАНОВКА ВАШИХ КОРАБЛЕЙ ===")
        placePlayerShips(player) // Ручная расстановка
    } else {
        placePlayerShipsAuto(player) // Автоматическая
    }

    println()
    println("=== КОМПЬЮТЕР РАССТАВЛЯЕТ КОРАБЛИ ===")
    placeEnemyShips(enemy)

    println()
    println("=== ИГРА НАЧАЛАСЬ ===")
    println("Введите 'quit' для выхода из игры.")
    println("Введите 'debug' для отображения кораблей противника.")
    println()

    var playerTurn = true
    var totalMoves = 0
    var debugMode = false

    while (player.hasShips() && enemy.hasShips()) {
        // Передаем флаг debugMode в печать полей
        printBothFields(player, enemy, debugMode)

        // СЧЁТЧИК потопленных кораблей №1
        val sunk = enemy.countShips()
        println("Потоплено вами: $sunk из 10")
        println()

        if (playerTurn) {
            println("Ваш ход. Введите координаты выстрела (row col) или команду:")
            val input = readln().trim().split(" ")

            // Обработка команд №3
            if (input.size == 1) {
                if (input[0].lowercase() == "quit") {
                    println("Вы вышли из игры.")
                    break
                }
                if (input[0].lowercase() == "debug") {
                    debugMode = !debugMode
                    println("Режим отладки: ${if (debugMode) "ВКЛ" else "ВЫКЛ"}")
                    continue
                }
            }

            // Проверка корректности ввода (два числа)
            if (input.size != 2) {
                println("Ошибка: нужно два числа через пробел.")
                continue
            }
            val r = input[0].toIntOrNull()
            val c = input[1].toIntOrNull()
            if (r == null || c == null) {
                println("Ошибка: координаты должны быть числами.")
                continue
            }

            // ПРОВЕРКА КООРДИНАТ ПОЛЯ №2 задание
            if (!enemy.isInside(r, c)) {
                println("Выстрел за границей поля! Попробуйте снова.")
                continue
            }
            val cell = enemy.field()[r][c]
            if (cell == CellState.HIT || cell == CellState.MISS) {
                println("Сюда уже стреляли! Попробуйте снова.")
                continue
            }

            // Сам выстрел
            val hit = enemy.shoot(r, c)
            totalMoves++
            if (hit) {
                println("Попадание!")
            } else {
                println("Промах.")
                playerTurn = false
            }
        } else {
            println("Ход компьютера...")
            var r: Int
            var c: Int
            do {
                r = Random.nextInt(0, 10)
                c = Random.nextInt(0, 10)
            } while (player.field()[r][c] == CellState.HIT || player.field()[r][c] == CellState.MISS)

            val hit = player.shoot(r, c)
            totalMoves++
            if (hit) {
                println("Компьютер попал в ($r, $c)!")
            } else {
                println("Компьютер промахнулся в ($r, $c).")
                playerTurn = true
            }
        }
        println()
    }

    // Вывод результатов
    if (enemy.hasShips()) {
        println("Победил компьютер!")
    } else {
        println("Вы победили!")
    }
    println("Игра длилась $totalMoves ходов.")
}
