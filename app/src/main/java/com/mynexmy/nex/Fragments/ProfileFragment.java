package com.mynexmy.nex.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mynexmy.nex.Activitys.AboutUs_Activity;
import com.mynexmy.nex.Activitys.ContactUspage;
import com.mynexmy.nex.Activitys.FAQ_Activity;
import com.mynexmy.nex.Activitys.Scratch_Activity;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.FileUtils;
import com.mynexmy.nex.MainActivity;
import com.mynexmy.nex.Activitys.Orders_Activity;
import com.mynexmy.nex.Activitys.PrivacyPolicyPage;
import com.mynexmy.nex.MyAccount_Activity2;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Activitys.TermsOfUse_Page;
import com.mynexmy.nex.Signup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 7;
    private final int gallery_request_code = 1000;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    View view;
    private String filePath;
    private String selectedPicture = "";
    SharedPreferences sharedPreferences;

    public static final String PRODUCT_PHOTO = "photo";
    private Bitmap bitmap;
    private ImageView mImage;
    private Uri mImageUri;
    ImageView imgP, imgProfile;
    RequestQueue requestQueue;
    TextView tvMobile, tvFName, tvLName, tvEmail;
    ProgressDialog progressDialog;
    String email, first_name, last_name, mobile, pincode, created_at, updated_at, referral_code,
            city, country, state, postal_code;

    RelativeLayout rlscratch, rlDelete, rlmycart, rlMngacc, rlLogout, rl_returnPolicy,
            rlOrders, rlAboutus, rlprivacypolicy, rl_termsofuse, rl_ContactUs, rlFAQ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        init();

        getDataProfile();
        getPrefs();

//        Glide.with(getActivity()).load("https://randomuser.me/api/portraits/women/58.jpg")
//                .placeholder(R.drawable.baseline_person_24)
//                .into(imgP);
//        rlwish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new WishlistFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
//
//            }

//        });

        rlmycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MycartFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, fragment).addToBackStack("name").commit();
                rlmycart.startAnimation(clickAnimation());

            }

        });
        rlMngacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name == null || first_name.equals("null")) {
                    Intent homeIntent = new Intent(getActivity(), Signup.class);
                    startActivity(homeIntent);

                } else {
                    Intent i = new Intent(getActivity(), MyAccount_Activity2.class);
                    startActivity(i);
                }

                rlMngacc.startAnimation(clickAnimation());

            }
        });

        rlAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AboutUs_Activity.class);
                startActivity(i);
                rlAboutus.startAnimation(clickAnimation());

            }
        });
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                rlLogout.startAnimation(myAnim);
            }
        });
        rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCustomAlertDialog();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                rlDelete.startAnimation(myAnim);
            }
        });

        rlOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Orders_Activity.class);
                startActivity(intent);
                rlOrders.startAnimation(clickAnimation());

            }
        });

        rlprivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivacyPolicyPage.class);
                startActivity(intent);
                rlprivacypolicy.startAnimation(clickAnimation());

            }
        });

        rl_termsofuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TermsOfUse_Page.class);
                startActivity(intent);
                rl_termsofuse.startAnimation(clickAnimation());

            }
        });
        rl_ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUspage.class);
                startActivity(intent);
                rl_ContactUs.startAnimation(clickAnimation());

            }
        });
        rlFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FAQ_Activity.class);
                startActivity(intent);
                rlFAQ.startAnimation(clickAnimation());

            }
        });
        rlscratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
//                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
//                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
//                ((Home)getActivity()).chipNotify();
                Intent intent = new Intent(getActivity(), Scratch_Activity.class);
                startActivity(intent);
//  Intent intent = new Intent(getActivity(), Scratch1_Activity.class);
//                startActivity(intent);

                rlscratch.startAnimation(clickAnimation());

            }
        });
        imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAndroidVersion();

                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgP.startAnimation(myAnim);
                ImagePicker.with(ProfileFragment.this)
                        .cropSquare()            //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }

        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri picUri = data.getData();
//         filePath = UriUtils.getFilePathFromUri(getContext(), picUri);
        filePath = FileUtils.getPath(getContext(), picUri);

        if (filePath != null) {
            try {
                Log.d("file_Path", String.valueOf(filePath));
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                imgP.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                selectedPicture = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                String sharedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imagePreferance", sharedImage);
                editor.putString("key1", "yes");
                editor.apply();
                editor.commit();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "else " + filePath, Toast.LENGTH_SHORT).show();
        }

    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        } else {
            // code for lollipop and pre-lollipop devices
        }
    }

    private boolean checkAndRequestPermissions() {

        int camera = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);

        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("in fragment on request", "Permission callback called-------");

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.
                            PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(getContext(), "Go to settings and enable permissions",
                                            Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void init() {
        imgP = view.findViewById(R.id.imgP);
        imgProfile = view.findViewById(R.id.imgProfile);
        rlscratch = view.findViewById(R.id.rlscratch);
        rlDelete = view.findViewById(R.id.rlDelete);
        rlmycart = view.findViewById(R.id.rlmycart);
        rlMngacc = view.findViewById(R.id.rlMngacc);
        rlLogout = view.findViewById(R.id.rlLogout);
        rlOrders = view.findViewById(R.id.rlOrders);
        rlAboutus = view.findViewById(R.id.rlAboutus);
        rlprivacypolicy = view.findViewById(R.id.rlprivacypolicy);
        rl_termsofuse = view.findViewById(R.id.rl_termsofuse);
        rl_ContactUs = view.findViewById(R.id.rl_ContactUs);
        tvFName = view.findViewById(R.id.tvFName);
        tvLName = view.findViewById(R.id.tvLName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvMobile = view.findViewById(R.id.tvMobile);
        rlFAQ = view.findViewById(R.id.rlFAQ);

    }

    void getDataProfile() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                    try {
                        progressDialog.dismiss();
                        if (response.getBoolean("status") == true) {
                            JSONObject jsonObject1 = response.getJSONObject("data");
                            JSONObject jsonObject = jsonObject1.getJSONObject("details");

                            email = jsonObject.optString("email");
                            first_name = jsonObject.optString("first_name");
                            last_name = jsonObject.optString("last_name");
                            mobile = jsonObject.optString("mobile");
                            Log.d("DataofProfile","email: "+email+" first_name: "+first_name+" last_name: "+last_name
                                    +" mobile: "+mobile);
//                            if (email == null || first_name == null || last_name == null) {
//                                Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
//
//                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",
//                                        MODE_PRIVATE);
//                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                                myEdit.clear();
//                                myEdit.apply();
//
//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                startActivity(intent);
//                                getActivity().finish();
//
//                            } else {
////                                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
//                            }

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPre", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("CusMobile", "" + jsonObject.optString("mobile"));
                            myEdit.apply();
                            myEdit.commit();
    //
                            if (first_name == null || first_name.equals("null")) {
                                tvFName.setText(" ");  // Set a blank space or default text like "N/A"
                            } else {
                                tvFName.setText(first_name);
                            }

                            if (last_name == null || last_name.equals("null")) {
                                tvLName.setText(" ");  // Set a blank space or default text like "N/A"
                            } else {
                                tvLName.setText(last_name);
                            }

                            if (email == null || email.equals("null")) {
                                tvEmail.setText(" ");  // Set a blank space or default text like "N/A"
                            } else {
                                tvEmail.setText(email);
                            }

                            if (mobile == null || mobile.equals("null")) {
                                tvMobile.setText(" ");  // Set a blank space or default text like "N/A"
                            } else {
                                tvMobile.setText(mobile);
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

                SharedPreferences sharedPreferences = getActivity().
                        getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));


                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

    public AlphaAnimation clickAnimation() {
        return new AlphaAnimation(1F, 0.1F); // Change "0.4F" as per your recruitment.
    }


    public void AlertDialog() {


        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//set icon
                .setIcon(R.drawable.nex)
//set title
                .setTitle("Logout Confirmation")
//set message
                .setMessage("Are you sure want to Logout?")

//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",
                                MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.clear();
                        myEdit.apply();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    }
                })

//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                    }
                })
                .show();
    }

    @SuppressLint("Range")
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path;
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void getPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String newString = sharedPreferences.getString("imagePreferance", "photo");
        String NewString1 = sharedPreferences.getString("key1", "");
        if (newString.isEmpty() || NewString1.equals("yes")) {
            byte[] bytesImage = Base64.decode(newString, Base64.DEFAULT);
            Glide.with(getActivity()).load(bytesImage).into(imgP);
        }
    }
    private void showCustomAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.deleteuser_custom_layout, null);

        RelativeLayout rlCancel = dialogView.findViewById(R.id.rlCancel);
         CardView cardDelete = dialogView.findViewById(R.id.cardDelete);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();


        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserData();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    void deleteUserData() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.new_progresslogo);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.deleteuser,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if (response.getBoolean("status") == true) {
                        Toast.makeText(getContext(), "Account Deleted"  , Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",
                                MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.clear();
                        myEdit.apply();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else{
                        Toast.makeText(getContext(), "" + response.getString("message"),
                                Toast.LENGTH_SHORT).show();
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

                SharedPreferences sharedPreferences = getActivity().
                        getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));


                return headers;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

}