package ru.spbstu.gusev.albumsearcher.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_album_details.*
import ru.spbstu.gusev.albumsearcher.R
import ru.spbstu.gusev.albumsearcher.data.ResultWrapper
import ru.spbstu.gusev.albumsearcher.extension.snackbarError
import ru.spbstu.gusev.albumsearcher.model.Song
import ru.spbstu.gusev.albumsearcher.ui.adapter.SongsAdapter


class AlbumDetailsFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_album_details
    override val menuRes: Int?
        get() = null
    override val progressBar: ProgressBar?
        get() = progress_bar
    private lateinit var songsAdapter: SongsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecycler()
        subscribeForData()
    }

    private fun subscribeForData() {
        viewModel.apply {
            songResponse.observe(this@AlbumDetailsFragment, Observer {
                it?.let {
                    updateUi(it)
                }
            })
            isLoading.observe(this@AlbumDetailsFragment, Observer {
                it?.let {
                    showProgress(it)
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(songResponse: ResultWrapper<List<Song>>) {
        when (songResponse) {
            is ResultWrapper.Success -> {
                val songList = songResponse.value
                if (songList.isNotEmpty()) {
                    songsAdapter.updateList(songList.filter { it.trackName.isNotEmpty() })
                    val firstElement = songList.first()
                    Glide.with(requireContext()).load(firstElement.artworkUrl100)
                        .into(album_artwork_icon)
                    album_title_text.text = firstElement.collectionName
                    author_text.text =
                        "${firstElement.artistName}, ${firstElement.releaseDate.split("-").first()}"
                    label_text.text = firstElement.copyright
                }
            }
            is ResultWrapper.GenericError -> {
                snackbarError(songResponse.error?.error_description ?: getString(R.string.error))
            }
            is ResultWrapper.NetworkError -> {
                snackbarError(getString(R.string.network_error))
            }
        }
    }

    private fun setupRecycler() {
        songsAdapter =
            SongsAdapter(requireActivity().applicationContext)
        val recyclerLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            songs_recycler.context,
            recyclerLayoutManager.orientation
        )
        songs_recycler.apply {
            adapter = songsAdapter
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
    }

}
