package com.karimsinouh.socialmedia.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.karimsinouh.socialmedia.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment:Fragment(R.layout.fragment_splash) {

    private lateinit var nav:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav=findNavController()

        CoroutineScope(Dispatchers.Main).launch{
            delay(1500)
            nav.navigate(R.id.splash_to_homeFragment)
        }

    }

}