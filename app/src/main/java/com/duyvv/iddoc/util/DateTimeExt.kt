package com.duyvv.iddoc.util

import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeExt {
    fun convertToDate(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(input)
        return outputFormat.format(date ?: Date())
    }
}

fun Fragment.openPickDate(onPicked: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val todayInMillis = calendar.timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val constraints = CalendarConstraints.Builder()
        .setEnd(todayInMillis)
        .setValidator(DateValidatorPointBackward.before(todayInMillis))
        .build()
    val builder = MaterialDatePicker.Builder.datePicker()
        .setSelection(todayInMillis)
        .setCalendarConstraints(constraints)

    val datePicker = builder.build()

    datePicker.addOnPositiveButtonClickListener { selection ->
        val selectedDate = Date(selection as Long)
        onPicked(dateFormat.format(selectedDate))
    }

    datePicker.show(childFragmentManager, datePicker.toString())
}