package com.karimsinouh.socialmedia.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.ActivityMainBinding
import com.karimsinouh.socialmedia.utils.USER_ID
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        const val ACTION_VIEW_POST="viewPostAction"
        const val KEY_POST="post"
    }

    private lateinit var binding:ActivityMainBinding
    private lateinit var nav:NavController
    private lateinit var vm:MainViewModel

    @Inject lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nav=findNavController(R.id.navHost)
        vm=ViewModelProvider(this).get(MainViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(nav)

        nav.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.home ->binding.bottomNavigationView.show()
                R.id.search ->binding.bottomNavigationView.show()
                R.id.videos ->binding.bottomNavigationView.show()
                R.id.notifications ->binding.bottomNavigationView.show()
                R.id.profile ->binding.bottomNavigationView.show()
                else->binding.bottomNavigationView.hide()
            }
        }


        auth.currentUser?.let {
            vm.loadCurrentUser(it.uid)
            vm.loadPosts()
        }

    }
}