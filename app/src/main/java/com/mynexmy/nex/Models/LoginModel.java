package com.mynexmy.nex.Models;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mynexmy.nex.ApiData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginModel {

    private final RequestQueue requestQueue;

    public LoginModel(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void loginUser(String email, String password, final LoginListener listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                ApiData.Login, jsonObject,
                response -> {
                    try {
                        if (response.getBoolean("status")) {
                            String token = response.getString("token");
                            listener.onSuccess(token);
                        } else {
                            listener.onFailure("Invalid credentials.");
                        }
                    } catch (JSONException e) {
                        listener.onFailure("Error processing response.");
                    }
                },
                error -> listener.onFailure("Network error.")
        );

        requestQueue.add(request);
    }

    public interface LoginListener {
        void onSuccess(String token);

        void onFailure(String error);
    }
}
