package com.example.androidmentoringprogram

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

private const val SONGS = 1
private const val SONGS_ID = 1

class SongsProvider: ContentProvider() {

    private var myDB: DBHelper? = null
    companion object {
        val AUTHORITY = "com.example.androidmentoringprogram.SongsProvider"
        val TABLE = "Songs"
        val CONTENT_URI : Uri = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE)
    }
    val uriMatcher = UriMatcher (UriMatcher.NO_MATCH)
    init {
        uriMatcher.addURI(AUTHORITY, TABLE, SONGS)
        uriMatcher.addURI(AUTHORITY, "$TABLE/#", SONGS_ID)
    }

    override fun onCreate(): Boolean {
        myDB = DBHelper(context, null, null, 1)
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                        selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = DBHelper.TABLE_NAME

        val uriType = uriMatcher.match(uri)
        when (uriType) {
            SONGS_ID -> queryBuilder.appendWhere(DBHelper.ID_COL + "="
                    + uri.lastPathSegment)
            SONGS -> {
            }
            else -> throw IllegalArgumentException("Unknown URI")
        }

        val cursor = queryBuilder.query(myDB?.readableDatabase,
            projection, selection, selectionArgs, null, null,
            sortOrder)
        cursor.setNotificationUri(context?.contentResolver,
            uri)
        return cursor
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

}