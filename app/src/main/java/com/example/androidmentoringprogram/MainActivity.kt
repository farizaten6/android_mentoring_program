package com.example.androidmentoringprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.androidmentoringprogram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navigationView.setNavigationItemSelectedListener {
                val lessonOneIntent = Intent(this@MainActivity, ViewPagerActivity::class.java)
                val lessonTwoIntent = Intent(this@MainActivity, PlayerActivity::class.java)

                when(it.itemId) {
                    R.id.lessonOneItem -> {
                        startActivity(lessonOneIntent)
                    }
                    R.id.lessonTwoItem -> {
                        startActivity(lessonTwoIntent)
                    }
                    R.id.lessonThreeItem -> {
                        Toast.makeText(this@MainActivity, "Lesson 3 was selected", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
/*        if (toggle.onOptionsItemSelected(item)){
            true
        }*/
        return super.onOptionsItemSelected(item)
    }
}