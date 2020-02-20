package ru.spbstu.gusev.albumsearcher.extension

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.spbstu.gusev.albumsearcher.R

fun Fragment.snackbarError(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).setBackgroundTint(
        requireContext().getColorFromTheme(
            R.attr.colorError
        )
    ).setTextColor(requireContext().getColorFromTheme(R.attr.colorOnError)).show()
}