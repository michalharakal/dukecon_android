package org.dukecon.android.conference;

import android.support.annotation.NonNull;
import android.util.Log;

import org.dukecon.android.api.model.Conference;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.api.service.ConferencesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Falk Sippach, falk@jug-da.de, @sippsack
 */
public class ConferenceRepository {

    public interface LoadEventsCallback {
        void onEventsLoaded(List<Event> events);
    }

    private Conference conference = null;

    private void getDataFromNetwork(@NonNull final LoadEventsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://latest.dukecon.org/javaland/2017/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        ConferencesApi conferencesApi = retrofit.create(ConferencesApi.class);

        Call<Conference> call = conferencesApi.getConference("jl2017");
        //asynchronous call
        call.enqueue(new Callback<Conference>() {
            @Override
            public void onResponse(Call<Conference> call, Response<Conference> response) {
                Log.d(ConferenceRepository.class.getName(), "Loaded conference data from " + response.raw().request().url());
                ConferenceRepository.this.conference = response.body();
                callback.onEventsLoaded(response.body().getEvents());
            }

            @Override
            public void onFailure(Call<Conference> call, Throwable t) {
                Log.d(ConferenceRepository.class.getName(), "Failure while loading conference data " + t.getMessage());

            }
        });
    }

    public void getEvents(@NonNull final LoadEventsCallback callback) {

        // Respond immediately with cache if available and not dirty
        if (conference != null) {
            callback.onEventsLoaded(conference.getEvents());
            return;
        }

        getDataFromNetwork(callback);
    }

}
