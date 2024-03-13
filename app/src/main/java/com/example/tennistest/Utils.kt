package com.example.tennistest

/**
 * Класс-репозиторий для хранения глобальных переменных
 */
class Utils {

    companion object {
        // Массив ссылок на массивы ответов их ресурсов
        var answersLinks: List<Int> = listOf(
            R.array.array1, R.array.array2, R.array.array2, R.array.array2, R.array.array3,
            R.array.array3, R.array.array3, R.array.array4, R.array.array4)
        // Массив ссылок на картинки - индикаторы прогресса
        var states: List<Int> = listOf(
            R.drawable.state1, R.drawable.state2, R.drawable.state3, R.drawable.state4, R.drawable.state5,
            R.drawable.state6, R.drawable.state7,R.drawable.state8, R.drawable.state9)
    }
}