package ru.spbstu.gusev.albumsearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.spbstu.gusev.albumsearcher.data.ResultWrapper
import ru.spbstu.gusev.albumsearcher.data.SearchRepository
import ru.spbstu.gusev.albumsearcher.model.Album
import ru.spbstu.gusev.albumsearcher.model.Song
import javax.inject.Inject


class SearchViewModel : BaseViewModel() {
    @Inject
    lateinit var searchRepository: SearchRepository
    val albumResponse = MutableLiveData<ResultWrapper<List<Album>>>()
    val songResponse = MutableLiveData<ResultWrapper<List<Song>>>()
    val isLoading = MutableLiveData(false)

    fun getAlbumList(query: String) {
        isLoading.value = true
        GlobalScope.launch(Dispatchers.Main) {
            albumResponse.value = searchRepository.getAlbumList(query)
            isLoading.value = false
        }
    }

    fun getAlbumDetails(id: String) {
        isLoading.value = true
        GlobalScope.launch(Dispatchers.Main) {
            songResponse.value = searchRepository.getAlbumDetails(id)
            isLoading.value = false
        }
    }
}
