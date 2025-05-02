package com.mynexmy.nex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.mynexmy.nex.Fragments.CategoryFragment;
import com.mynexmy.nex.Fragments.HomeFragment;
import com.mynexmy.nex.Fragments.MycartFragment;
import com.mynexmy.nex.Fragments.ProfileFragment;
import com.mynexmy.nex.Fragments.Scratchcard_Fragment;
import com.mynexmy.nex.Fragments.SearchFragment;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;
import java.util.logging.Handler;

public class Home extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    String Token;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private static final String TAG = "Home";
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.blackIconStatusBar(Home.this, R.color.white);


        checkForAppUpdateAvailability();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//frame_container is your layout name in xml file
        transaction.replace(R.id.frame_container, new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();


        chipNavigationBar = findViewById(R.id.bottom_nvg);

        chipNavigationBar.setItemSelected(R.id.home,
                true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container,
                        new HomeFragment()).commit();
        bottomMenu();


        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        cartNotify(products.size());


    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        if (R.id.home == i) {
                            HomeFragment fragment6 = new HomeFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment6).commit();

                        } else if (R.id.category == i) {
                            CategoryFragment fragment5 = new CategoryFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment5).commit();

                        } else if (R.id.search == i) {
                            SearchFragment fragment4 = new SearchFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment4).commit();
                        } else if (R.id.scratchcard == i) {
                            Scratchcard_Fragment fragment3 = new Scratchcard_Fragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment3).commit();

                        } else if (R.id.my_cart == i) {
                            MycartFragment fragment2 = new MycartFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment2).commit();

                        } else if (R.id.profile == i) {
                            ProfileFragment fragment1 = new ProfileFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                            transaction.replace(R.id.frame_container, fragment1).commit();

                        }

                    }
                });
    }

    private void checkForAppUpdateAvailability() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                openPlayStore();
                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, Home.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "New app is ready!",
                Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
                Log.d("InstallationTag", "Installation initiated");
            } else {
                Log.e(TAG, "AppUpdateManager is null");
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.main));
        snackbar.show();
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            Log.d(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());

            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                    Log.d(TAG, "Installation complete");
                }
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "onActivityResult: app download failed");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ProductDatabase db = Room.databaseBuilder(getApplicationContext(),
                ProductDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        cartNotify(products.size());

    }


    public void cartNotify(int v) {

        if (v == 0) {
            chipNavigationBar.dismissBadge(R.id.my_cart);
        } else {
            chipNavigationBar.showBadge(R.id.my_cart, v);
        }
    }

    public void chipNotify() {

        chipNavigationBar.setItemSelected(R.id.scratchcard,
                true);
    }

    public void chipNotifyTocategory() {

        chipNavigationBar.setItemSelected(R.id.category,
                true);
    }

    public void chipNotifyToProfile() {

        chipNavigationBar.setItemSelected(R.id.profile,
                true);
    }

  public void chipNotifyToSearch() {

        chipNavigationBar.setItemSelected(R.id.search,
                true);
    }


}