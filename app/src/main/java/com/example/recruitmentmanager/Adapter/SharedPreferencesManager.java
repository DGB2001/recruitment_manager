package com.example.recruitmentmanager.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.recruitmentmanager.Model.AuthLoginResponse;

public class SharedPreferencesManager {
    private static String SHARED_PREF_NAME = "usersignin";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void saveUser(AuthLoginResponse user) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("role", user.getRole());
        editor.putString("status", user.getStatus());
        editor.putInt("candidate_id", user.getCandidate_id());
        editor.putInt("employer_id", user.getEmployer_id());
        editor.putBoolean("signedIn", true);
        editor.apply();
    }

    public void rememberUserLogin(String emailLogin, String passwordLogin) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("email_login", emailLogin);
        editor.putString("password_login", passwordLogin);
        editor.apply();
    }

    public String[] getRememberUserLogin() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new String[]{sharedPreferences.getString("email_login", ""), sharedPreferences.getString("password_login", "")};
    }


    public boolean isSignedIn() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("signedIn", false);
    }

    public AuthLoginResponse getUserAuthLogin() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new AuthLoginResponse(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("role", null),
                sharedPreferences.getString("status", null),
                sharedPreferences.getInt("candidate_id", -1),
                sharedPreferences.getInt("employer_id", -1));
    }


    public void signOut() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("id");
        editor.remove("email");
        editor.remove("role");
        editor.remove("status");
        editor.remove("candidate_id");
        editor.remove("employer_id");
        editor.remove("signedIn");
        editor.commit();
       // editor.apply();
    }
}
