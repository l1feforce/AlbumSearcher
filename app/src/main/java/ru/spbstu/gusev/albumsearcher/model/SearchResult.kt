package ru.spbstu.gusev.albumsearcher.model

data class SearchResult <T> (
    val resultCount: Int,
    val results: List<T>
)