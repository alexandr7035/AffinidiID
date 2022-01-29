package by.alexandr7035.affinidi_id.domain.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.getUnixDateFromStringFormat(
    formatStr: String,
    timeZoneStr: String? = null,
    dateLocale: Locale = Locale.US
): Long {
    val format = SimpleDateFormat(formatStr, dateLocale)

    if (timeZoneStr != null) {
        format.timeZone = TimeZone.getTimeZone(timeZoneStr)
    }
    return format.parse(this)!!.time
}