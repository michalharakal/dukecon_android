package org.dukecon.android.app.di;

import org.dukecon.android.features.main.di.MainComponent;
import org.dukecon.android.features.main.di.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetModule.class}
)
public interface ApplicationComponent {
    MainComponent plus(MainModule mainModule);
}