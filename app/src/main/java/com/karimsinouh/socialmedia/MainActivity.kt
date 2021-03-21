package com.karimsinouh.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.karimsinouh.socialmedia.databinding.ActivityMainBinding
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var nav:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nav=findNavController(R.id.navHost)

        binding.bottomNavigationView.setupWithNavController(nav)

        nav.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.home->binding.bottomNavigationView.show()
                R.id.search->binding.bottomNavigationView.show()
                R.id.videos->binding.bottomNavigationView.show()
                R.id.notifications->binding.bottomNavigationView.show()
                R.id.profile->binding.bottomNavigationView.show()
                else->binding.bottomNavigationView.hide()
            }
        }

    }
}