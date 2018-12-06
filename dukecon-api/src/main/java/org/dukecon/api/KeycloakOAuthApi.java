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

    @retrofit2.http.FormUrlEncoded
    @POST("token")
    Call<OAuthToken> refresh(
            @retrofit2.http.Field("code") String code,
            @retrofit2.http.Field("client_id") String clientId,
            @retrofit2.http.Field("grant_type") String grantType
    );


    @FormUrlEncoded
    @POST("token")
    Call<OAuthToken> refreshOAuthToken(
            @Field("client_id") String clientId,
            @Field("grant_type") String grantType,
            @Field("scope") String scope,
            @Field("refresh_token") String refreshToken
    );
}
