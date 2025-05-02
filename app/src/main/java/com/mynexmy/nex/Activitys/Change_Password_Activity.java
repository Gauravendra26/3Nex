package com.mynexmy.nex.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Change_Password_Activity extends AppCompatActivity {
    RelativeLayout rlChngPassword;
    ImageView btn_back1;
    String Mobile,oldP,p1,p2;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText etpass1,etpass2,etOldP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Utils.blackIconStatusBar(Change_Password_Activity.this, R.color.white);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        init();


        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPre", MODE_PRIVATE);
        Mobile=sharedPreferences.getString("CusMobile", "");

         rlChngPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()){
                    changepassword();
                }
            }
        });
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }


    private void changepassword() {
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.new_progresslogo);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile", Mobile);
                jsonObject.put("password", etpass1.getText().toString().trim());


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ApiData.Resetpassword, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                if (response.getBoolean("status") == true) {
                                    Toast.makeText(Change_Password_Activity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Change_Password_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);

        }

    }
    private void init() {

        rlChngPassword = findViewById(R.id.rlChngPassword);
        btn_back1 = findViewById(R.id.btn_back1);
        etpass2 = findViewById(R.id.etpass2);
        etpass1 = findViewById(R.id.etpass1);
        etOldP = findViewById(R.id.etOldP);
    }

    boolean isValid() {



        p1 = etpass1.getText().toString();
        p2 = etpass2.getText().toString();
        oldP= etOldP.getText().toString();

        if (oldP.isEmpty()){
            etOldP.setError("Enter Old Password");
            return false;
        }
        if (p1.equals(oldP)) {
            etpass1.setError("New Password Match With Old");
            return false;
        }


        if (p1.isEmpty()) {
            etpass1.setError("Enter Password");
            return false;
        } else if (p1.length() <6) {
            etpass1.setError("Enter Full Password");
            return false;
        }

        if (!p2.equals(p1)) {
            etpass2.setError("Password Not Match");
            return false;
        }
        return true;

    }
}