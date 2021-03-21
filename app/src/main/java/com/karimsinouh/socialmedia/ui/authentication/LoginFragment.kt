package com.karimsinouh.socialmedia.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.FragmentLoginBinding
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment:Fragment(R.layout.fragment_login) {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var nav:NavController
    @Inject lateinit var auth:FirebaseAuth

    private lateinit var dialog:MaterialAlertDialogBuilder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser!=null)
            navigateHome()

        binding= FragmentLoginBinding.bind(view)
        nav=findNavController()

        onClickListeners()

        dialog= MaterialAlertDialogBuilder(requireContext()).setTitle("Alert")

        binding.email.editText?.doOnTextChanged { _, _, _, _ -> binding.email.error=null }
        binding.password.editText?.doOnTextChanged { _, _, _, _ -> binding.password.error=null }

    }

    private fun onClickListeners(){
        binding.signup.setOnClickListener {
            navigateToSignUp()
        }

        binding.login.setOnClickListener {
            login()
        }
    }

    private fun login(){


        val email=binding.email.editText?.text.toString()
        val password=binding.password.editText?.text.toString()

        if (!email.contains("@") || !email.contains(".")){
            binding.email.error="Please enter a valid email"
            return
        }

        if (password.length<6){
            binding.password.error="6 letters at least"
            return
        }

        hideViews()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful)
                navigateHome()
            else
                dialog.setMessage(it.exception?.message!!).show()
            showViews()
        }

    }

    private fun navigateToSignUp()=nav.navigate(R.id.login_to_signUpFragment)
    private fun navigateHome()=nav.navigate(R.id.login_to_homeFragment)

    private fun hideViews()=binding.apply {
        bar.show()
        login.hide()
        signup.hide()
        dontHaveAccount.hide()
    }

    private fun showViews()=binding.apply {
        bar.hide()
        login.show()
        signup.show()
        dontHaveAccount.show()
    }

}