package com.example.androidmentoringprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.androidmentoringprogram.databinding.ActivityMainBinding
import com.example.androidmentoringprogram.firstlesson.ViewPagerActivity
import com.example.androidmentoringprogram.secondlesson.PlayerActivity
import com.example.androidmentoringprogram.thirdlesson.NewsActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                val lessonThreeIntent = Intent(this@MainActivity, NewsActivity::class.java)

                when(it.itemId) {
                    R.id.lessonOneItem -> {
                        startActivity(lessonOneIntent)
                    }
                    R.id.lessonTwoItem -> {
                        startActivity(lessonTwoIntent)
                    }
                    R.id.lessonThreeItem -> {
                        startActivity(lessonThreeIntent)
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
}