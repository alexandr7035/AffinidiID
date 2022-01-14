package by.alexandr7035.data.extensions

import timber.log.Timber

fun Timber.Forest.debug(message: String) {
    tag("DEBUG_TAG").d(message)
}