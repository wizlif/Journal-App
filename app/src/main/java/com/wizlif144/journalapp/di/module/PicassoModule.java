package com.wizlif144.journalapp.di.module;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {NetworkModule.class})
public class PicassoModule {
    @Provides
    @Singleton
    Picasso picasso(Application app,OkHttp3Downloader okHttpDownloader)
    {
        return new Picasso.Builder(app)
                .downloader(okHttpDownloader)
                .build();
    }

    @Provides
    @Singleton
    OkHttp3Downloader okHttpDownloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }
}
