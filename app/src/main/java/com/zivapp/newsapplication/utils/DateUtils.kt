package com.zivapp.newsapplication.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val PATTERN_YMD_TIME_SSZ = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val PATTERN_YMD_TIME_SS_ss = "yyyy-MM-dd'T'HH:mm:ss+ss:ss"
    const val PATTERN_YMD_TIME_SS_ssssZ = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
    const val PATTERN_DD_MMMM_YYYY = "dd-MM-yyyy"

    fun formatGMTToUTCDateStr(
        utcTime: String,
        pattern: String = PATTERN_DD_MMMM_YYYY
    ): String? {
        var dateTime: String? = ""

        if (utcTime.contains("Z")) {
            try {
                dateTime = parsePatternFormat(utcTime, pattern, PATTERN_YMD_TIME_SSZ)
            } catch (e: ParseException) {

                try {
                    dateTime = parsePatternFormat(utcTime, pattern, PATTERN_YMD_TIME_SS_ssssZ)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {

            try {
                dateTime = parsePatternFormat(utcTime, pattern, PATTERN_YMD_TIME_SS_ss)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return dateTime
    }

    private fun parsePatternFormat(utcTime: String, pattern: String, formatPattern: String): String? {
        val sf = SimpleDateFormat(formatPattern, Locale.getDefault())
        val sdfWithoutZ = SimpleDateFormat(pattern)
        sf.timeZone = getUTCZone()

        val date = sf.parse(utcTime)
        return sdfWithoutZ.format(date)
    }

    private fun getUTCZone(): TimeZone {
        return TimeZone.getTimeZone("UTC")
    }
}