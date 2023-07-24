import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun Context.showKeyboard() = getActivity()?.showKeyboard()
fun Context.hideKeyboard() = getActivity()?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.showKeyboard()
fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Activity.showKeyboard() = WindowCompat.getInsetsController(window, window.decorView)?.show(WindowInsetsCompat.Type.ime())
fun Activity.hideKeyboard() = WindowCompat.getInsetsController(window, window.decorView)?.hide(WindowInsetsCompat.Type.ime())

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }
}