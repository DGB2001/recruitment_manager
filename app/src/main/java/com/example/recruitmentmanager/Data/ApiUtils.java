package com.example.recruitmentmanager.Data;

public class ApiUtils {
    private ApiUtils() {}
    public static final String BASE_URL = "http://192.168.1.3:8010/api/v1/";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
