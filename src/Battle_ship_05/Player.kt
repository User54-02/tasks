// Класс игрока в морской бой.
// Хранит имя, поле, статистику и умеет управлять своим состоянием.
class Player(val name: String) {

    // подсчёт потопленных кораблей
    fun countShips(): Int {
        var hitsCount = 0
        for (r in 0..9) {
            for (c in 0..9) {
                if (_field[r][c] == CellState.HIT) {
                    hitsCount++
                }
            }
        }
        return hitsCount / 2
    }

    // ======================== ПОЛЕ ========================

    // Приватное поле 10х10. Каждая клетка — это CellState.
    // Снаружи поле НЕ видно.
    private val _field: Array<Array<CellState>> =
        Array(10) { Array(10) { CellState.EMPTY } }

    // Get-ер для поля. Снаружи можно только ЧИТАТЬ.
    fun field(): Array<Array<CellState>> = _field

    // ======================== СТАТИСТИКА ========================

    private var _shots: Int = 0
    private var _hits: Int = 0

    val shots: Int
        get() = _shots

    val hits: Int
        get() = _hits

    // ======================== МЕТОДЫ ========================

    fun accuracy(): Double =
        if (_shots == 0) 0.0 else _hits * 100.0 / _shots

    fun registerShot(hit: Boolean) {
        _shots++
        if (hit) _hits++
    }

    fun reset() {
        _shots = 0
        _hits = 0
        for (r in 0..9) {
            for (c in 0..9) {
                _field[r][c] = CellState.EMPTY
            }
        }
    }

    fun status(): String {
        val acc = String.format("%.1f", accuracy())
        return "Игрок $name: выстрелов $_shots, попаданий $_hits, точность $acc%"
    }

    // ======================== ПРОВЕРКИ (Boolean-функции) ========================

    // Проверяет, что координата (row, col) внутри поля 10х10.
    fun isInside(row: Int, col: Int): Boolean {
        return row in 0..9 && col in 0..9
    }

    // Проверяет, можно ли поставить корабль длины size
    // начиная с (startRow, startCol) в заданном направлении.
    fun canPlaceShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        // Перебираем все палубы будущего корабля.
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol

            // 1. Палуба должна быть внутри поля.
            if (!isInside(r, c)) return false

            // 2. Палуба должна быть пустой.
            if (_field[r][c] != CellState.EMPTY) return false

            // 3. Все 8 соседей должны быть пустыми (или за границей).
            for (dr in -1..1) {
                for (dc in -1..1) {
                    if (dr == 0 && dc == 0) continue
                    val nr = r + dr
                    val nc = c + dc
                    if (isInside(nr, nc) && _field[nr][nc] == CellState.SHIP) {
                        return false
                    }
                }
            }
        }
        return true
    }

    // Проверяет, есть ли на поле хотя бы одна неподбитая палуба.
    fun hasShips(): Boolean {
        for (r in 0..9) {
            for (c in 0..9) {
                if (_field[r][c] == CellState.SHIP) return true
            }
        }
        return false
    }

    // ======================== ДЕЙСТВИЯ ========================

    // Ставит корабль длины size начиная с (startRow, startCol).
    // Возвращает true, если поставлен, false — если нельзя.
    fun placeShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        if (!canPlaceShip(startRow, startCol, size, horizontal)) {
            return false
        }
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol
            _field[r][c] = CellState.SHIP
        }
        return true
    }

    // Выстрел по клетке (row, col).
    // Возвращает true при попадании, false — при промахе или невозможном выстреле.
    fun shoot(row: Int, col: Int): Boolean {
        if (!isInside(row, col)) {
            println("Выстрел за границей поля!")
            return false
        }
        val current = _field[row][col]
        if (current == CellState.HIT || current == CellState.MISS) {
            println("Сюда уже стреляли!")
            return false
        }
        val hit = (current == CellState.SHIP)
        _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        registerShot(hit)
        return hit
    }
}
