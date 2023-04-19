package com.example.recruitmentmanager.Data;

public class ApiUtils {
    private ApiUtils() {}
    public static final String BASE_URL = "http://172.20.10.4:8010/api/v1/";
    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
