package org.example
//Kotlin lang.
import kotlin.random.Random
import kotlin.system.exitProcess

fun iExit() {
    exitProcess(status = 0)
}

fun main() {
    println("=== Игра 'Угадай число' ===")

    var playAgain = true
    while (playAgain) {
        val (maxNumber, maxAttempts) = chooseDifficulty()

        val secretNumber = Random.nextInt(0, maxNumber + 1)
        var attemptsLeft = maxAttempts
        var guessed = false

        println("\nЯ загадал число от 0 до $maxNumber.")
        println("У вас $maxAttempts попыток. Удачи!\n")

        while (attemptsLeft > 0 && !guessed) {
            print("Попытка ${maxAttempts - attemptsLeft + 1}/$maxAttempts. Введите число: ")
            val input = readlnOrNull()?.trim()
            val guess = input?.toIntOrNull()

            if (guess == null || guess < 0 || guess > maxNumber) {
                println("Некорректный ввод. Введите целое число от 0 до $maxNumber.")
                continue
            }

            attemptsLeft--

            when {
                guess == secretNumber -> {
                    guessed = true
                    println("Поздравляем! Вы угадали число $secretNumber!")
                }

                guess < secretNumber -> {
                    println("Загаданное число БОЛЬШЕ.")
                    if (attemptsLeft > 0) println("Осталось попыток: $attemptsLeft")
                }

                else -> {
                    println("Загаданное число МЕНЬШЕ.")
                    if (attemptsLeft > 0) println("Осталось попыток: $attemptsLeft")
                }
            }
        }

        if (!guessed) {
            println("Вы проиграли. Было загадано число $secretNumber.")
        }

        playAgain = askRepeat()
    }

    println("Спасибо за игру!")
}

fun chooseDifficulty(): Pair<Int, Int> {
    while (true) {
        println("\nВыберите уровень сложности:")
        println("===============================")
        println("0 - Exit Game")
        println("1 - Лёгкий (0..10, 3 попытки)")
        println("2 - Средний (0..100, 5 попыток)")
        println("3 - Сложный (0..1000, 7 попыток)")
        println("4 - Кастомный")
        print("Ваш выбор: ")

        when (readlnOrNull()?.trim()) {
            "0" -> iExit()
            "1" -> return 10 to 3
            "2" -> return 100 to 5
            "3" -> return 1000 to 7
            "4" -> {
                val max = readPositiveInt("Введите максимальное число диапазона (>= 1): ")
                val attempts = readPositiveInt("Введите количество попыток (>= 1): ")
                return max to attempts
            }

            else -> println("Некорректный выбор, попробуйте снова.")
        }
    }
}

fun readPositiveInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val value = readlnOrNull()?.trim()?.toIntOrNull()
        if (value != null && value >= 1) return value
        println("Некорректный ввод. Введите целое число >= 1.")
    }
}

fun askRepeat(): Boolean {
    while (true) {
        print("\nПовторить игру еще раз? 1 - да/0 - нет: ")
        when (readlnOrNull()?.trim()) {
            "1" -> return true
            "0" -> return false
            else -> println("Введите 1 или 0.")
        }
    }
}
