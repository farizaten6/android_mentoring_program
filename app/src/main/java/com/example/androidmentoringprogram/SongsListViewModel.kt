package com.example.androidmentoringprogram

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsListViewModel : ViewModel() {
    var songs = MutableLiveData<List<Song>>()

    fun getData(contentResolver: ContentResolver, namePattern: String? = null): MutableLiveData<List<Song>> {
        loadSongs(contentResolver, namePattern)
        return songs
    }

    private fun loadSongs(contentResolver: ContentResolver, namePattern: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = contentResolver.query(
                SongsProvider.CONTENT_URI,
                arrayOf(DBHelper.ID_COL, DBHelper.SONG_LINK_COL, DBHelper.ARTIST_COl, DBHelper.GENRE_COL, DBHelper.NAME_COL),
                namePattern?.takeIf { it.isNotEmpty() },
                null,
                null,
            ) ?: return@launch
            if (cursor.count == 0) {
                cursor.close()
                songs.postValue(emptyList())
                return@launch
            }
            val idIndex = cursor.getColumnIndex(DBHelper.ID_COL)
            val songLinkIndex = cursor.getColumnIndex(DBHelper.SONG_LINK_COL)
            val artistIndex = cursor.getColumnIndex(DBHelper.ARTIST_COl)
            val genreIndex = cursor.getColumnIndex(DBHelper.GENRE_COL)
            val nameIndex = cursor.getColumnIndex(DBHelper.NAME_COL)
            val list = mutableListOf<Song>()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val songLink = cursor.getInt(songLinkIndex)
                val artist = cursor.getString(artistIndex)
                val genre = cursor.getString(genreIndex)
                val name = cursor.getString(nameIndex)
                list.add(Song(id, songLink, artist, genre, name))
            }
            Log.d(TAG, "Loaded songs: $list")
            cursor.close()
            songs.postValue(list)
        }
    }

}