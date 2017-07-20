package org.dukecon.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.conference.ConferenceRepository;
import org.dukecon.android.events.EventsListAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ConferenceRepository conferenceRepository;
    private RecyclerView list;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        conferenceRepository = new ConferenceRepository();
        renderView();
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        conferenceRepository.getEvents(new ConferenceRepository.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<Event> events) {
                setList(events);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    public void renderView() {
        list = (RecyclerView) findViewById(R.id.control_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
