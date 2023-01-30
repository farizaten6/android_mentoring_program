package com.example.androidmentoringprogram

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

private const val SONGS = 0
private const val SONG_ID = 0

class SongsProvider: ContentProvider() {

    private var myDB: DBHelper? = null
    companion object {
        val AUTHORITY = "com.example.androidmentoringprogram"
        val TABLE = "Songs"
        val CONTENT_URI : Uri = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE)
    }
    val uriMatcher = UriMatcher (UriMatcher.NO_MATCH)
    init {
        uriMatcher.addURI(AUTHORITY, TABLE, SONGS)
        uriMatcher.addURI(AUTHORITY, "$TABLE/#", SONG_ID)
    }

    override fun onCreate(): Boolean {
        myDB = DBHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?)
    : Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = DBHelper.TABLE_NAME

        val cursor = queryBuilder.query(
            myDB?.readableDatabase,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder)

        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val sqlDB = myDB!!.writableDatabase
        val rowId: Long
        when (uriMatcher.match(uri)) {
            SONGS -> rowId = sqlDB.insert(DBHelper.TABLE_NAME, null, values)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.parse("$TABLE/$rowId")
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

}