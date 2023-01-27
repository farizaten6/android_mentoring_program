package com.example.androidmentoringprogram

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        const val TABLE_NAME = "Songs"
        const val ID_COL = "ID"
        const val SONG_LINK_COL = "SONG_LINK"
        const val ARTIST_COl = "ARTIST"
        const val GENRE_COL = "GENRE"
        const val NAME_COL = "NAME"
        const val DATABASE_NAME = "MyDB"
        const val DATABASE_VERSION = 1
    }

    private val myCR: ContentResolver
    init {
        myCR = context!!.contentResolver
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                SONG_LINK_COL + " INTEGER, " +
                ARTIST_COl + " TEXT, " +
                GENRE_COL + " TEXT, " +
                NAME_COL + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addSong(id: Long, songLink: Int, artist : String, genre : String, name : String){
        val values = ContentValues()
        values.put(ID_COL, id)
        values.put(SONG_LINK_COL, songLink)
        values.put(ARTIST_COl, artist)
        values.put(GENRE_COL, genre)
        values.put(NAME_COL, name)

        myCR.insert(SongsProvider.CONTENT_URI, values)
    }

    fun findSong(artistname: String?, genrename: String?, songname: String?): List<Song> {
        val projection = arrayOf(ID_COL, SONG_LINK_COL, ARTIST_COl, GENRE_COL, NAME_COL)
        var selection: String? = null
        artistname?.let { selection = "ARTIST = \"$artistname\"" }
        genrename?.let { selection = "GENRE = \"$genrename\""  }
        songname?.let { selection = "NAME = \"$songname\""  }
        val cursor = myCR.query(SongsProvider.CONTENT_URI, projection, selection, null, null) ?: return emptyList()
        val resultList = mutableListOf<Song>()
        val idIndex = cursor.getColumnIndex(ID_COL)
        val songLinkIndex = cursor.getColumnIndex(SONG_LINK_COL)
        val artistIndex = cursor.getColumnIndex(ARTIST_COl)
        val genreIndex = cursor.getColumnIndex(GENRE_COL)
        val nameIndex = cursor.getColumnIndex(NAME_COL)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(idIndex)
            val songLink = cursor.getInt(songLinkIndex)
            val artist = cursor.getString(artistIndex)
            val genre = cursor.getString(genreIndex)
            val name = cursor.getString(nameIndex)
            resultList.add(Song(id, songLink, artist, genre, name))
        }
        cursor.close()
        return resultList
    }
}