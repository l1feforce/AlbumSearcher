package ru.spbstu.gusev.albumsearcher.model

data class Song (
    var trackName: String = "",
    var collectionName: String = "",
    var artistName: String = "",
    var artworkUrl100: String = "",
    var releaseDate: String = "",
    var copyright: String = ""
)
