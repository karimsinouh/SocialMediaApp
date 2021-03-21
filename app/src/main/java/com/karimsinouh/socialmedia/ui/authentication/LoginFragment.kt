package com.karimsinouh.socialmedia.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.FragmentLoginBinding

class LoginFragment:Fragment(R.layout.fragment_login) {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var nav:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLoginBinding.bind(view)
        nav=findNavController()


        binding.signup.setOnClickListener {
            navigateToSignUp()
        }

        binding.login.setOnClickListener {
            navigateHome()
        }

    }

    private fun navigateToSignUp()=nav.navigate(R.id.login_to_signUpFragment)
    private fun navigateHome()=nav.navigate(R.id.login_to_signUpFragment)


}