package com.example.recruitmentmanager.Data;

public class ApiUtils {
    private ApiUtils() {}

      public static final String BASE_URL = "http://192.168.0.106:8000/api/v1/"; //Dương
//    public static final String BASE_URL = "http://192.168.1.12:8000/api/v1/"; //Bảo
//    public static final String BASE_URL = "http://172.19.201.113:8000/api/v1/"; //Sơn

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
