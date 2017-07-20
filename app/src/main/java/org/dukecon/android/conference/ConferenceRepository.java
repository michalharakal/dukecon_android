package org.dukecon.android.conference;

import android.util.Log;

import org.dukecon.android.MainActivity;
import org.dukecon.android.api.model.Conference;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.api.service.ConferencesApi;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Arrays.asList;

/**
 * @author Falk Sippach, falk@jug-da.de, @sippsack
 */
public class ConferenceRepository implements Callback<Conference> {

    private Conference conference;

    public ConferenceRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://latest.dukecon.org/javaland/2017/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        ConferencesApi conferencesApi = retrofit.create(ConferencesApi.class);

        Call<Conference> call = conferencesApi.getConference("jl2017");
        //asynchronous call
        call.enqueue(this);
    }

    public List<Event> getEventsForDay(int day) {
        return this.conference != null ? this.conference.getEvents() : Arrays.<Event>asList();
    }

    @Override
    public void onResponse(Call<Conference> call, Response<Conference> response) {
        Log.d(ConferenceRepository.class.getName(), "Loaded conference data from " + response.raw().request().url());
        this.conference = response.body();
    }

    @Override
    public void onFailure(Call<Conference> call, Throwable t) {
        Log.d(ConferenceRepository.class.getName(), "Failure while loading conference data " + t.getMessage());
    }
}
