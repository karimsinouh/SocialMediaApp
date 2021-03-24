package com.karimsinouh.socialmedia.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.data.Post
import com.karimsinouh.socialmedia.repositories.PostRepo
import com.karimsinouh.socialmedia.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class UploadPostService:Service() {

    override fun onBind(intent: Intent?): IBinder? =null

    companion object{
        const val ACTION_TEXT="text"
        const val ACTION_IMAGE="image"
        const val ACTION_VIDEO="video"

        const val KEY_POST="text"
        const val KEY_VIDEO="video_uri"
        const val KEY_IMAGES="images_list"

        const val NOTIFICATION_CHANNEL_ID="post_service_id"
        const val ID="uploadPostService"
    }

    @Inject lateinit var manager:NotificationManager
    @Inject @Named(ID) lateinit var notification: NotificationCompat.Builder
    @Inject lateinit var repo:PostRepo

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val videoUri=intent?.getStringExtra(KEY_VIDEO)?:""
        val imagesUris=intent?.getStringArrayListExtra(KEY_IMAGES)?: emptyList<String>()

        val post=intent?.getSerializableExtra(KEY_POST) as Post

        when(intent.action){
            ACTION_TEXT->
                repo.uploadPost(post){
                    if (!it.isSuccessful)
                        handleFailure(it.message!!)
                    else
                        handleSuccess(it.data!!)
                }


            ACTION_IMAGE->
                repo.uploadPostWithImages(post, imagesUris){
                    if (!it.isSuccessful)
                        handleFailure(it.message!!)
                    else
                        handleSuccess(it.data!!)
                }

            ACTION_VIDEO->{

            }
        }


        startForeground(1,notification.build())

        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleFailure(message:String){
        notification.setContentTitle("Uploading failed")
                .setSubText(message)
                .setSmallIcon(R.drawable.ic_error)
        manager.notify(2,notification.build())
        stopSelf()
    }

    private fun handleSuccess(postId:String){
        val intent=Intent(this,MainActivity::class.java).apply {
            action=MainActivity.ACTION_VIEW_POST
            putExtra(MainActivity.KEY_POST,postId)
        }

        val pendingIntent=PendingIntent.getActivity(this,0,intent,FLAG_UPDATE_CURRENT)

        notification.setContentTitle("Your post has been successfully uploaded.")
                .setSubText("click here to see your post")
                .setSmallIcon(R.drawable.ic_cloud_done)
                .setContentIntent(pendingIntent)
        manager.notify(3,notification.build())
        stopSelf()
    }

}