package ru.spbstu.gusev.albumsearcher.data

import ru.spbstu.gusev.albumsearcher.data.retrofit.SearchApi
import ru.spbstu.gusev.albumsearcher.model.Album
import ru.spbstu.gusev.albumsearcher.model.Song
import javax.inject.Inject

class SearchRepository
@Inject constructor(
    val searchApi: SearchApi
) {
    suspend fun getAlbumList(query: String): ResultWrapper<List<Album>> =
        safeApiCall { searchApi.getAlbums(query).await().results }


    suspend fun getAlbumDetails(id: String): ResultWrapper<List<Song>> =
        safeApiCall { searchApi.getAlbumDetails(id).await().results }

}
