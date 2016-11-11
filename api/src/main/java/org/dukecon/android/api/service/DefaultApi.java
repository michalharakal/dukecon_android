package org.dukecon.android.api.service;

import org.dukecon.android.api.CollectionFormats.*;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;

import org.dukecon.android.api.model.Conference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DefaultApi {
  /**
   * returns list of conferences
   * 
   * @return Call&lt;List<Conference>&gt;
   */
  
  @GET("conferences")
  Call<List<Conference>> getConferences();
    

}
