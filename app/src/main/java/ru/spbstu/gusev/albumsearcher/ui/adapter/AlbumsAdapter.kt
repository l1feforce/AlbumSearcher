package ru.spbstu.gusev.albumsearcher.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.album_item.view.*
import ru.spbstu.gusev.albumsearcher.R
import ru.spbstu.gusev.albumsearcher.model.Album

class AlbumsAdapter(val context: Context):
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var albumList = emptyList<Album>()
    private var onItemClickListener:((Album) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.album_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(albumList[position])

    fun updateData(albumList: List<Album>) {
        this.albumList = albumList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(action: (Album) -> Unit) {
        onItemClickListener = action
    }

    inner class AlbumViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(album: Album) {
            view.apply {
                author_text.text = album.artistName
                album_title_text.text = album.collectionName
                Glide.with(context).load(album.artworkUrl60).into(album_artwork_icon)
                setOnClickListener {
                    onItemClickListener?.invoke(album)
                }
            }
        }
    }
}
