package com.karimsinouh.socialmedia.ui.writePost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.adapters.ImagesAdapter
import com.karimsinouh.socialmedia.databinding.FragmentWritePostBinding
import com.karimsinouh.socialmedia.utils.hide
import com.karimsinouh.socialmedia.utils.show
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WritePostFragment: Fragment(R.layout.fragment_write_post) {

    private lateinit var binding:FragmentWritePostBinding
    private lateinit var nav:NavController

    private val vm by viewModels<WritePostViewModel>()
    @Inject lateinit var imagesAdapter:ImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentWritePostBinding.bind(view)
        nav=findNavController()

        onClickListeners()
        setupRcv()

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        vm.images.observe(viewLifecycleOwner){
            if (it.isNotEmpty())
                binding.imagesRcv.show()
            else
                binding.imagesRcv.hide()

            imagesAdapter.submitList(it)
        }
    }

    private fun onClickListeners() {

        binding.toolbar.setNavigationOnClickListener {
            nav.popBackStack()
        }

        binding.addPicture.setOnClickListener {
            CropImage.activity().start(requireActivity(),this)
        }
    }


    private fun setupRcv()=binding.imagesRcv.apply{
        layoutManager=LinearLayoutManager(requireContext()).apply {
            orientation=RecyclerView.HORIZONTAL
        }
        setHasFixedSize(true)
        adapter=imagesAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            if (data!=null){
                val uri=CropImage.getActivityResult(data).uri
                vm.addImage(uri.toString())
            }else{
                Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }

    }


}