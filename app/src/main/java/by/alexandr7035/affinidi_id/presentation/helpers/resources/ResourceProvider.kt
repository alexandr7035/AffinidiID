package by.alexandr7035.affinidi_id.presentation.helpers.resources

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String

    fun getColor(@ColorRes id: Int): Int
}