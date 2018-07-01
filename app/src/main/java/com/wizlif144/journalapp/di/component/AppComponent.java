/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.wizlif144.journalapp.di.component;

import android.app.Application;


import com.wizlif144.journalapp.JournalApp;
import com.wizlif144.journalapp.di.builder.ActivityBuilder;
import com.wizlif144.journalapp.di.module.AppModule;
import com.wizlif144.journalapp.di.module.NetworkModule;
import com.wizlif144.journalapp.di.module.PicassoModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        NetworkModule.class,
        PicassoModule.class
})
public interface AppComponent {

    void inject(JournalApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
