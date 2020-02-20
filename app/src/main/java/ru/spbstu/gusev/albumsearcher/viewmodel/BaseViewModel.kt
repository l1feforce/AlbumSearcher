package ru.spbstu.gusev.albumsearcher.viewmodel

import androidx.lifecycle.ViewModel
import ru.spbstu.gusev.albumsearcher.di.DI
import toothpick.Toothpick

@Suppress("LeakingThis")
abstract class BaseViewModel : ViewModel() {
    private val scope = Toothpick.openScope(DI.APP_SCOPE)

    init {
        Toothpick.inject(this, scope)
    }
}