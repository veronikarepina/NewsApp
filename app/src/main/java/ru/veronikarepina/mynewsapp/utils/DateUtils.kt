package ru.veronikarepina.mynewsapp.utils

import java.text.SimpleDateFormat

object DateUtils {
    const val isoDateType = "yyy-MM-dd'T'HH:mm:ss"
    const val defaultDateType = "dd.MM.yyyy"
    fun toDefaultDate(text: String): String? {
        val from = SimpleDateFormat(isoDateType)
        val to = SimpleDateFormat(defaultDateType)
        return to.format(from.parse(text))
    }
}