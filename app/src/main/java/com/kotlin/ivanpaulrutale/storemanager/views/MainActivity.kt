package com.kotlin.ivanpaulrutale.storemanager.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.kotlin.ivanpaulrutale.storemanager.R
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    CheckIn.OnFragmentInteractionListener,Search.OnFragmentInteractionListener,Reports.OnFragmentInteractionListener,CheckOut.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        if(from_Report_details){
            replaceFragment(Reports())
            from_Report_details = false
        }
        else{
            replaceFragment(Search())
        }

        val mDrawerToggle = object : ActionBarDrawerToggle(
         this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                // Do whatever you want here
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if( currentFocus != null) {
                    inputManager.hideSoftInputFromWindow(
                        currentFocus.windowToken,
                        InputMethodManager.SHOW_FORCED
                    )
                }
            }
        }

        drawer_layout.addDrawerListener(mDrawerToggle)

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

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
        changeFromActivityToFragment(this,newFragment)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame,fragment)
        fragmentTransaction.commit()
    }
}