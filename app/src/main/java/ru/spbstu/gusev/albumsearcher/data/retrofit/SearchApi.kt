package ru.spbstu.gusev.albumsearcher.data.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.spbstu.gusev.albumsearcher.model.Album
import ru.spbstu.gusev.albumsearcher.model.SearchResult
import ru.spbstu.gusev.albumsearcher.model.Song

interface SearchApi {

    @GET("/search?entity=album")
    fun getAlbums(@Query("term") query: String): Deferred<SearchResult<Album>>

    @GET("/lookup?entity=song")
    fun getAlbumDetails(@Query("id") id: String): Deferred<SearchResult<Song>>
}