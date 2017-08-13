package org.dukecon.android.features.sessions.data;

import android.util.Log;

import org.dukecon.android.api.ConferencesApi;
import org.dukecon.android.api.model.Conference;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.app.configuration.ConfigurationsProvider;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Falk Sippach, falk@jug-da.de, @sippsack
 */
public class ConferenceRepository implements SessionsDataSource {

    private static final String LOG_TAG = ConferenceRepository.class.getName();
    
    private final Lazy<ConferencesApi> conferenceApiService;
    private final Lazy<ConfigurationsProvider> configurationsProvider;


    private Conference conference = null;


    @Inject
    public ConferenceRepository(Lazy<ConferencesApi> conferenceApiService, Lazy<ConfigurationsProvider> configurationsProvider) {
        this.conferenceApiService = conferenceApiService;
        this.configurationsProvider = configurationsProvider;
    }

    @Override
    public void refreshEvents() {
        // delete memory cache
        conference = null;
    }

    @Override
    public void getEvents(@Nonnull LoadEventsCallback callback) {
        // Respond immediately with cache if available and not dirty
        if (conference != null) {
            callback.onEventsLoaded(conference.getEvents());
            return;
        }

        getDataFromNetwork(callback);
    }

    @Override
    public void getEvent(@Nonnull String taskId, @Nonnull LoadEventCallback callback) {
        if (conference != null) {
            for (Event event : conference.getEvents()) {
                if (taskId.equals(event.getId())) {
                    callback.onEventLoaded(event);
                    return;
                }
            }
        }
        callback.onDataNotAvailable();
    }

    private void getDataFromNetwork(@Nonnull final LoadEventsCallback callback) {
        Call<Conference> call = conferenceApiService.get().getConference(configurationsProvider
                .get().getConferenceId());
        //asynchronous call
        call.enqueue(new Callback<Conference>() {
            @Override
            public void onResponse(Call<Conference> call, Response<Conference> response) {
                Log.d(ConferenceRepository.class.getName(), "Loaded conference data from " + response.raw().request().url());
                ConferenceRepository.this.conference = response.body();
                callback.onEventsLoaded(ConferenceRepository.this.conference.getEvents());
            }

            @Override
            public void onFailure(Call<Conference> call, Throwable t) {
                Log.d(LOG_TAG, "Failure while loading conference data " + t.getMessage());

            }
        });
    }
}
