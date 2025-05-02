package com.mynexmy.nex.MVVM_Models;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.ApiData;

import org.json.JSONException;
import org.json.JSONObject;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();
    private final MutableLiveData<String> loginToken = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final RequestQueue requestQueue;

    public MainViewModel(Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(application);
    }

    public LiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public LiveData<String> getLoginToken() {
        return loginToken;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Method to validate email and password
    public boolean isValid(String email, String password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            errorMessage.setValue("Please enter Email");
            return false;
        } else if (!email.matches(emailPattern)) {
            errorMessage.setValue("Please enter Valid Email");
            return false;
        }
        if (password.isEmpty()) {
            errorMessage.setValue("Enter Password");
            return false;
        } else if (password.length() < 6) {
            errorMessage.setValue("Enter Full Password");
            return false;
        }
        return true;
    }

    // Method to make a login request
    public void getLogIn(String email, String password, Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            errorMessage.setValue("Error creating JSON");
        }

        String url = ApiData.Login;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            if (status) {
                                String token = response.getString("token");
                                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Login_Status", true);
                                editor.putString("Login_Token", token);
                                editor.apply();

                                loginToken.setValue(token);
                                loginStatus.setValue(true);
                            } else {
                                loginStatus.setValue(false);
                                errorMessage.setValue("Please Enter Correct Email and Password");
                            }
                        } catch (JSONException e) {
                            errorMessage.setValue("Error parsing response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorMessage.setValue("Login failed. Please try again.");
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}

