package com.example.recruitmentmanager.Data;

import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.Model.EmployerInfo;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    @FormUrlEncoded
    Call<AuthLoginResponse> SignInData(@Field("email") String email,
                                       @Field("password") String password);

    @GET("recruitment-news")
    Call<List<RecruitmentInfo>> getRecruitmentNewsList(@Query("order_by") String order_by);

    @GET("recruitment-news/{id}")
    Call<RecruitmentInfo> getRecruitmentNewDetail (@Path("id") int id);

    @POST("applications")
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
    
    @PUT("candidates/{id}")
    Call<User> updateCandidateDetail (@Path("id") int id, @Body User user);

    @DELETE("candidates/{id}")
    Call<Void> deleteCandidate (@Path("id") int id);

    @GET("candidates/{candidateId}/applications")
    Call<List<ApplicationInfo>> getHistoryApplication (@Path("candidateId") int candidate_id);
    
    @GET("recruitment-news/")
    Call<List<RecruitmentInfo>> getRecruitmentEmployerList(@Query("employer_id") int employer_id, @Query("order_by") String order_by);

    @POST("candidates")
    @FormUrlEncoded
    Call<ApplicationResponse> createcandidate(@Field("email") String email,
                                                    @Field("password") String password,
                                                    @Field("role") int role,
                                                    @Field("name") String name,
                                                    @Field("gender") int gender,
                                                    @Field("phone_number") String phone,
                                                    @Field("address") String address);
    @POST("employers")
    @FormUrlEncoded
    Call<ApplicationResponse> createemployer(@Field("email") String email,
                                              @Field("password") String password,
                                              @Field("role") int role,
                                              @Field("company_name") String companyName,
                                              @Field("phone_number") String phone,
                                              @Field("address") String address);
                                              
    @GET("employers/{id}")
    Call<User> getEmployerDetail (@Path("id") int id);

    @PUT("employers/{id}")
    Call<User> updateEmployerDetail (@Path("id") int id, @Body User user);

    @DELETE("employers/{id}")
    Call<Void> deleteEmployer (@Path("id") int id);
}