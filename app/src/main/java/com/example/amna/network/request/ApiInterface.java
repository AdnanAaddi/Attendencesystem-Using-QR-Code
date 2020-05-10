package com.example.amna.network.request;

import com.example.amna.network.response.LoginResponse;
import com.example.amna.network.response.MessageResponse;
import com.example.amna.network.response.UserResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    String LOGIN = "api/login";
    String USER = "api/user";
    String QRCODE="api/qrcode";

    @Headers("Content-Type: application/json")
    @POST(LOGIN)
    Call<LoginResponse> signUpCall(@Query("email") String email, @Query("password") String password);

    @Headers("Content-Type: application/json")
    @GET(USER)
    Call<UserResponce> userCall(@Header("Authorization") String toke);

    @Headers("Content-Type: application/json")
    @POST(QRCODE)
    Call<MessageResponse> receiveMsg(@Header("Authorization") String token,@Query("course_code") String code);
}

