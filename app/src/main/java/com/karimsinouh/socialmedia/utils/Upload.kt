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

    fun image(path:String,uri:Uri,listener:(Result<String>)->Unit){
        val fileName=System.currentTimeMillis().toString()+ Random.nextInt(1,999999)+".png"
        val ref=storage.child("$path/$fileName")
        val uploadTask=ref.putFile(uri)

        val urlTask=uploadTask.continueWithTask {
            if (!it.isSuccessful){
                throw it.exception!!
            }
            ref.downloadUrl
        }.addOnCompleteListener {
            listener(Result(it.isSuccessful,it.result.toString(),it.exception?.message))
        }
    }

}