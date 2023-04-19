package com.example.recruitmentmanager.Data;

import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.Model.RecruitmentInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    @FormUrlEncoded
    Call<AuthLoginResponse> SignInData(@Field("email") String email,
                                       @Field("password") String password);

    @GET("recruitment-news/{id}")
    Call<RecruitmentInfo> getRecruitmentNewDetail (@Path("id") int id);

    @GET("recruitment-news")
    Call<List<RecruitmentInfo>> getRecruitmentNewsList();
}
