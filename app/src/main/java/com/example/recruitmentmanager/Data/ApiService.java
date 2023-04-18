package com.example.recruitmentmanager.Data;

import com.example.recruitmentmanager.Model.AuthLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<AuthLoginResponse> SignInData(@Field("email") String email,
                                       @Field("password") String password);
}
