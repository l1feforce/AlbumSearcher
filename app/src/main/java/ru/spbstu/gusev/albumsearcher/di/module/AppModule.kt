package ru.spbstu.gusev.albumsearcher.di.module

import android.content.Context
import ru.spbstu.gusev.albumsearcher.data.retrofit.ApiProvider
import ru.spbstu.gusev.albumsearcher.data.retrofit.SearchApi
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)

        bind(SearchApi::class.java).toProvider(
            ApiProvider::class.java)
    }
}