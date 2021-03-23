package com.karimsinouh.socialmedia.ui.writePost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.databinding.FragmentWritePostBinding
import com.theartofdev.edmodo.cropper.CropImage

class WritePostFragment: Fragment(R.layout.fragment_write_post) {


    private lateinit var binding:FragmentWritePostBinding
    private lateinit var nav:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentWritePostBinding.bind(view)
        nav=findNavController()


        onClickListeners()


    }

    private fun onClickListeners() {

        binding.toolbar.setNavigationOnClickListener {
            nav.popBackStack()
        }

        binding.addPicture.setOnClickListener {
            CropImage.activity().start(requireActivity(),this)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            if (data!=null){
                val uri=CropImage.getActivityResult(data).uri
            }else{
                Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

    }


}