package com.example.androidmentoringprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, findViewById(R.id.drawerLayout), R.string.open, R.string.close)
        findViewById<DrawerLayout>(R.id.drawerLayout).addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<NavigationView>(R.id.navigationView).setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.settingsMenuItem -> {
                    Toast.makeText(this, "Settings option selected", Toast.LENGTH_SHORT).show()
                }
                R.id.profileMenuItem -> {
                    Toast.makeText(this, "Profile option selected", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}