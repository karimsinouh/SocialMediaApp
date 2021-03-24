package com.karimsinouh.socialmedia.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.karimsinouh.socialmedia.databinding.ItemImageBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor(
    private val glide:RequestManager
):RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    inner class ImageHolder(private val binding:ItemImageBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(uri: String){
            glide.load(Uri.parse(uri)).into(binding.image)
            binding.removeButton.setOnClickListener {
                onRemove?.let {
                    it(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ImageHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val uri=differ.currentList[position]
        holder.bind(uri)
    }

    override fun getItemCount()=differ.currentList.size

    //diff utils
    private val diffCallback=object:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String)=oldItem==newItem

        override fun areContentsTheSame(oldItem: String, newItem: String)=oldItem==newItem
    }

    private val differ=AsyncListDiffer(this,diffCallback)

    fun submitList(it:List<String>){
        differ.submitList(it)
        notifyDataSetChanged()
    }


    //callbacks
    private var onRemove:( (Int)->Unit )?=null
    fun onRemoveListener(listener:(Int)->Unit){
        onRemove=listener
    }

}