package org.dukecon.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.dukecon.android.api.DefaultApi;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.events.EventsListAdapter;
import org.dukecon.android.features.sessions.data.ConferenceRepository;

import java.util.List;

import dagger.Lazy;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ConferenceRepository conferenceRepository;
    private RecyclerView list;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (RecyclerView) findViewById(R.id.control_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);


        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();


        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("https://jfs.dukecon.org/2017/rest/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final DefaultApi defaultApi = restAdapter.create(DefaultApi.class);


        conferenceRepository = new ConferenceRepository(new Lazy<DefaultApi>() {
            @Override
            public DefaultApi get() {
                return defaultApi;
            }
        });
        list.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onResume() {
        super.onResume();

        conferenceRepository.getEvents(new ConferenceRepository.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<Event> events) {
                //toolbar.setTitle(co.getName());
                setList(events);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void setList(List<Event> events) {
        EventsListAdapter adapter = new EventsListAdapter(getApplicationContext(),
                events,
                new EventsListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Event item) {

                    }
                });

        list.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer_layout.closeDrawer(GravityCompat.START);
        return false;
    }
}
