package by.alexandr7035.affinidi_id.core.extensions

import android.text.TextUtils
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearError() {

    if (!TextUtils.isEmpty(error)) {
        error = null
        isErrorEnabled = false
    }
}