package com.example.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.blackIconStatusBar(Home.this, R.color.white);

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
                        } else if (R.id.wishlist == i) {
                            WishlistFragment fragment3 = new WishlistFragment();
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


}