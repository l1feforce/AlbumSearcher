package ru.spbstu.gusev.albumsearcher.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.spbstu.gusev.albumsearcher.App
import ru.spbstu.gusev.albumsearcher.R
import ru.spbstu.gusev.albumsearcher.di.DI
import ru.spbstu.gusev.albumsearcher.extension.getColorFromTheme
import ru.spbstu.gusev.albumsearcher.viewmodel.SearchViewModel
import toothpick.Toothpick

abstract class BaseFragment : Fragment() {
    abstract val layoutRes: Int
    abstract val menuRes: Int?
    abstract val progressBar: ProgressBar?
    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuRes?.let {
            setHasOptionsMenu(true)
        }
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuRes?.let {
            inflater.inflate(it, menu)
            @ColorInt val color = requireContext().getColorFromTheme(R.attr.colorOnPrimary)
            menu.forEach {
                it.icon.setColorFilter(
                    color, PorterDuff.Mode.SRC_ATOP
                )
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(activity?.application as App).get(
                SearchViewModel::class.java
            )
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }

    fun showProgress(isLoading: Boolean) {
        progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}