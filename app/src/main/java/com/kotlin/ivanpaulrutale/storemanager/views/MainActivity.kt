package com.kotlin.ivanpaulrutale.storemanager.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kotlin.ivanpaulrutale.storemanager.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val destinations = setOf(
            R.id.fragmentSearch,
            R.id.fragmentCheckIn,
            R.id.fragmentReports
        )

        appBarConfig = AppBarConfiguration(destinations, drawer_layout)
        navController = findNavController(R.id.main_fragments_container)
        setupActionBarWithNavController(navController, appBarConfig)
        navigation_drawer.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
<<<<<<< HEAD

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var newFragment:Fragment = Fragment()
        when (item.itemId) {
            R.id.nav_search -> {
                newFragment = Search()
            }
            R.id.nav_check_in -> {
                newFragment = CheckIn()
            }
            R.id.nav_check_out -> {
                newFragment = CheckOut()
            }
            R.id.nav_reports -> {
                newFragment = Reports()
            }
        }
        changeFromActivityToFragment(this, newFragment)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame,fragment)
        fragmentTransaction.commit()
    }
=======
>>>>>>> 011cee12cd25830e7f505e0f2278b1bdc3c891bf
}
