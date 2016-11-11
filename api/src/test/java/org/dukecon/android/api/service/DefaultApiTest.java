package org.dukecon.android.api.service;

import org.dukecon.android.api.ApiClient;
import org.dukecon.android.api.model.Conference;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DefaultApi
 */
public class DefaultApiTest {

    private DefaultApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(DefaultApi.class);
    }

    
    /**
     * returns list of conferences
     *
     * 
     */
    @Test
    public void getConferencesTest() {
        // List<Conference> response = api.getConferences();

        // TODO: test validations
    }
    
}
