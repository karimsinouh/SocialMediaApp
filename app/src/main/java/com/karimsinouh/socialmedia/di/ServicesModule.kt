package com.karimsinouh.socialmedia.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.karimsinouh.socialmedia.R
import com.karimsinouh.socialmedia.service.UploadPostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
object ServicesModule {

    @Provides
    fun notificationManager(@ApplicationContext c:Context)=c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Named(UploadPostService.ID)
    fun serviceNotification(@ApplicationContext c:Context)=
            NotificationCompat.Builder(c,UploadPostService.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(c.getString(R.string.uploading_post))
                    .setSmallIcon(R.drawable.ic_upload)

}