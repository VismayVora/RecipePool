package com.example.recipepool.screens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipepool.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nav: NavigationView
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //code for setting status bar white
        window.statusBarColor = ContextCompat.getColor(this, com.example.recipepool.R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // shared preferences to
        val pref = applicationContext.getSharedPreferences("SharedPref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()

        nav = binding.leftNav

        val header = nav.getHeaderView(0)
        val headerImage: CircleImageView = header.findViewById(com.example.recipepool.R.id.left_nav_header_image)
        val headerName: TextView = header.findViewById(com.example.recipepool.R.id.left_nav_header_name)
        val headerEmail: TextView = header.findViewById(com.example.recipepool.R.id.left_nav_header_email)

        sharedPreferences = applicationContext.getSharedPreferences("SharedPref", MODE_PRIVATE)
        val name = sharedPreferences.getString("username", null).toString()
        headerName.text = name
        headerEmail.text = sharedPreferences.getString("email", null).toString()

        // image
        /*val drawable: TextDrawable = TextDrawable.builder()
            .buildRect("A", Color.RED)*/



        val navHostFragment = supportFragmentManager.findFragmentById(com.example.recipepool.R.id.fragmentContainerView)
                as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        drawer = binding.drawer
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, com.example.recipepool.R.string.open, com.example.recipepool.R.string.close)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.isDrawerIndicatorEnabled = true
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, com.example.recipepool.R.color.black)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // left nav navigation
        nav.setNavigationItemSelectedListener {
            drawer.closeDrawer(GravityCompat.START)
            when(it.itemId){
                com.example.recipepool.R.id.Logout -> {
                    val intent = Intent(this,Login::class.java)
                    editor.clear()
                    editor.apply()
                    startActivity(intent)
                    finish()
                }

                com.example.recipepool.R.id.profile_left -> {
                    val intent = Intent(this, ProfileActivityNew::class.java)
                    startActivity(intent)
                }

                com.example.recipepool.R.id.inventory -> {
                    val intent = Intent(this,ManageInventory::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        // recipies

    }

    // search bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.recipepool.R.menu.search, menu)
        val searchBtn = menu.findItem(com.example.recipepool.R.id.search)
        val search = searchBtn?.actionView as SearchView
        search.queryHint = "Search Here"

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                //  val array = query?.split("\\s".toRegex())?.toTypedArray()

                val intent = Intent(this@MainActivity,Search::class.java)

                // intent.putExtra("search",array)
                intent.putExtra("search",query)
                startActivity(intent)

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

}