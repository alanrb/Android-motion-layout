package dev.tuongnt.tinder.presentation.extension

import android.text.format.DateFormat
import java.util.*

/**
 * Created by Tuong (Alan) on 9/14/20.
 * Copyright (c) 2020 Buuuk. All rights reserved.
 */

fun String.toDateString(): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = toLong() * 1000L
    return DateFormat.format("dd/MM/yyyy", calendar).toString()
}