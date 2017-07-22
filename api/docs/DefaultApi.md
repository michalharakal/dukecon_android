# DefaultApi

All URIs are relative to *http://dukecon.org/rest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConferences**](DefaultApi.md#getConferences) | **GET** conferences | returns list of conferences


<a name="getConferences"></a>
# **getConferences**
> List&lt;Conference&gt; getConferences()

returns list of conferences



### Example
```java
// Import classes:
//import org.dukecon.android.api.ApiException;
//import org.dukecon.android.api.service.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
try {
    List<Conference> result = apiInstance.getConferences();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getConferences");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Conference&gt;**](Conference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

