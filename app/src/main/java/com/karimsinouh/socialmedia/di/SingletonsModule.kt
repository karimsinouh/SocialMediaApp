package com.karimsinouh.socialmedia.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SingletonsModule {

    @Provides
    @Singleton
    fun glide(@ApplicationContext c:Context)=Glide.with(c).applyDefaultRequestOptions(RequestOptions())
    

}