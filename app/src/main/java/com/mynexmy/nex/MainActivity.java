package com.mynexmy.nex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.Activitys.Forget_Password_Activity;
import com.mynexmy.nex.MVVM_Models.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView mcvLogin, mcvSignUp;
    private EditText etUser, etPass;
    private TextView tvForget;
    private ImageView imgshow, imghide;
    private MainViewModel mainViewModel;
    private AlertDialog progressDialog;
    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        checkLoginStatus();

        mainViewModel.getLoginStatus().observe(this, loginStatus -> {
            dismissProgressDialog();
            if (loginStatus) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.getErrorMessage().observe(this, error -> {
            dismissProgressDialog();
            if (error != null) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        initViews();

        mcvSignUp.setOnClickListener(v -> {
            hideDefaultKeyboard();
            startActivity(new Intent(getApplicationContext(), SignUp_MobileNo_Activity.class));
        });

        mcvLogin.setOnClickListener(v -> {
            hideDefaultKeyboard();
            String email = etUser.getText().toString().trim();
            String password = etPass.getText().toString();

            if (mainViewModel.isValid(email, password)) {
                showProgressDialog();
                mainViewModel.getLogIn(email, password, MainActivity.this);
            }
        });

        tvForget.setOnClickListener(v -> {
            hideDefaultKeyboard();
            startActivity(new Intent(getApplicationContext(), Forget_Password_Activity.class));
        });

        imgshow.setOnClickListener(v -> {
            etPass.setTransformationMethod(null);  // Show password
            imgshow.setVisibility(View.INVISIBLE);
            imghide.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            imgshow.startAnimation(clickAnimation());  // Optional animation
        });

        imghide.setOnClickListener(v -> {
            etPass.setTransformationMethod(new PasswordTransformationMethod());  // Hide password
            imghide.setVisibility(View.INVISIBLE);
            imgshow.setVisibility(View.VISIBLE);
            hideDefaultKeyboard();
            imghide.startAnimation(clickAnimation());  // Optional animation
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();
    }

    private void initViews() {
        mcvLogin = findViewById(R.id.mcvLogin);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etpass);
        mcvSignUp = findViewById(R.id.mcvSignUp);
        tvForget = findViewById(R.id.tvForget);
        imgshow = findViewById(R.id.imgshow);
        imghide = findViewById(R.id.imghide);
    }

    private void hideDefaultKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void checkLoginStatus() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean loginStatus = sh.getBoolean("Login_Status", false);
        if (loginStatus) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        }
    }

    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.new_progresslogo); // Set your custom layout for loading
        builder.setCancelable(false); // Prevent dismissing the dialog
        progressDialog = builder.create();
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.1F); // Change "0.4F" as per your recruitment.
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setMessage("We need location access to show your location.")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            // Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}
