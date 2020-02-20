package ru.spbstu.gusev.albumsearcher.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider
@Inject constructor() : Provider<SearchApi> {

    private val baseUrl = "https://itunes.apple.com"
    override fun get(): SearchApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()
            .create(SearchApi::class.java)
}