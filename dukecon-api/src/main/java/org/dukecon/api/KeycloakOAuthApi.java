package org.dukecon.api;

import org.dukecon.android.api.model.OAuthToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KeycloakOAuthApi {
    @FormUrlEncoded
    @POST("token")
    Call<OAuthToken> getOpenIdToken(
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType,
            @Field("redirect_uri") String redirect_uri,
            @Field("code") String code
    );
}
