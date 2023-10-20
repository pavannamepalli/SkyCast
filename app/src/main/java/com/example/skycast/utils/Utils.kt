package com.example.skycast.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class Utils {

   fun getDateFromat(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date = inputFormat.parse(date)

        // Format the date as "dd MMMM"
        val outputFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("GMT+05:30") // Set the desired time zone
        return outputFormat.format(date).toString()
    }
    fun insertNewLineAfterNWords(input: String): String {
        val words = input.split(" ")
        val result = StringBuilder()

        for (i in words.indices) {
            result.append(words[i])

            // Add a new line character after every 'n' words
            if ((i + 1) % 3 == 0) {
                result.append("\n")
            } else {
                result.append(" ")
            }
        }

        return result.toString()
    }

    fun fahrenheitToCelsius(fahrenheit: Double): String {
        return String.format("%.1f",(fahrenheit - 32) * 5 / 9)
    }
}