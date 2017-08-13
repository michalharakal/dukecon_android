package org.dukecon.android.app;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import org.dukecon.android.app.di.ApplicationComponent;
import org.dukecon.android.app.di.ApplicationModule;
import org.dukecon.android.app.di.DaggerApplicationComponent;

public class DukeconApplication extends Application {

    public static DukeconApplication get(Context context) {
        return (DukeconApplication) context.getApplicationContext();
    }

    private ApplicationComponent appComponent;


    protected void initAppComponent() {
        appComponent = DaggerApplicationComponent.builder().applicationModule(new
                ApplicationModule(this)).build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        JodaTimeAndroid.init(this);
    }
}