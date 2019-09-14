package com.example.dz_now

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dz_now.ui.accueil.AccueilFragment
import com.example.dz_now.ui.enregistres.EnregistresFragment
import com.example.dz_now.ui.explorez.ExplorezFragment
import com.example.dz_now.ui.videos.VideosFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        if (savedInstanceState == null) {
            val fragment = AccueilFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

//        val navController = findNavController(R.id.nav_host_fragment)

  //      val appBarConfiguration = AppBarConfiguration(
   //         setOf(
    //            R.id.navigation_accueil, R.id.navigation_explorez, R.id.navigation_videos, R.id.navigation_enregistres
     //       )
      //  )
      //  setupActionBarWithNavController(navController, appBarConfiguration)
       // navView.setupWithNavController(navController)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_accueil -> {
                val fragment = AccueilFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_explorez -> {
                val fragment = ExplorezFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_enregistres-> {
                val fragment = EnregistresFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_videos-> {
                val fragment = VideosFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

}
