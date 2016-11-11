package org.dukecon.android.api.service;

import org.dukecon.android.api.CollectionFormats.*;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;

import org.dukecon.android.api.model.Conference;
import org.dukecon.android.api.model.Event;
import org.dukecon.android.api.model.MetaData;
import org.dukecon.android.api.model.Speaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConferencesApi {
  /**
   * returns full conference data
   * 
   * @param id  (required)
   * @return Call&lt;Conference&gt;
   */
  
  @GET("conferences/{id}")
  Call<Conference> getConference(
    @Path("id") String id
  );

  /**
   * returns list of conference events
   * 
   * @param id  (required)
   * @return Call&lt;List<Event>&gt;
   */
  
  @GET("conferences/{id}/events")
  Call<List<Event>> getEvents(
    @Path("id") String id
  );

  /**
   * returns list of conference meta data
   * 
   * @param id  (required)
   * @return Call&lt;MetaData&gt;
   */
  
  @GET("conferences/{id}/metadata")
  Call<MetaData> getMeta(
    @Path("id") String id
  );

  /**
   * returns list of conference speakers
   * 
   * @param id  (required)
   * @return Call&lt;List<Speaker>&gt;
   */
  
  @GET("conferences/{id}/speakers")
  Call<List<Speaker>> getSpeakers(
    @Path("id") String id
  );

}
