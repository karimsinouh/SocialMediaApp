package com.karimsinouh.socialmedia.utils

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.karimsinouh.socialmedia.data.Result
import kotlin.random.Random

object Upload {

    private val storage by lazy {
        Firebase.storage.reference
    }


    fun images(path:String,uriList:List<String>,listener:(Result<List<String>>)->Unit){

        val urls= mutableListOf<String>()

        for(uri in uriList){
            image(path,Uri.parse(uri)){
                if (it.isSuccessful) {
                    urls.add(it.data!!)

                    if (uri==uriList.last())
                        listener(Result(true,urls))
                }
                else {
                    listener(Result(false, null, "Something went wrong while uploading"))
                    return@image
                }

            }
        }
    }

    fun image(path:String,uri:Uri,name:String?=null,listener:(Result<String>)->Unit){
        val fileName=name ?: System.currentTimeMillis().toString()+ Random.nextInt(1,999999)+".png"
        val ref=storage.child("$path/$fileName")
        val uploadTask=ref.putFile(uri)

        val urlTask=uploadTask.continueWithTask {
            if (!it.isSuccessful){
                listener(Result(false,null,it.exception?.message))
                throw it.exception!!
            }
            ref.downloadUrl
        }.addOnCompleteListener {
            listener(Result(it.isSuccessful,it.result.toString(),it.exception?.message))
        }
    }

}