package com.karimsinouh.socialmedia.ui.authentication

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.data.User
import com.karimsinouh.socialmedia.databinding.FragmentSignupBinding
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var nav: NavController
    private lateinit var dialog:MaterialAlertDialogBuilder

    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSignupBinding.bind(view)
        nav=findNavController()


        onClickListeners()


        binding.email.editText?.doOnTextChanged { _, _, _, _ -> binding.email.error=null }
        binding.password.editText?.doOnTextChanged { _, _, _, _ -> binding.password.error=null }
        binding.name.editText?.doOnTextChanged { _, _, _, _ -> binding.name.error=null }

        dialog= MaterialAlertDialogBuilder(requireContext()).setTitle("Alert")

    }

    private fun onClickListeners(){
        binding.backButton.setOnClickListener {
            nav.popBackStack()
        }

        binding.signup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val email=binding.email.editText?.text.toString()
        val password=binding.password.editText?.text.toString()
        val name=binding.name.editText?.text.toString()

        if (!email.contains("@") || !email.contains(".")){
            binding.email.error="Please enter a valid email"
            return
        }

        if (password.length<6){
            binding.password.error="6 letters at least"
            return
        }


        if (name.length<3){
            binding.password.error="please enter a valid name"
            return
        }

        hideViews()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                setUserName(it.result?.user?.uid!!,name)
            }
            else {
                dialog.setMessage(it.exception?.message).show()
                showViews()
            }


        }

    }

    private fun setUserName(id: String,name:String) {
        val requestChanges= UserProfileChangeRequest.Builder().apply {
            displayName = name
        }.build()

        auth.currentUser?.updateProfile(requestChanges)?.addOnCompleteListener {
            if (it.isSuccessful){
                val user=User(name)
                createUserDocument(id,user)
            }else{
                showViews()
                dialog.setMessage(it.exception?.message).show()
            }
        }
    }

    //in firestore
    private fun createUserDocument(id:String,user:User){
        firestore.collection("users").document(id).set(user).addOnCompleteListener {
            if (it.isSuccessful){
                nav.popBackStack()
            }else{
                showViews()
                dialog.setMessage(it.exception?.message).show()
            }
        }
    }

    private fun hideViews()=binding.apply {
        layout.hide()
        progressBar.show()
        backButton.hide()
    }

    private fun showViews()=binding.apply{
        layout.show()
        progressBar.hide()
        backButton.show()
    }


}