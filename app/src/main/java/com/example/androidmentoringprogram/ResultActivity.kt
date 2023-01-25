package com.example.androidmentoringprogram

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity(){

    private lateinit var db: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        db = DBHelper(this)
        (db as DBHelper).addSong(105L, "liiiink", "AAA", "ROCK", "NAMEEE")
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.songResultName).text = (db as DBHelper).findSong("AAA")?.name
    }

}