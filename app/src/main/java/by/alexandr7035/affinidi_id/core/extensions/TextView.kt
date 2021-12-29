package by.alexandr7035.affinidi_id.core.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.copyToClipboard(clipLabel: String) {
    val clipBoard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    clipBoard?.setPrimaryClip(
        ClipData.newPlainText(
            clipLabel,
            text.toString()
        )
    )
}