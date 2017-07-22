package org.dukecon.android.conference;

import android.support.annotation.NonNull;
import android.util.Log;

import org.dukecon.android.api.ConferencesApi;
import org.dukecon.android.api.model.Conference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Falk Sippach, falk@jug-da.de, @sippsack
 */
public class ConferenceRepository {


    public interface LoadConferenceCallback {
        void onConferenceLoaded(Conference conference);
    }

    private Conference conference = null;

    private void getDataFromNetwork(@NonNull final LoadConferenceCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jfs.dukecon.org/2017/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        ConferencesApi conferencesApi = retrofit.create(ConferencesApi.class);

        Call<Conference> call = conferencesApi.getConference("jfs2017");
        //asynchronous call
        call.enqueue(new Callback<Conference>() {
            @Override
            public void onResponse(Call<Conference> call, Response<Conference> response) {
                Log.d(ConferenceRepository.class.getName(), "Loaded conference data from " + response.raw().request().url());
                ConferenceRepository.this.conference = response.body();
                callback.onConferenceLoaded(ConferenceRepository.this.conference);
            }

            @Override
            public void onFailure(Call<Conference> call, Throwable t) {
                Log.d(ConferenceRepository.class.getName(), "Failure while loading conference data " + t.getMessage());

            }
        });
    }

    public void getConference(@NonNull final LoadConferenceCallback callback) {
        // Respond immediately with cache if available and not dirty
        if (conference != null) {
            callback.onConferenceLoaded(conference);
            return;
        }

        getDataFromNetwork(callback);
    }

}
