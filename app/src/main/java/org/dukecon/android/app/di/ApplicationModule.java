package org.dukecon.android.app.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.dukecon.android.app.configuration.ConfigurationsProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final Application app;

    private final SharedPreferences mSharedPrefernces;

    public ApplicationModule(Application app) {
        this.app = app;
        mSharedPrefernces = app.getSharedPreferences("app", 0);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return mSharedPrefernces;
    }

    @Provides
    @Singleton
    ConfigurationsProvider provideEndpoitUrlProvider() {
        return new ConfigurationsProvider() {
            @Override
            public String getUrl() {
                return "https://jfs.dukecon.org/2017/rest/";
            }

            @Override
            public String getConferenceId() {
                return "jfs2017";
            }
        };
    }
}
