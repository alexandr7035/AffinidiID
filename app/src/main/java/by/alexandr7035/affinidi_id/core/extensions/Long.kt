package by.alexandr7035.affinidi_id.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.getStringDateFromLong(
    stringFormat: String,
    timezoneStr: String? = null,
    locale: Locale = Locale.US
): String {
    val format = SimpleDateFormat(stringFormat, locale)

    if (timezoneStr != null) {
        format.timeZone = TimeZone.getTimeZone(timezoneStr)
    }

    return format.format(this)
}