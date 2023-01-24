package com.example.androidmentoringprogram

import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader


class ResultActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private val AUTHORITY = "com.c1ctech.mycontacts"
    private val BASE_PATH = "contacts"
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$BASE_PATH")

    private val SONGS = 1
    private val SONGS_ID = 2

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, SONGS)
        uriMatcher.addURI(AUTHORITY, "$BASE_PATH/#", SONGS_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return if (id === 0) {
            CursorLoader(this, CONTENT_URI, null, null, null, null)
        } else null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        val list: MutableList<Song> = ArrayList()

        while (data.moveToNext()) {
            val artist: String = data.getString(1)
            val genre: String = data.getString(2)
            val name: String = data.getString(3)
            val song = Song(artist, genre, name)
            list.add(song)
        }

        adapter = SongsAdapter(this, list)
        rv_list.setAdapter(adapter)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}