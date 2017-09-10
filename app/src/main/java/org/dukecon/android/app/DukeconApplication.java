package org.dukecon.android.app;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import org.dukecon.android.app.di.ApplicationComponent;
import org.dukecon.android.app.di.ApplicationModule;
import org.dukecon.android.app.di.DaggerApplicationComponent;
import org.dukecon.android.features.main.di.MainComponent;
import org.dukecon.android.features.main.di.MainModule;
import org.dukecon.android.features.sessions.SessionNavigator;

public class DukeconApplication extends Application {


    public static DukeconApplication get(Context context) {
        return (DukeconApplication) context.getApplicationContext();
    }

    private ApplicationComponent appComponent;

    private MainComponent mainComponent;


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

    public MainComponent createMainComponent(SessionNavigator sessionNavigator) {
        mainComponent = appComponent.plus(new MainModule(sessionNavigator));
        return mainComponent;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }


    public void releaseMainComponent() {
        mainComponent = null;
    }
}