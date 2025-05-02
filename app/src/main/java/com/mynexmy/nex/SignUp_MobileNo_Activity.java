package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp_MobileNo_Activity extends AppCompatActivity {
    RelativeLayout rlgetotp1, rlcan;
    ImageView btn_back4;
    EditText etMobile;
    private static final String BASE_URL = "https://api.example.com/";
    private ApiService apiService;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_mobile_no);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        init();
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Prepare the request body



        rlgetotp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(etMobile.getText().toString().length()>=10){
                 getOTP();
//                  Intent i = new Intent(getApplicationContext(), SignUp_OtpSbmit_Activity.class);
//                 i.putExtra("mobile",etMobile.getText().toString().trim());
//                 startActivity(i);

             }else {
                 Toast.makeText(SignUp_MobileNo_Activity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
             }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlgetotp1.startAnimation(myAnim);
            }
        });
        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                btn_back4.startAnimation(myAnim);
            }
        });
        rlcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlcan.startAnimation(myAnim);
            }

        });
    }




    void init() {
        rlgetotp1 = findViewById(R.id.rlgetotp1);
        btn_back4 = findViewById(R.id.btn_back4);
        rlcan = findViewById(R.id.rlcan);
        etMobile = findViewById(R.id.etMobile);

    }


    private void getOTP() {
//        {
//
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.new_progresslogo);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            JSONObject jsonObject = new JSONObject();
            try
            {
                jsonObject.put("mobile",etMobile.getText().toString().trim());

                Log.e("CheckOtpQuant",""+etMobile.getText().toString().trim());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ApiData.get_otp, jsonObject,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDialog.dismiss();

                                if (response.getBoolean("status") == true) {
                                    Toast.makeText(SignUp_MobileNo_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences =  getSharedPreferences(
                                            "MySharedPref",
                                            MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.clear();
                                    myEdit.apply();

                                    Intent i = new Intent(getApplicationContext(), SignUp_OtpSbmit_Activity.class);
                                    i.putExtra("mobile",etMobile.getText().toString().trim());
                                    startActivity(i);

                                } else {
                                    Toast.makeText(SignUp_MobileNo_Activity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }





        }