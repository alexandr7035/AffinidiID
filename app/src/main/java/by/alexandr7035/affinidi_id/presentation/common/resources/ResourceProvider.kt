package by.alexandr7035.affinidi_id.presentation.common.resources

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String

    fun getColor(@ColorRes id: Int): Int
}