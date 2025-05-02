package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mynexmy.nex.Activitys.Order_Summary_Activity;
import com.mynexmy.nex.Activitys.ScratchCart_Activity;
import com.mynexmy.nex.Activitys.Scratch_Delivery_Activity;
import com.mynexmy.nex.Activitys.SelectAddressPage;
import com.mynexmy.nex.Adapters.adapterStateSnipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Signup extends AppCompatActivity {
    EditText etEmail, etPass2, etState;
    ImageView imghide1, imgshow1, btn_back4;
    ProgressDialog progressDialog;
    EditText etFirst, etLast, etPin;
    String email, name, fullName, lname, state, p1, pin;
    int scratchIndicator, scratch_card_payment_id, mycartIndicator, scratchcartIndicator,
            scratch_card_price, scratch_card_id;
    RelativeLayout rlSignup1,rlState;
    RecyclerView rvState;
    LinearLayout llMAin;
    String Token2, address_phone,stateNameFinal;
    TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Intent intent = getIntent();
        Token2 = intent.getStringExtra("Token");
        scratchIndicator = intent.getIntExtra("scratchIndicator", 0);
        scratch_card_payment_id = intent.getIntExtra("scratch_card_payment_id", 0);
        mycartIndicator = intent.getIntExtra("mycartIndicator", 0);
        scratchcartIndicator = intent.getIntExtra("scratchcartIndicator", 0);
        address_phone = intent.getStringExtra("address_phone");
        scratch_card_id = intent.getIntExtra("scratch_card_id", 0);
        scratch_card_price = intent.getIntExtra("scratch_card_price", 0);

        init();
        rvState.setLayoutManager(new LinearLayoutManager(this)); // Set your LayoutManager (Vertical Scroll)

        List<String> indiaStatesAndUTs = Arrays.asList(
                "Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam",
                "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli and Daman and Diu",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand",
                "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra",
                "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand",
                "Uttar Pradesh", "West Bengal"
        );

        adapterStateSnipper adapter = new adapterStateSnipper(this, indiaStatesAndUTs);
        adapter.set(new adapterStateSnipper.ProductClick() {
            @Override
            public void productClickHomeNew(int position, String stateName) {
                tvState.setVisibility(View.VISIBLE);
                rvState.setVisibility(View.GONE);
                tvState.setText(stateName);
                stateNameFinal=stateName;
                rlState.setBackgroundResource(R.drawable.shape_text);  // Assuming shape_manage.xml is the shape you want to apply
                tvState.setTextColor(Color.BLACK);
//                Toast.makeText(getApplicationContext(), "Clicked on " + stateName, Toast.LENGTH_SHORT).show();
            }
        });
        rvState.setAdapter(adapter);

        llMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvState.setVisibility(View.VISIBLE);
                rvState.setVisibility(View.GONE);
                rlState.setBackgroundResource(R.drawable.shape_text);  // Assuming shape_manage.xml is the shape you want to apply
                  }
        });


        Log.d("DataOfProfile", "Token: " + Token2 +
                ", scratchIndicator: " + scratchIndicator +
                ", scratch_card_payment_id: " + scratch_card_payment_id +
                ", mycartIndicator: " + mycartIndicator +
                ", scratchcartIndicator: " + scratchcartIndicator +
                ", address_phone: " + address_phone +
                ", scratch_card_id: " + scratch_card_id +

                ", scratch_card_price: " + scratch_card_price);
        if (scratchIndicator == 1 || scratchIndicator == 2) {
            // Hide the back button image
            btn_back4.setVisibility(View.GONE);

        }

        // Handle visibility and interactions of password show/hide icons
        imgshow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgshow1.setVisibility(View.INVISIBLE);
                imghide1.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();
                etPass2.setTransformationMethod(null);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                imgshow1.startAnimation(myAnim);
            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide tvState and show rvState
                tvState.setVisibility(View.GONE);
                rvState.setVisibility(View.VISIBLE);

                rlState.setBackgroundResource(R.drawable.shape_manage);  // Assuming shape_manage.xml is the shape you want to apply
            }
        });


        imghide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass2.setTransformationMethod(new PasswordTransformationMethod());
                imghide1.setVisibility(View.INVISIBLE);
                imgshow1.setVisibility(View.VISIBLE);
                hideDefaultKeyboard();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                imghide1.startAnimation(myAnim);
            }
        });

        rlSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDefaultKeyboard();
                if (isValid()) {
                    getRegisterData();
//                    Toast.makeText(Signup.this, "OK", Toast.LENGTH_SHORT).show();
                }
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                rlSignup1.startAnimation(myAnim);
            }
        });

        btn_back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                hideDefaultKeyboard();
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                btn_back4.startAnimation(myAnim);
            }
        });
    }

    void init() {
        etEmail = findViewById(R.id.etEmail);
        etPass2 = findViewById(R.id.etPass2);
        tvState = findViewById(R.id.tvState);
        rvState = findViewById(R.id.rvState);
        llMAin = findViewById(R.id.llMAin);

        imghide1 = findViewById(R.id.imghide1);
        imgshow1 = findViewById(R.id.imgshow1);

        rlState = findViewById(R.id.rlState);
        rlSignup1 = findViewById(R.id.rlSignup1);
        btn_back4 = findViewById(R.id.btn_back4);
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etPin = findViewById(R.id.etPin);
    }
    private void setDropdownHeight(Spinner spinner, int height) {
        try {
            // Accessing the Spinner's mPopup field using reflection
            Field popupField = Spinner.class.getDeclaredField("mPopup");
            popupField.setAccessible(true);
            Object popupObject = popupField.get(spinner);

            // If the PopupWindow is found, set its height
            if (popupObject instanceof PopupWindow) {
                PopupWindow popupWindow = (PopupWindow) popupObject;
                popupWindow.setHeight(height); // Set the height of the dropdown
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void hideDefaultKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    void getRegisterData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fname", name);
            jsonObject.put("lanme", lname);
            jsonObject.put("pincode", pin);
            jsonObject.put("email", email);
            jsonObject.put("state", stateNameFinal);
            jsonObject.put("password", p1);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                ApiData.Register,
                jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (response.getBoolean("status")) {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        if (scratchIndicator == 1) {
                            // If scratchIndicator is 1, go to Home page
                            Intent homeIntent = new Intent(getApplicationContext(), Scratch_Delivery_Activity.class);
                            homeIntent.putExtra("scratchIndicator", scratchIndicator);
                            startActivity(homeIntent);
                            finish();
                        } else if (scratchIndicator == 2) {
                            // If scratchIndicator is 2, go to SelectAddressPage
                            Intent i = new Intent(getApplicationContext(), SelectAddressPage.class);
                            i.putExtra("scratch_card_payment_id", scratch_card_payment_id);
                            i.putExtra("scratchIndicator", scratchIndicator);
                            startActivity(i);
                            finish();
                        } else if (mycartIndicator == 11) {
                            // If scratchIndicator is 2, go to SelectAddressPage
                            Intent i = new Intent(getApplicationContext(), Order_Summary_Activity.class);
                            startActivity(i);
                            finish();
                        } else if (scratchcartIndicator == 21) {
                            // If scratchIndicator is 2, go to SelectAddressPage
                            Intent i = new Intent(getApplicationContext(), ScratchCart_Activity.class);
                            i.putExtra("scratch_card_id", scratch_card_id);
                            i.putExtra("scratch_card_price", scratch_card_price);
                            i.putExtra("email", email);
                            i.putExtra("address_phone", address_phone);
                            i.putExtra("name", fullName);


                            Log.d("IntentData", "scratch_card_id: " + scratch_card_id +
                                    ", scratch_card_price: " + scratch_card_price +
                                    ", email: " + email +
                                    ", address_phone: " + address_phone +
                                    ", name: " + fullName);

                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }


    boolean isValid() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";

        email = etEmail.getText().toString().trim();
        name = etFirst.getText().toString().trim();
        lname = etLast.getText().toString().trim();
//        state = etState.getText().toString().trim();
        pin = etPin.getText().toString().trim();
        p1 = etPass2.getText().toString();
        fullName = name + " " + lname;

        if (name.isEmpty()) {
            etFirst.setError("Please Enter First Name");
            return false;
        }
        if (lname.isEmpty()) {
            etLast.setError("Please Enter Last Name");
            return false;
        }
        if (stateNameFinal == null || stateNameFinal.equals("Enter State")) {
            Toast.makeText(this, "Please Choose State", Toast.LENGTH_SHORT).show();
            return false;
        }


//        if (state.isEmpty()) {
//            etState.setError("Please Enter State");
//            return false;
//        }

        if (lname.isEmpty()) {
            etLast.setError("Please Enter Last Name");
            return false;
        }
        if (pin.isEmpty()) {
            etPin.setError("Please Enter Pincode");
            return false;
        } else if (pin.length() < 6) {
            etPin.setError("Enter Full Pincode");
            return false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Please Enter Email");
            return false;
        } else if (!email.matches(emailPattern)) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }
//
        if (p1.isEmpty()) {
            etPass2.setError("Enter Password");
            return false;
        } else if (p1.length() < 7) {
            etPass2.setError("Enter a password with more than 7 characters.");
            return false;
        }

        return true;
    }


    // Override the back press behavior based on scratchIndicator
    @Override
    public void onBackPressed() {
        if (scratchIndicator == 1 || scratchIndicator == 2) {
            // Do nothing, so back press is disabled
        } else {
            super.onBackPressed(); // Default back press behavior
        }
    }
}
