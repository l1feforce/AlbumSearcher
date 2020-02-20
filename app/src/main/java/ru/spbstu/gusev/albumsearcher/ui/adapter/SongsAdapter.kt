package ru.spbstu.gusev.albumsearcher.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.album_item.view.author_text
import kotlinx.android.synthetic.main.song_item.view.*
import ru.spbstu.gusev.albumsearcher.R
import ru.spbstu.gusev.albumsearcher.model.Song

class SongsAdapter(val context: Context):
    RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    private var songList = emptyList<Song>()
    private var onItemClickListener:((Song) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder =
        SongsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.song_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) =
        holder.bind(songList[position])

    fun updateList(albumList: List<Song>) {
        this.songList = albumList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(action: (Song) -> Unit) {
        onItemClickListener = action
    }

    inner class SongsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(Song: Song) {
            view.apply {
                author_text.text = Song.artistName
                song_name_text.text = Song.trackName
                setOnClickListener {
                    onItemClickListener?.invoke(Song)
                }
            }
        }
    }
}
