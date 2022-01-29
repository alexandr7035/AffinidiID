package by.alexandr7035.affinidi_id.core.extensions

import android.view.View
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, snackBarMode: SnackBarMode, snackBarLength: Int) {
    val snackBar = Snackbar.make(this, message, snackBarLength)

    when (snackBarMode) {
        SnackBarMode.Positive -> {
            snackBar.view.setBackgroundResource(R.drawable.bg_snackbar_positive)
        }

        SnackBarMode.Negative -> {
            snackBar.view.setBackgroundResource(R.drawable.bg_snackbar_negative)
        }

        SnackBarMode.Neutral -> {
            snackBar.view.setBackgroundResource(R.drawable.bg_snackbar_neutral)
        }
    }

    snackBar.show()
}