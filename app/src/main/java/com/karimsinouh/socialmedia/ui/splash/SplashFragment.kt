package com.karimsinouh.socialmedia.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.karimsinouh.socialmedia.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment:Fragment(R.layout.fragment_splash) {

    private lateinit var nav:NavController
    @Inject lateinit var auth:FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav=findNavController()

        CoroutineScope(Dispatchers.Main).launch{
            delay(1000)
            if (auth.currentUser!=null)
                nav.navigate(R.id.splash_to_homeFragment)
            else
                nav.navigate(R.id.splash_to_loginFragment)
        }

    }

}