package org.dukecon.android.api.service;

import org.dukecon.android.api.ApiClient;
import org.dukecon.android.api.model.Conference;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.api.model.MetaData;
import org.dukecon.android.api.model.Speaker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ConferencesApi
 */
public class ConferencesApiTest {

    private ConferencesApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(ConferencesApi.class);
    }

    
    /**
     * returns full conference data
     *
     * 
     */
    @Test
    public void getConferenceTest() {
        String id = null;
        // Conference response = api.getConference(id);

        // TODO: test validations
    }
    
    /**
     * returns list of conference events
     *
     * 
     */
    @Test
    public void getEventsTest() {
        String id = null;
        // List<Event> response = api.getEvents(id);

        // TODO: test validations
    }
    
    /**
     * returns list of conference meta data
     *
     * 
     */
    @Test
    public void getMetaTest() {
        String id = null;
        // MetaData response = api.getMeta(id);

        // TODO: test validations
    }
    
    /**
     * returns list of conference speakers
     *
     * 
     */
    @Test
    public void getSpeakersTest() {
        String id = null;
        // List<Speaker> response = api.getSpeakers(id);

        // TODO: test validations
    }
    
}
