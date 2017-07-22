# ConferencesApi

All URIs are relative to *http://dukecon.org/rest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConference**](ConferencesApi.md#getConference) | **GET** conferences/{id} | returns full conference data
[**getEvents**](ConferencesApi.md#getEvents) | **GET** conferences/{id}/events | returns list of conference events
[**getMeta**](ConferencesApi.md#getMeta) | **GET** conferences/{id}/metadata | returns list of conference meta data
[**getSpeakers**](ConferencesApi.md#getSpeakers) | **GET** conferences/{id}/speakers | returns list of conference speakers


<a name="getConference"></a>
# **getConference**
> Conference getConference(id)

returns full conference data



### Example
```java
// Import classes:
//import org.dukecon.android.api.ApiException;
//import org.dukecon.android.api.service.ConferencesApi;


ConferencesApi apiInstance = new ConferencesApi();
String id = "id_example"; // String | 
try {
    Conference result = apiInstance.getConference(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConferencesApi#getConference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Conference**](Conference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getEvents"></a>
# **getEvents**
> List&lt;Event&gt; getEvents(id)

returns list of conference events



### Example
```java
// Import classes:
//import org.dukecon.android.api.ApiException;
//import org.dukecon.android.api.service.ConferencesApi;


ConferencesApi apiInstance = new ConferencesApi();
String id = "id_example"; // String | 
try {
    List<Event> result = apiInstance.getEvents(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConferencesApi#getEvents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**List&lt;Event&gt;**](Event.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMeta"></a>
# **getMeta**
> MetaData getMeta(id)

returns list of conference meta data



### Example
```java
// Import classes:
//import org.dukecon.android.api.ApiException;
//import org.dukecon.android.api.service.ConferencesApi;


ConferencesApi apiInstance = new ConferencesApi();
String id = "id_example"; // String | 
try {
    MetaData result = apiInstance.getMeta(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConferencesApi#getMeta");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**MetaData**](MetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpeakers"></a>
# **getSpeakers**
> List&lt;Speaker&gt; getSpeakers(id)

returns list of conference speakers



### Example
```java
// Import classes:
//import org.dukecon.android.api.ApiException;
//import org.dukecon.android.api.service.ConferencesApi;


ConferencesApi apiInstance = new ConferencesApi();
String id = "id_example"; // String | 
try {
    List<Speaker> result = apiInstance.getSpeakers(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConferencesApi#getSpeakers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**List&lt;Speaker&gt;**](Speaker.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

