package com.example.androidmentoringprogram

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
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
}