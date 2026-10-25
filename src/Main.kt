//  ПЕРЕЧИСЛЕНИЯ (ENUM)
enum class Genre {
    FICTION, SCIENTIFIC, FANTASY, DETECTIVE, BIOGRAPHY, CHILDREN
}
enum class BookStatus(val displayName: String) {
    AVAILABLE("Доступна"),
    BORROWED("Выдана"),
    LOST("Потеряна"),
    RESERVED("Зарезервирована"),
    ARCHIVED("В архиве")
}
// КЛАСС КНИГИ
class Book(
    val title: String,
    val author: String,
    val year: Int,
    val genre: Genre,
    var status: BookStatus
)
//  ОСНОВНАЯ ПРОГРАММА
fun main() {
    // каталог из 12 книг
    val books = mutableListOf(
        Book("Мастер и Маргарита", "Булгаков", 1967, Genre.FICTION, BookStatus.AVAILABLE),
        Book("Преступление и наказание", "Достоевский", 1866, Genre.FICTION, BookStatus.BORROWED),
        Book("Краткая история времени", "Хокинг", 1988, Genre.SCIENTIFIC, BookStatus.AVAILABLE),
        Book("Гарри Поттер", "Роулинг", 1997, Genre.FANTASY, BookStatus.BORROWED),
        Book("Убийство в Восточном экспрессе", "Кристи", 1934, Genre.DETECTIVE, BookStatus.LOST),
        Book("Стив Джобс", "Айзексон", 2011, Genre.BIOGRAPHY, BookStatus.AVAILABLE),
        Book("Маленький принц", "Экзюпери", 1943, Genre.CHILDREN, BookStatus.RESERVED),
        Book("Властелин колец", "Толкин", 1954, Genre.FANTASY, BookStatus.ARCHIVED),
        Book("Дюна", "Герберт", 1965, Genre.FICTION, BookStatus.AVAILABLE),
        Book("Марсианин", "Вейер", 2011, Genre.SCIENTIFIC, BookStatus.BORROWED),
        Book("Шерлок Холмс", "Дойл", 1892, Genre.DETECTIVE, BookStatus.AVAILABLE),
        Book("Голодные игры", "Коллинз", 2008, Genre.FANTASY, BookStatus.LOST)
    )

    // Вывод каталога
    println("=== КАТАЛОГ КНИГ ===")
    println("№ | Название | Автор | Год | Жанр | Статус")
    println("-------------------------------------------------------------")
    for ((index, book) in books.withIndex()) {
        println("${index + 1} | ${book.title} | ${book.author} | ${book.year} | ${book.genre} | ${book.status.displayName}")
    }
    println()
    // Статистика
    println("=== СТАТИСТИКА ===")

    // По статусам
    val statusCounts = mutableMapOf<BookStatus, Int>()
    for (status in BookStatus.values()) statusCounts[status] = 0
    for (book in books) statusCounts[book.status] = statusCounts[book.status]!! + 1

    for ((status, count) in statusCounts) {
        when(status) {
            BookStatus.AVAILABLE -> println("Доступно: $count")
            BookStatus.BORROWED -> println("Выдано: $count")
            BookStatus.LOST -> println("Потеряно: $count")
            BookStatus.RESERVED -> println("Зарезервировано: $count")
            BookStatus.ARCHIVED -> println("В архиве: $count")
        }
    }
    println()
    // По жанрам
    val genreCounts = mutableMapOf<Genre, Int>()
    for (book in books) genreCounts[book.genre] = genreCounts.getOrDefault(book.genre, 0) + 1
    println("По жанрам:")
    for ((genre, count) in genreCounts) {
        println("$genre: $count")
    }
    println()
    // Самая старая и новая
    val oldest = books.minByOrNull { it.year }!!
    val newest = books.maxByOrNull { it.year }!!
    println("Самая старая: ${oldest.title} (${oldest.year})")
    println("Самая новая: ${newest.title} (${newest.year})")
    // Средний год
    val avgYear = books.map { it.year }.average().toInt()
    println("Средний год издания: $avgYear")
    println()
    // Фильтры
    println("=== ФИЛЬТРЫ ===")
    // Доступные книги
    println("Доступные книги:")
    for (book in books) {
        if (book.status == BookStatus.AVAILABLE) println(" - ${book.title}")
    }
    println()

    // По жанру
    val searchGenre = "FANTASY"
    println("Книги в жанре $searchGenre:")
    for (book in books) {
        if (book.genre.name == searchGenre) println(" - ${book.title}")
    }
    println()

    // После определенного года
    val searchYear = 1960
    println("Книги после $searchYear года:")
    for (book in books) {
        if (book.year > searchYear) println(" - ${book.title} (${book.year})")
    }
    println()

    // Действия с книгами
    println("=== ДЕЙСТВИЯ С КНИГАМИ ===")

    fun getBook(num: Int): Book? = if (num in 1..books.size) books[num - 1] else null

    // Выдать книгу
    val bookToBorrow = getBook(1)
    if (bookToBorrow != null) {
        if (bookToBorrow.status == BookStatus.AVAILABLE) {
            bookToBorrow.status = BookStatus.BORROWED
            println("Книга '${bookToBorrow.title}' выдана.")
        } else {
            println("Книгу '${bookToBorrow.title}' нельзя выдать, она ${bookToBorrow.status.displayName}.")
        }
    }

    // Вернуть книгу
    val bookToReturn = getBook(2)
    if (bookToReturn != null && bookToReturn.status == BookStatus.BORROWED) {
        bookToReturn.status = BookStatus.AVAILABLE
        println("Книга '${bookToReturn.title}' возвращена.")
    }

    // Потерять книгу
    val bookToLose = getBook(3)
    if (bookToLose != null) {
        bookToLose.status = BookStatus.LOST
        println("Книга '${bookToLose.title}' потеряна.")
    }
    println()

    // ОТЧЁТ
    println("=== ОТЧЁТ ПО КАТАЛОГУ ===")
    println("Всего книг: ${books.size}")
    println()

    println("По статусам:")
    for ((status, count) in statusCounts) {
        val percent = count * 100 / books.size
        println("${status.name}: $count ($percent%)")
    }
    println()

    println("По жанрам:")
    for ((genre, count) in genreCounts) {
        val percent = count * 100 / books.size
        println("${genre.name}: $count ($percent%)")
    }
    println()

    println("Самая старая книга: ${oldest.title} (${oldest.year})")
    println("Самая новая книга: ${newest.title} (${newest.year})")
    println("Средний год: $avgYear")
}