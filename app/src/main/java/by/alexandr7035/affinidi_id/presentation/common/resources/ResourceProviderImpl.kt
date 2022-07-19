package by.alexandr7035.affinidi_id.presentation.common.resources

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceProviderImpl(private val context: Context): ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    override fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }
}