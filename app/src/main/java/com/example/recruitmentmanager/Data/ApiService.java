package com.example.recruitmentmanager.Data;

import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.Model.EmployerInfo;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.Model.User;

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

    @GET("recruitment-news")
    Call<List<RecruitmentInfo>> getRecruitmentNewsList();

    @GET("recruitment-news/{id}")
    Call<RecruitmentInfo> getRecruitmentNewDetail (@Path("id") int id);

    @POST("application")
    @FormUrlEncoded
    Call<ApplicationResponse> createApplicationData(@Field("candidate_id") int  candidate_id,
                                         @Field("master_technical_id") int master_technical_id,
                                         @Field("master_level_id") int master_level_id,
                                         @Field("recruitment_news_id") int recruitment_news_id,
                                         @Field("title") String title,
                                         @Field("content") String content);


    @GET("employers")
    Call<List<EmployerInfo>> getEmployerList();

    @GET("candidates/{id}")
    Call<User> getCandidateDetail (@Path("id") int id);

}