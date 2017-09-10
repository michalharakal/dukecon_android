package org.dukecon.android.features.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.dukecon.android.R;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.app.DukeconApplication;
import org.dukecon.android.features.sessions.SessionNavigator;
import org.dukecon.android.features.sessions.SessionsDateView;


public class MainActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener, SessionNavigator {

    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private ViewGroup content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (FrameLayout) findViewById(R.id.content);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showView(R.id.action_schedule);

    }

    private void inject() {
        DukeconApplication.get(this).createMainComponent(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (showView(item.getItemId())) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean showView(int viewId) {
        View view = null;
        switch (viewId) {
            case R.id.action_schedule:
                view = new SessionsDateView(this);
                break;
            case R.id.action_speakers:
                // SpeakerListView(this)
                break;
            case R.id.action_info:
                // InfoView(this);
                break;
            default:
                view = null;

        }
        if (view != null) {
            content.removeAllViews();
            content.addView(view);
            return true;
        }
        return false;
    }

    @Override
    public void showSession(Event event) {

    }
}
