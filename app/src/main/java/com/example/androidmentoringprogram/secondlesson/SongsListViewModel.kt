package com.example.androidmentoringprogram.secondlesson

import android.content.ContentResolver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsListViewModel : ViewModel() {
    private val projection = arrayOf(
        DBHelper.ID_COL,
        DBHelper.SONG_LINK_COL,
        DBHelper.ARTIST_COl,
        DBHelper.GENRE_COL,
        DBHelper.NAME_COL
    )
    var songs = MutableLiveData<List<Song>>()
    var foundSongs = MutableLiveData<List<Song>>()

    fun getData(contentResolver: ContentResolver, namePattern: String? = null): MutableLiveData<List<Song>> {
        loadSongs(contentResolver, namePattern)
        return songs
    }

    fun getFoundData(cr: ContentResolver, artistname: String?, genrename: String?, songname: String?): MutableLiveData<List<Song>> {
        findSong(cr, artistname, genrename, songname)
        return foundSongs
    }

    private fun loadSongs(cr: ContentResolver, namePattern: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = cr.query(SongsProvider.CONTENT_URI, projection, namePattern?.takeIf { it.isNotEmpty() }, null, null)
                ?: return@launch

            val idIndex = cursor.getColumnIndex(DBHelper.ID_COL)
            val songLinkIndex = cursor.getColumnIndex(DBHelper.SONG_LINK_COL)
            val artistIndex = cursor.getColumnIndex(DBHelper.ARTIST_COl)
            val genreIndex = cursor.getColumnIndex(DBHelper.GENRE_COL)
            val nameIndex = cursor.getColumnIndex(DBHelper.NAME_COL)
            val resultList = mutableListOf<Song>()

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val songLink = cursor.getInt(songLinkIndex)
                val artist = cursor.getString(artistIndex)
                val genre = cursor.getString(genreIndex)
                val name = cursor.getString(nameIndex)
                resultList.add(Song(id, songLink, artist, genre, name))
            }
            cursor.close()
            songs.postValue(resultList)
        }
    }

    private fun findSong(cr: ContentResolver, artistname: String?, genrename: String?, songname: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            var selection: String? = null
            artistname?.let { selection = "ARTIST = \"$artistname\"" }
            genrename?.let { selection = "GENRE = \"$genrename\"" }
            songname?.let { selection = "NAME = \"$songname\"" }

            val cursor = cr.query(SongsProvider.CONTENT_URI, projection, selection, null, null)
                ?: return@launch

            val idIndex = cursor.getColumnIndex(DBHelper.ID_COL)
            val songLinkIndex = cursor.getColumnIndex(DBHelper.SONG_LINK_COL)
            val artistIndex = cursor.getColumnIndex(DBHelper.ARTIST_COl)
            val genreIndex = cursor.getColumnIndex(DBHelper.GENRE_COL)
            val nameIndex = cursor.getColumnIndex(DBHelper.NAME_COL)
            val resultList = mutableListOf<Song>()

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val songLink = cursor.getInt(songLinkIndex)
                val artist = cursor.getString(artistIndex)
                val genre = cursor.getString(genreIndex)
                val name = cursor.getString(nameIndex)
                resultList.add(Song(id, songLink, artist, genre, name))
            }
            cursor.close()
            foundSongs.postValue(resultList)
        }
    }

}