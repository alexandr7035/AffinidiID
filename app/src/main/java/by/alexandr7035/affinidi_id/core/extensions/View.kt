package by.alexandr7035.affinidi_id.core.extensions

import android.view.View
import by.alexandr7035.affinidi_id.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, isPositive: Boolean, snackBarLength: Int) {
    val snackBar = Snackbar.make(this, message, snackBarLength)

    if (isPositive) {
        snackBar.view.setBackgroundResource(R.drawable.background_snackbar_positive)
    }
    else {
        snackBar.view.setBackgroundResource(R.drawable.background_snackbar_negative)
    }

    snackBar.show()
}