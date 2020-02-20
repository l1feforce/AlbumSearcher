package ru.spbstu.gusev.albumsearcher.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_empty.*
import ru.spbstu.gusev.albumsearcher.R
import ru.spbstu.gusev.albumsearcher.data.ResultWrapper
import ru.spbstu.gusev.albumsearcher.extension.snackbarError
import ru.spbstu.gusev.albumsearcher.model.Album
import ru.spbstu.gusev.albumsearcher.ui.adapter.AlbumsAdapter

class SearchFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_search
    override val menuRes: Int?
        get() = R.menu.search_menu
    override val progressBar: ProgressBar?
        get() = progress_bar
    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var menuItem: MenuItem

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeForData()
        setupRecycler()
        search_icon.setOnClickListener {
            menuItem.expandActionView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getAlbumList(it)
                    albums_recycler.smoothScrollToPosition(0)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        val closeButton =
            searchView.findViewById<AppCompatImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton?.let {
            it.setOnClickListener {
                hideContent()
                searchView.setQuery("", false)
            }
        }
    }

    private fun setupRecycler() {
        albumsAdapter = AlbumsAdapter(
            requireActivity().applicationContext
        )
        val recyclerLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            albums_recycler.context,
            recyclerLayoutManager.orientation
        )
        albums_recycler.apply {
            adapter = albumsAdapter
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
        albumsAdapter.setOnItemClickListener {
            viewModel.getAlbumDetails(it.collectionId)
            findNavController().navigate(R.id.albumDetailsFragment)
        }
    }

    private fun subscribeForData() {
        viewModel.apply {
            albumResponse.observe(this@SearchFragment, Observer {
                it?.let {
                    updateUi(it)
                }
            })
            isLoading.observe(this@SearchFragment, Observer {
                it?.let {
                    showProgress(it)
                }
            })
        }
    }

    private fun updateUi(albumResponse: ResultWrapper<List<Album>>) {
        when (albumResponse) {
            is ResultWrapper.Success -> {
                val albumList = albumResponse.value
                if (albumList.isNotEmpty()) {
                    albumsAdapter.updateData(albumList.sortedBy { it.collectionName })
                    showContent()
                } else {
                    snackbarError(getString(R.string.empty_list_error))
                    hideContent()
                }
            }
            is ResultWrapper.GenericError -> {
                snackbarError(albumResponse.error?.error_description ?: "Error")
            }
            is ResultWrapper.NetworkError -> {
                snackbarError(getString(R.string.network_error))
            }
        }
    }

    private fun hideContent() {
        albums_recycler.visibility = View.GONE
        empty_search_include.visibility = View.VISIBLE
    }

    private fun showContent() {
        albums_recycler.visibility = View.VISIBLE
        empty_search_include.visibility = View.GONE
    }
}
