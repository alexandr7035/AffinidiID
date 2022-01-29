package by.alexandr7035.affinidi_id.presentation.common

sealed class SnackBarMode {
    object Positive: SnackBarMode()

    object Negative: SnackBarMode()

    object Neutral: SnackBarMode()
}
