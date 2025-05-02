package com.mynexmy.nex.Fragments;

import static android.content.Context.MODE_PRIVATE;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.material.card.MaterialCardView;
import com.mynexmy.nex.Activitys.Grocery_Activity;
import com.mynexmy.nex.Activitys.Nextube_Activity;

import com.mynexmy.nex.Activitys.PickNPay_Activity;
import com.mynexmy.nex.Activitys.ProductDetails_All_Activity;
import com.mynexmy.nex.Activitys.Scratch_Activity;
import com.mynexmy.nex.Activitys.Viewall_Product_3_Activity;
import com.mynexmy.nex.Adapters.HomeCategory_Adapter;
import com.mynexmy.nex.Adapters.HomeProductAdapter;
import com.mynexmy.nex.Adapters.HomeProductAdapter2;
import com.mynexmy.nex.Adapters.HomeScratch_Adapter;
import com.mynexmy.nex.Adapters.ImageSliderAdapterFootwear;
import com.mynexmy.nex.Adapters.ImageSliderAdapterProducts;
import com.mynexmy.nex.Adapters.Product8Adapter;
import com.mynexmy.nex.Adapters.Search_Adapter;

import com.mynexmy.nex.Adapters.ImageSliderAdapterMovie;
import com.mynexmy.nex.Adapters.adapterHome;
import com.mynexmy.nex.ApiData;
import com.mynexmy.nex.Activitys.Category_Details_Activity;

import com.mynexmy.nex.ApiService;
import com.mynexmy.nex.BannerResponse;
import com.mynexmy.nex.CacheManager;
import com.mynexmy.nex.Home;
import com.mynexmy.nex.Adapters.ImageAdapter;
import com.mynexmy.nex.Models.HomeCategory_Model;
import com.mynexmy.nex.Models.HomeProductModel;
import com.mynexmy.nex.Models.HomeProductModel2;
import com.mynexmy.nex.Models.ModelHome;
import com.mynexmy.nex.Models.Search_Model;
import com.mynexmy.nex.Models.Slide_Model1;
import com.mynexmy.nex.Models.Slide_Model2;
import com.mynexmy.nex.Models.Slide_Model_8Product;
import com.mynexmy.nex.Models.Slide_Model_Footwear;
import com.mynexmy.nex.Models.Slide_Model_HomeScratch;
import com.mynexmy.nex.Models.Slide_Model_Movie;
import com.mynexmy.nex.Models.Slide_Model_Product;
import com.mynexmy.nex.Models.Slide_Model_Scratch;
import com.mynexmy.nex.ProductDetailPage2;
import com.mynexmy.nex.ProductDetailPage_Newest2_Activity;
import com.mynexmy.nex.R;
import com.mynexmy.nex.Room.Product;
import com.mynexmy.nex.Room.ProductDao;
import com.mynexmy.nex.Room.ProductDatabase;
import com.mynexmy.nex.Activitys.Viewall_Product_1_Activity;
import com.mynexmy.nex.Activitys.Viewall_Product_2_Activity;
import com.mynexmy.nex.Signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements
        adapterHome.ProductClick, HomeProductAdapter2.ProductPageClick,
        HomeCategory_Adapter.ProductPageClick, HomeProductAdapter.ProductPageClick,
        ImageSliderAdapterMovie.whenClick,
        ImageAdapter.whenClick,
        Search_Adapter.ProductPageClick,
        HomeScratch_Adapter.ProductPageClick {

    View view;
    Search_Adapter search_adapter;
    HorizontalScrollView horizontalScrollView;
    RelativeLayout l1;

    List<Search_Model> search_models;
    RequestQueue requestQueue;
    TextView etHomeSearch;
    SwipeRefreshLayout refreshLayout;
    RecyclerView rv1, rv2, rv3, rv4, rvHomeSearch, rvHomeScratch, rv8Product;
    LinearLayout  llHome;

    MaterialCardView mcvShopNow, mcvScratchcard, mcvPicknPay, mcvShopNowButton, mcvScratchcardButton,
            mcvPicknPayButton, mcvProfile;
    // Find the RelativeLayout and other views
    VideoView videoView;
    ImageView muteButton;
    ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    ImageView imgHomeLogo, imgRightCategory, imgLeftCategory, imgRightFeatured,
            imgLeftFeatured, imgRightAll, imgLeftAll, imgGroceries, imgNextube, imgShopnow, imgScratchcard,
            imgsc49, imgsc99, imgsc149, imgsc199, imgsc249, imgsc299, imgsc349, imgsc399, imgsc449,
            imgsc499, imgpicknpay, imgscratchCart, imgNotification, imgPicknPay;
    String imageUrlofmobile_grecery, imageUrlofmobile_pick_and_pay, imageUrlofmobile_scratch, imageUrlofmobile_shop_now,
            imageUrlofmobile_nextube, imageUrlofmobile_scratch_card, imageUrlofmobile_8_products;
    LottieAnimationView ani1, ani2, ani3;
    List<HomeProductModel> homeProductModels;
    List<HomeProductModel2> homeProductModels2;
    List<HomeCategory_Model> homeCategory_models;
    HomeProductAdapter homeProductAdapter;
    HomeProductAdapter2 homeProductAdapter2;
    HomeCategory_Adapter homeCategory_adapter;
    ImageSliderAdapterProducts imageSliderAdapterProducts;
    ImageSliderAdapterMovie imageSliderAdapterMovie;


    ImageView placeholderImageScratch, placeholderImageProduct, placeholderImageMovie,
            placeholderImageFootwear;
    private ViewPager2 viewPagerScratch, viewPagerProduct, viewPagerMovie, viewPagerFootwear;
    private LinearLayout dotIndicatorScratch, dotIndicatorProduct, dotIndicatorMovie, dotIndicatorFootwear;
    private Handler handlerScratch;
    private Runnable runnableScratch;
    private Handler handlerProduct;
    private Runnable runnableProduct;
    private Handler handlerMovie;
    private Runnable runnableMovie;
    private Handler handlerFootwear;
    private Runnable runnableFootwear;
    List<ModelHome> modelHomes;
    adapterHome adapterhome;
    ImageSlider imageslider1;
    CardView card, card49, card99, card149, card199, card249, card299, card349, card399, card449, card499;
    TextView tv_View1, tv_View2, tv_View3, tvLoading;
    ArrayList<Slide_Model1> slide_models = new ArrayList<>();
    private ArrayList<Slide_Model_Scratch> slide_models_Scratch = new ArrayList<>();
    private ArrayList<Slide_Model_Product> slide_model_products = new ArrayList<>();
    private ArrayList<Slide_Model_8Product> slide_model_8Products = new ArrayList<>();
    ArrayList<Slide_Model2> slide_models1 = new ArrayList<>();
    ArrayList<Slide_Model_Movie> slide_model_movie = new ArrayList<>();
    ArrayList<Slide_Model_Footwear> slide_model_footwears = new ArrayList<>();
    String email, first_name, last_name, mobile;
    ArrayList<Slide_Model_HomeScratch> slide_model_homeScratch = new ArrayList<>();
    private ExoPlayer player;
    StyledPlayerView playerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        init();


        getNewestProduct();
        getAllProduct();
        getCategory();
        fetchSliderImages();
        fetchBannerImages();
        getDataProfile();
        fetchVideo();


// Update the SwipeRefreshLayout listener
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Stop and release the current video
                stopCurrentVideo();

                // Refresh the video by re-fetching it
                getNewestProduct();
                getAllProduct();
                getCategory();
                fetchSliderImages();
                fetchBannerImages();
                getDataProfile();
                fetchVideo();  // Fetch the video again

                // Stop the refreshing animation
                refreshLayout.setRefreshing(false);
            }
        });



        imgHomeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Grocery_Activity.class);
                startActivity(intent);
            }
        });
        etHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChipSearch();
            }
        });


        tv_View1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Viewall_Product_1_Activity.class);
                int product_featured = 1;
                intent.putExtra("product_id", product_featured);
                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                tv_View1.startAnimation(myAnim);
            }
        });
        tv_View2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Viewall_Product_2_Activity.class);
                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                tv_View2.startAnimation(myAnim);
            }
        });
        mcvShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Viewall_Product_2_Activity.class);
                startActivity(intent);

            }
        });
        mcvShopNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Viewall_Product_2_Activity.class);
                startActivity(intent);

            }
        });
        imgscratchCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Scratch_Activity.class);
                startActivity(intent);

            }
        });

        mcvScratchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();

            }
        });
        mcvScratchcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();

            }
        });
        mcvPicknPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PickNPay_Activity.class);

                startActivity(intent);
            }
        });
        mcvPicknPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PickNPay_Activity.class);
                startActivity(intent);
            }
        });
        imgpicknpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Pick & Pay Launching Soon", Toast.LENGTH_SHORT).show();
            }
        });
        mcvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChipProfile();
            }
        });
        card49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();

                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card49.startAnimation(myAnim);
            }
        });
        card99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card99.startAnimation(myAnim);
            }
        });
        card149.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card149.startAnimation(myAnim);
            }
        });
        card199.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card199.startAnimation(myAnim);
            }
        });
        card249.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card249.startAnimation(myAnim);
            }
        });
        card299.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card299.startAnimation(myAnim);
            }
        });
        card349.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card349.startAnimation(myAnim);
            }
        });
        card399.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card399.startAnimation(myAnim);
            }
        });
        card449.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card449.startAnimation(myAnim);
            }
        });
        card499.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scratchcard_Fragment fragment2 = new Scratchcard_Fragment();
                FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                fragmentTransaction.replace(R.id.frame_container, fragment2).commit();
                ((Home) getActivity()).chipNotify();
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                card499.startAnimation(myAnim);
            }
        });
        tv_View3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Viewall_Product_3_Activity.class);
                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                tv_View3.startAnimation(myAnim);
            }
        });
        imgGroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Grocery_Activity.class);

                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgGroceries.startAnimation(myAnim);
            }
        });
        imgNextube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Nextube_Activity.class);
                startActivity(intent);
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgNextube.startAnimation(myAnim);
            }
        });


        imgRightCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgRightCategory.startAnimation(myAnim);
            }

        });
        imgLeftCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgLeftCategory.startAnimation(myAnim);
            }
        });


        imgRightFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgRightFeatured.startAnimation(myAnim);
            }
        });


        imgLeftFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgLeftFeatured.startAnimation(myAnim);
            }
        });
        imgRightAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgRightAll.startAnimation(myAnim);
            }
        });

        imgLeftAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                imgLeftAll.startAnimation(myAnim);
            }
        });


// Mute button functionality (same as before)
         muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isMuted = player.getVolume() == 0;
                if (isMuted) {
                    // Unmute the player
                    player.setVolume(1f);
                    muteButton.setImageResource(R.drawable.ic_volume_on);  // Change the icon to unmuted
                } else {
                    // Mute the player
                    player.setVolume(0f);
                    muteButton.setImageResource(R.drawable.ic_volume_off);  // Change the icon to muted
                }
            }
        });

     return view;
    }


    private void init() {

        rv1 = view.findViewById(R.id.rv1);
        rv2 = view.findViewById(R.id.rv2);
        rv3 = view.findViewById(R.id.rv3);
        rv4 = view.findViewById(R.id.rv4);
        rvHomeSearch = view.findViewById(R.id.rvHomeSearch);
        rvHomeScratch = view.findViewById(R.id.rvHomeScratch);

        rv8Product = view.findViewById(R.id.rv8Product);
        etHomeSearch = view.findViewById(R.id.etHomeSearch);
        ani1 = view.findViewById(R.id.ani1);
        ani2 = view.findViewById(R.id.ani2);
        ani3 = view.findViewById(R.id.ani3);
        // Find the RelativeLayout and other views
        videoView = view.findViewById(R.id.videoView);
        muteButton = view.findViewById(R.id.muteButton);
        progressBar = view.findViewById(R.id.progressBar);
        imgRightFeatured = view.findViewById(R.id.imgRightFeatured);
        imgLeftFeatured = view.findViewById(R.id.imgLeftFeatured);
        imgRightAll = view.findViewById(R.id.imgRightAll);
        imgLeftAll = view.findViewById(R.id.imgLeftAll);
        imgHomeLogo = view.findViewById(R.id.imgHomeLogo);
        imgRightCategory = view.findViewById(R.id.imgRightCategory);
        imgLeftCategory = view.findViewById(R.id.imgLeftCategory);
        imgGroceries = view.findViewById(R.id.imgGroceries);
        imgShopnow = view.findViewById(R.id.imgShopnow);
        imgPicknPay = view.findViewById(R.id.imgPicknPay);
        imgScratchcard = view.findViewById(R.id.imgScratchcard);
        imgNextube = view.findViewById(R.id.imgNextube);
        imgsc49 = view.findViewById(R.id.imgsc49);
        imgsc99 = view.findViewById(R.id.imgsc99);
        imgsc149 = view.findViewById(R.id.imgsc149);
        imgsc199 = view.findViewById(R.id.imgsc199);
        imgsc249 = view.findViewById(R.id.imgsc249);
        imgsc299 = view.findViewById(R.id.imgsc299);
        imgsc349 = view.findViewById(R.id.imgsc349);
        imgsc399 = view.findViewById(R.id.imgsc399);
        imgsc449 = view.findViewById(R.id.imgsc449);
        imgsc499 = view.findViewById(R.id.imgsc499);
        imgpicknpay = view.findViewById(R.id.imgpicknpay);
        imgscratchCart = view.findViewById(R.id.imgscratchCart);
        imgNotification = view.findViewById(R.id.imgNotification);

        playerView = view.findViewById(R.id.player_view);
        tv_View1 = view.findViewById(R.id.tv_View1);
        tv_View2 = view.findViewById(R.id.tv_View2);
        tv_View3 = view.findViewById(R.id.tv_View3);
        placeholderImageScratch = view.findViewById(R.id.placeholderImageScratch);
        placeholderImageProduct = view.findViewById(R.id.placeholderImageProduct);
        placeholderImageMovie = view.findViewById(R.id.placeholderImageMovie);
        placeholderImageFootwear = view.findViewById(R.id.placeholderImageFootwear);
        viewPagerScratch = view.findViewById(R.id.viewPagerScratch);
        viewPagerProduct = view.findViewById(R.id.viewPagerProduct);
        viewPagerMovie = view.findViewById(R.id.viewPagerMovie);
        viewPagerFootwear = view.findViewById(R.id.viewPagerFootwear);
        dotIndicatorScratch = view.findViewById(R.id.dotIndicatorScratch);
        dotIndicatorProduct = view.findViewById(R.id.dotIndicatorProduct);
        dotIndicatorMovie = view.findViewById(R.id.dotIndicatorMovie);
        dotIndicatorFootwear = view.findViewById(R.id.dotIndicatorFootwear);


        card = view.findViewById(R.id.card);
        card49 = view.findViewById(R.id.card49);
        card99 = view.findViewById(R.id.card99);
        card149 = view.findViewById(R.id.card149);
        card199 = view.findViewById(R.id.card199);
        card249 = view.findViewById(R.id.card249);
        card299 = view.findViewById(R.id.card299);
        card349 = view.findViewById(R.id.card349);
        card399 = view.findViewById(R.id.card399);
        card449 = view.findViewById(R.id.card449);
        card499 = view.findViewById(R.id.card499);
        imageslider1 = view.findViewById(R.id.imageslider1);
//        tvLoading = view.findViewById(R.id.tvLoading);


//        llShopnow = view.findViewById(R.id.llShopnow);
        mcvShopNow = view.findViewById(R.id.mcvShopNow);
        mcvScratchcard = view.findViewById(R.id.mcvScratchcard);
        mcvPicknPay = view.findViewById(R.id.mcvPicknPay);
        mcvShopNowButton = view.findViewById(R.id.mcvShopNowButton);
        mcvScratchcardButton = view.findViewById(R.id.mcvScratchcardButton);
        mcvPicknPayButton = view.findViewById(R.id.mcvPicknPayButton);
        mcvProfile = view.findViewById(R.id.mcvProfile);
//        llscratchcard = view.findViewById(R.id.llscratchcard);
        llHome = view.findViewById(R.id.llHome);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        l1 = view.findViewById(R.id.l1);

    }

    //
    private void getProductDetails(String id) {
        // Creating a string request to send request to the URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Search + "?query=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        search_models = new ArrayList<>();
                        search_models.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                int product_id = user.optInt("product_id");
                                int category_id = user.optInt("category_id");
                                int product_status = user.optInt("product_status");
                                int added_by = user.optInt("added_by");
                                int product_reg_price = user.optInt("product_reg_price");
                                int product_sell_price = user.optInt("product_sell_price");
                                double product_rating = user.optDouble("product_rating");
                                int product_rating_total = user.optInt("product_rating_total");
                                int product_featured = user.optInt("product_featured");
                                int product_tax_percent = user.optInt("product_tax_percent");
                                String product_title = user.optString("product_title");
                                String product_url = user.optString("product_url");
                                String product_code = user.optString("product_code");
                                String product_description = user.optString("product_description");
                                String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                // Adding the product to the list
                                search_models.add(new Search_Model(product_id, product_rating, product_image,
                                        product_title, product_sell_price, product_reg_price, product_tax_percent));
                            }

                            // Setting up the adapter and RecyclerView
                            search_adapter = new Search_Adapter(getActivity(), search_models);
                            Collections.reverse(search_models);
                            GridLayoutManager layoutManagerC = new GridLayoutManager(getActivity(), 2);
                            rvHomeSearch.setLayoutManager(layoutManagerC);
                            rvHomeSearch.setItemAnimator(new DefaultItemAnimator());
                            rvHomeSearch.setAdapter(search_adapter);
                            search_adapter.set(HomeFragment.this);

//                            tvLoading.setVisibility(View.GONE);
                            llHome.setVisibility(View.GONE);
                            rvHomeSearch.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error (can show a toast, log, etc.)
                    }
                });

        // Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Adding the string request to the request queue
        requestQueue.add(stringRequest);
    }

    private void getCategory() {
        //getting the progressbar

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Category_list,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            homeCategory_models = new ArrayList<>();
                            homeCategory_models.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int category_id = user.optInt("category_id");
                                String category_parent = user.optString("category_parent");
                                String category_title = user.optString("category_title");
                                String category_slug = user.optString("category_slug");
                                int category_status = user.optInt("category_status");
                                String category_image = user.optString("category_image");
                                String category_description = user.optString("category_description");
                                int added_by = user.optInt("added_by");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");

                                homeCategory_models.add(new HomeCategory_Model(category_id, category_title,
                                        category_image));

                            }
                            homeCategory_adapter = new HomeCategory_Adapter(getActivity(), homeCategory_models);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(),
                                    LinearLayoutManager.HORIZONTAL, true);
                            layoutManager2.setStackFromEnd(true);

                            rv4.setLayoutManager(layoutManager2);
                            rv4.setItemViewCacheSize(0);
                            rv4.setHasFixedSize(true);
                            rv4.setItemAnimator(new DefaultItemAnimator());
                            rv4.setAdapter(homeCategory_adapter);
                            homeCategory_adapter.set(HomeFragment.this);

                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occur
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }


    public void fetchSliderImages() {
        // Display placeholder data initially


        // Make the network request to fetch slider data
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Sliders,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("sliderData", response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");

                            // If the status is true, process the slider data
                            if (status) {
                                if (jsonObject.has("data")) {
                                    JSONObject dataObject = jsonObject.getJSONObject("data");

                                    // Extract the "home" array
                                    if (dataObject.has("home")) {
                                        JSONArray homeArray = dataObject.getJSONArray("home");

                                        // Clear previous data before populating the lists
                                        slide_models_Scratch.clear();
                                        slide_model_products.clear();
                                        slide_model_movie.clear();

                                        // Process images in the "home" array
                                        for (int i = 0; i < homeArray.length(); i++) {
                                            String imageUrl = homeArray.getString(i);
                                            Log.d("sliderData", "Adding image URL: " + imageUrl);

                                            // Add to the respective list based on index range
                                            if (i >= 0 && i <= 4) {
                                                slide_models_Scratch.add(new Slide_Model_Scratch(imageUrl, String.valueOf(i)));
                                            }
                                            if (i >= 5 && i <= 14) {
                                                slide_model_products.add(new Slide_Model_Product(imageUrl, String.valueOf(i)));
                                            }
                                            if (i >= 15 && i <= 22) {
                                                slide_model_movie.add(new Slide_Model_Movie(imageUrl,
                                                        String.valueOf(i), 1));
                                            }
                                        }

                                        // Set up RecyclerView and adapters for Scratch section
                                        if (!slide_models_Scratch.isEmpty()) {
                                            ImageAdapter imageAdapter1 = new ImageAdapter(
                                                    (FragmentActivity) getActivity(),
                                                    slide_models_Scratch,
                                                    new ImageAdapter.whenClick() {
                                                        @Override
                                                        public void setChip() {
                                                            ((Home) getActivity()).chipNotify();
                                                        }
                                                    }
                                            );


                                            placeholderImageScratch.setVisibility(View.GONE);
                                            viewPagerScratch.setAdapter(imageAdapter1);
                                            addDotsIndicator();
                                            setupAutoScrolling();
                                            viewPagerScratch.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicator(position);
                                                }
                                            });
                                        } else {
                                            Log.e("sliderData", "No data found for slide_models_Scratch (slide_models_Scratch is empty)");
                                        }

//                                         Set up RecyclerView and adapters for Products section
                                        if (!slide_model_products.isEmpty()) {
                                            ImageSliderAdapterProducts imageAdapter1 =
                                                    new ImageSliderAdapterProducts(
                                                            (FragmentActivity) getActivity(),    // Pass the context (ensure it's a FragmentActivity)
                                                            slide_model_products                  // Pass the slide_model_products list
                                                    );
                                            placeholderImageProduct.setVisibility(View.GONE);
                                            // Set the adapter to the ViewPager
                                            viewPagerProduct.setAdapter(imageAdapter1);
                                            addDotsIndicatorProduct();
                                            setupAutoScrollingProduct();

                                            viewPagerProduct.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicatorProduct(position);
                                                }
                                            });
                                        } else {
                                            Log.e("sliderData", "No data found for recyclerViewProduct (slide_model_products is empty)");
                                        }

//                                        // Set up RecyclerView and adapters for Movie section
                                        if (!slide_model_movie.isEmpty()) {
                                            ImageSliderAdapterMovie imageAdapter1 = new ImageSliderAdapterMovie(
                                                    (FragmentActivity) getActivity(),    // Pass the context (ensure it's a FragmentActivity)
                                                    slide_model_movie                  // Pass the slide_model_products list
                                            );
                                            placeholderImageMovie.setVisibility(View.GONE);
                                            // Set the adapter to the ViewPager
                                            viewPagerMovie.setAdapter(imageAdapter1);
                                            addDotsIndicatorMovie();
                                            setupAutoScrollingMovie();

                                            viewPagerMovie.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                @Override
                                                public void onPageSelected(int position) {
                                                    super.onPageSelected(position);
                                                    updateDotIndicatorMovie(position);
                                                }
                                            });

                                        } else {
                                            Log.e("sliderData", "No data found for recyclerViewMovie (slide_model_movie is empty)");
                                        }
                                        llHome.setVisibility(View.VISIBLE);
//                                        tvLoading .setVisibility(View.GONE);
                                        l1.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("sliderData", "Error parsing response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("sliderData", "Error fetching slider images: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }
     // Add a class-level variable to track the current video URL
    private String currentVideoUrl = "";


    public void fetchVideo() {
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://3nex.co.in/api/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Call API to fetch banner images
        Call<BannerResponse> call = apiService.fetchVideo();
        call.enqueue(new Callback<BannerResponse>() {

            @Override
            public void onResponse(Call<BannerResponse> call, retrofit2.Response<BannerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BannerResponse bannerResponse = response.body();
                    if (bannerResponse.isStatus()) {
                        List<String> mobileVideos = bannerResponse.getData().getVideo();

                        if (mobileVideos != null && !mobileVideos.isEmpty()) {
                            currentVideoUrl = mobileVideos.get(0); // Store the current video URL
                            String videoUrl = currentVideoUrl;
                            Log.e("dataofmobile_video", videoUrl);

                            // Parse the video URL and get the Uri for ExoPlayer
                            Uri videoUri = Uri.parse(videoUrl);

                            // Initialize the ExoPlayer
                            player = new ExoPlayer.Builder(getContext()).build();
                            playerView.setPlayer(player);

                            // Set the media item for the video URL
                            MediaItem mediaItem = MediaItem.fromUri(videoUri);
                            player.setMediaItem(mediaItem);
                            player.prepare();

                            // Set the listener for buffering and progress bar visibility
                            player.addListener(new Player.Listener() {
                                @Override
                                public void onPlaybackStateChanged(int state) {
                                    if (state == Player.STATE_READY) {
                                        // Hide the progress bar when the video is ready
                                        progressBar.setVisibility(View.GONE);
                                        player.setPlayWhenReady(true); // Start the playback
                                    } else if (state == Player.STATE_BUFFERING) {
                                        // Show the progress bar when buffering
                                        progressBar.setVisibility(View.VISIBLE);
                                    }
                                }

                                public void onPlayerError(ExoPlaybackException error) {
                                    // Handle errors if any
                                    Toast.makeText(getContext(), "Error playing video", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // Listener to auto-play the video after it finishes
                            player.addListener(new Player.Listener() {
                                @Override
                                public void onPlaybackStateChanged(int state) {
                                    if (state == Player.STATE_ENDED) {
                                        // If video ends, restart it
                                        player.seekTo(0); // Optionally, reset to the start
                                        player.play();   // Restart the video
                                    }
                                }
                            });

                            // If video is already cached, use the cached file
                            CacheManager cacheManager = new CacheManager(getContext());
                            String cacheKey = videoUrl.hashCode() + ".mp4"; // Cache by URL hash
                            File cachedFile = cacheManager.getCacheFile(cacheKey);

                            if (cachedFile.exists()) {
                                // If cached, use the cached file instead of downloading
                                videoUri = Uri.fromFile(cachedFile);
                                Log.e("VideoCache", "Loading cached video: " + cachedFile.getPath());
                            } else {
                                // Otherwise, download and cache the video
                                Log.e("VideoCache", "Downloading and caching video.");
                                cacheManager.cacheVideo(videoUrl, cacheKey);
                            }

                            // Optionally set the video for playback if no cache is used
                            player.setMediaItem(MediaItem.fromUri(videoUri));
                        }
                    } else {
                        // Handle the case where no video URL is available
                        Toast.makeText(getContext(), "No video available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle failure (e.g., error in response)
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                // Handle failure (e.g., network error)
                Log.e("APIError", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Define a method to stop and release the current player
    private void stopCurrentVideo() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;  // Clear the player reference
        }

        // Clear the cached video (if necessary)
        CacheManager cacheManager = new CacheManager(getContext());
        String cacheKey = currentVideoUrl.hashCode() + ".mp4"; // Cache by URL hash
        File cachedFile = cacheManager.getCacheFile(cacheKey);

        if (cachedFile.exists()) {
            cachedFile.delete();  // Remove the cached video file
            Log.e("VideoCache", "Cache cleared: " + cachedFile.getPath());
        }
    }

    public void fetchBannerImages() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.Banners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("BannerData", response);

                            JSONObject jsonObject = new JSONObject(response);

                            boolean status = jsonObject.getBoolean("status");
                            slide_model_8Products.clear();
                            slide_model_footwears.clear();

                            if (status) {
                                // Check if "data" object exists
                                if (jsonObject.has("data")) {
                                    // Get the "data" object
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONArray mobile_greceryArray = dataObject.optJSONArray("mobile_grecery");
                                    JSONArray mobile_scratchArray = dataObject.optJSONArray("mobile_scratch");
                                    JSONArray mobile_shop_nowArray = dataObject.optJSONArray("mobile_shop_now");
                                    JSONArray mobile_nextubeArray = dataObject.optJSONArray("mobile_nextube");
                                    JSONArray mobile_miscArray = dataObject.optJSONArray("mobile_misc");
                                    JSONArray mobile_ottArray = dataObject.optJSONArray("mobile_ott");
                                    JSONArray mobile_scratch_cardArray = dataObject.optJSONArray("mobile_scratch_card");
                                    JSONArray mobile_nextube_posterArray = dataObject.optJSONArray("mobile_nextube_poster");
                                    JSONArray mobile_grocery_posterArray = dataObject.optJSONArray("mobile_grocery_poster");
                                    JSONArray mobile_clothingArray = dataObject.optJSONArray("mobile_clothing");
                                    JSONArray mobile_movieArray = dataObject.optJSONArray("mobile_movie");
                                    JSONArray mobile_8_products = dataObject.optJSONArray("8_products");
                                    JSONArray mobile_footwear = dataObject.optJSONArray("footwear");
//                                    JSONArray mobile_video = dataObject.optJSONArray("video");
                                    JSONArray mobile_pick_and_pay = dataObject.optJSONArray("pick_and_pay");


                                    for (int i = 0; i < mobile_8_products.length(); i++) {
                                        String imageUrl = mobile_8_products.getString(i);

                                        slide_model_8Products.add(new Slide_Model_8Product(imageUrl,
                                                String.valueOf(i)));
                                        Log.e("dataof8Product", "" + imageUrl);

                                    }
                                    for (int i = 0; i < mobile_footwear.length(); i++) {
                                        String imageUrl = mobile_footwear.getString(i);

                                        slide_model_footwears.add(new Slide_Model_Footwear(imageUrl,
                                                String.valueOf(i), 1));
                                        Log.e("dataofFootwear", "" + imageUrl);

                                    }
                                    for (int i = 0; i < mobile_grocery_posterArray.length(); i++) {
                                        imageUrlofmobile_grecery = mobile_grocery_posterArray.getString(i);
                                        if (isAdded()) {
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_grecery)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgGroceries);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < mobile_pick_and_pay.length(); i++) {
                                        imageUrlofmobile_pick_and_pay = mobile_pick_and_pay.getString(i);
                                        if (isAdded()) {
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_pick_and_pay)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgPicknPay);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < mobile_scratchArray.length(); i++) {
                                        imageUrlofmobile_scratch = mobile_scratchArray.getString(i);
                                        if (isAdded()) {
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgScratchcard);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < mobile_shop_nowArray.length(); i++) {
                                        imageUrlofmobile_shop_now = mobile_shop_nowArray.getString(i);
                                        if (isAdded()) {
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_shop_now)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgShopnow);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < mobile_nextube_posterArray.length(); i++) {
                                        imageUrlofmobile_nextube = mobile_nextube_posterArray.getString(i);
                                        if (isAdded()) {
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_nextube)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgNextube);
                                            }
                                        }
                                    }
                                    for (int i = 0; i < mobile_scratch_cardArray.length(); i++) {
                                        imageUrlofmobile_scratch_card = mobile_scratch_cardArray.getString(i);
                                        if (isAdded()) {
//                                            if (i == 0) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgsc49);
//                                            }
                                            if (i == 8) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgsc99);
                                            }
//                                            if (i == 2) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgsc149);
//                                            }
                                            if (i == 6) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgsc199);
                                            }
//                                            if (i == 4) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgsc249);
//                                            }
                                            if (i == 4) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgsc299);
                                            }
//                                            if (i == 6) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgsc349);
//                                            }

                                            if (i == 2) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgsc399);
                                            }
//                                            if (i == 8) {
//                                                Glide.with(requireContext())
//                                                        .load(imageUrlofmobile_scratch_card)
//                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
//                                                        .into(imgsc449);
//                                            }
                                            if (i == 0) {
                                                Glide.with(requireContext())
                                                        .load(imageUrlofmobile_scratch_card)
                                                        .fitCenter().placeholder(R.drawable.plash).dontAnimate()
                                                        .into(imgsc499);
                                            }
                                        }
                                    }


                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(),
                                            LinearLayoutManager.HORIZONTAL, false);

                                    rv8Product.setLayoutManager(layoutManager1);

                                    Product8Adapter imageAdapter = new Product8Adapter(slide_model_8Products);

                                    rv8Product.setAdapter(imageAdapter);


                                    if (!slide_model_footwears.isEmpty()) {
                                        ImageSliderAdapterFootwear imageAdapter1 = new ImageSliderAdapterFootwear(
                                                (FragmentActivity) getActivity(),
                                                slide_model_footwears

                                        );

                                        placeholderImageFootwear.setVisibility(View.GONE);
                                        viewPagerFootwear.setAdapter(imageAdapter1);
                                        addDotsIndicatorFootwear();
                                        setupAutoScrollingFootwear();
                                        viewPagerFootwear.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                            @Override
                                            public void onPageSelected(int position) {
                                                super.onPageSelected(position);
                                                updateDotIndicatorFootwear(position);
                                            }
                                        });
                                    } else {
                                        Log.e("sliderData", "No data found for slide_models_Scratch (slide_models_Scratch is empty)");
                                    }

//


                                    Log.e("BannerData", "imageUrlofmobile_grecery: " + imageUrlofmobile_grecery + " imageUrlofmobile_scratch:" +
                                            " " + imageUrlofmobile_scratch + " imageUrlofmobile_nextube: " + imageUrlofmobile_nextube + " " +
                                            "imageUrlofmobile_shop_now: " + imageUrlofmobile_shop_now + " mobile_miscArray " + mobile_miscArray
                                            + " mobile_ottArray " + mobile_ottArray
                                            + " mobile_scratch_cardArray " + mobile_scratch_cardArray
                                            + " mobile_nextube_posterArray " + mobile_nextube_posterArray + " mobile_clothingArray " + mobile_clothingArray + " mobile_movieArray " + mobile_movieArray);


//                                        SliderAdapter1 adapter = new SliderAdapter1(getContext(), slide_models);
//
//                                        slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//                                        slider.setSliderAdapter(adapter);
//
//                                        slider.setScrollTimeInSec(2);
//
//                                        slider.setAutoCycle(true);
//
//                                        slider.startAutoCycle();
//                                        adapter.setOnClick1(HomeFragment.this);


                                } else {
                                    // Handle case when "data" object is missing
                                    Log.e("BannerData", "No 'data' object found in JSON response");
                                }
                            } else {
                                // Handle case when status is false
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON exception
                            Log.e("BannerData", "Error parsing JSON: " + e.getMessage());
//                            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the Volley error object
                        Log.e("Volley Error", "Error: " + error.toString(), error);
                        // Handle the error as needed
                    }
                }
        );

// Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void getNewestProduct() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.product_list,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            homeProductModels = new ArrayList<HomeProductModel>();
                            homeProductModels.clear();
                            modelHomes = new ArrayList<ModelHome>();
                            modelHomes.clear();
                            int maxLimit = 40; // Set the maximum limit to 40
                            int count = 0;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int product_id = user.optInt("product_id");
                                String product_title = user.optString("product_title");
                                String product_url = user.optString("product_url");
                                String product_code = user.optString("product_code");
                                String product_description = user.optString("product_description");
                                int category_id = user.optInt("category_id");
                                String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                int product_status = user.optInt("product_status");
                                int added_by = user.optInt("added_by");
                                int product_reg_price = user.optInt("product_reg_price");
                                int product_sell_price = user.optInt("product_sell_price");
                                double product_rating = user.optDouble("product_rating");
                                int product_rating_total = user.optInt("product_rating_total");
                                int product_featured = user.optInt("product_featured");
                                int product_tax_percent = user.optInt("product_tax_percent");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");
                                int category_parent = user.optInt("category_parent");
                                String category_title = user.optString("category_title");
                                String category_slug = user.optString("category_slug");
                                int category_status = user.optInt("category_status");
                                String category_image = user.optString("category_image");
                                String category_description = user.optString("category_description");
                                JSONArray productsImages = user.getJSONArray("product_images");

                                // Counter for the number of items added to modelHomes

                                if (product_featured == 0) {
                                    // Check if the count has reached the limit
                                    if (count < maxLimit) {
                                        modelHomes.add(new ModelHome(product_id, product_rating,
                                                product_title, product_description, product_rating_total,
                                                product_image, product_sell_price, product_reg_price, product_tax_percent));
                                        count++; // Increment the counter
                                    }
                                } else {
                                    homeProductModels.add(new HomeProductModel(product_id, product_rating,
                                            product_title, product_description, product_rating_total,
                                            product_image, product_sell_price, product_reg_price, product_tax_percent, added_by));
                                }

                            }

                            setData1();

                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occur
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void getAllProduct() {


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiData.product_list,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            homeProductModels2 = new ArrayList<>();
                            homeProductModels2.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                int product_id = user.optInt("product_id");
                                String product_title = user.optString("product_title");
                                String product_url = user.optString("product_url");
                                String product_code = user.optString("product_code");
                                String product_description = user.optString("product_description");
                                int category_id = user.optInt("category_id");
                                String product_image = ApiData.IMAGE_BASE_URL + user.optString("product_image");
                                int product_status = user.optInt("product_status");
                                int added_by = user.optInt("added_by");
                                int product_reg_price = user.optInt("product_reg_price");
                                int product_sell_price = user.optInt("product_sell_price");
                                double product_rating = user.optDouble("product_rating");
                                int product_rating_total = user.optInt("product_rating_total");
                                int product_featured = user.optInt("product_featured");
                                int product_tax_percent = user.optInt("product_tax_percent");
                                String created_at = user.optString("created_at");
                                String updated_at = user.optString("updated_at");
                                int category_parent = user.optInt("category_parent");
                                String category_title = user.optString("category_title");
                                String category_slug = user.optString("category_slug");
                                int category_status = user.optInt("category_status");
                                String category_image = user.optString("category_image");
                                String category_description = user.optString("category_description");
                                JSONArray productsImages = user.getJSONArray("product_images");

                                homeProductModels2.add(new HomeProductModel2(product_id, product_rating,
                                        product_title, product_description, product_rating_total,
                                        product_image, product_sell_price, product_reg_price, product_tax_percent, added_by));


                            }
                            setData2();

                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occur
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    void getDataProfile() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ApiData.Profile,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        JSONObject jsonObject = jsonObject1.getJSONObject("details");

                        email = jsonObject.optString("email");
                        first_name = jsonObject.optString("first_name");
                        last_name = jsonObject.optString("last_name");

                        if (getContext() != null) {
                            float density = getContext().getResources().getDisplayMetrics().density;
                            int sizeInPixels = (int) (25 * density);  // 25dp to pixels

                            if (first_name == null || first_name.equals("null")) {
                                imgNotification.setImageResource(R.drawable.notification_dot);
                                Log.d("dataofLogin", "No first name");

                                imgNotification.setOnClickListener(v -> {
                                    // Redirect to Signup if first_name is null or empty
                                    Intent i = new Intent(getActivity(), Signup.class);
                                    startActivity(i);
                                });

                            } else {

                                imgNotification.setImageResource(R.drawable.notification);

                                // Set size to 25dp
                                ViewGroup.LayoutParams params = imgNotification.getLayoutParams();
                                params.width = sizeInPixels;
                                params.height = sizeInPixels;
                                imgNotification.setLayoutParams(params);
                                Log.d("dataofLogin", first_name);

                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e("dataofLogin", "Error parsing response", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("Login_Token", ""));
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

    public void setData1() {

        ani1.setVisibility(View.GONE);
        ani2.setVisibility(View.GONE);

        homeProductAdapter = new HomeProductAdapter(getActivity(), homeProductModels);
//        Collections.reverse(homeProductModels);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                2);
        rv1.setLayoutManager(layoutManager);
        rv1.setItemViewCacheSize(0);
        rv1.setHasFixedSize(true);
        rv1.setItemAnimator(new DefaultItemAnimator());
        rv1.setAdapter(homeProductAdapter);
        homeProductAdapter.set(HomeFragment.this);


        adapterhome = new adapterHome(getContext(), modelHomes);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),
                2);

        rv2.setLayoutManager(layoutManager1);
        rv2.setItemViewCacheSize(20);
        rv2.setHasFixedSize(true);
        rv2.setItemAnimator(new DefaultItemAnimator());
        rv2.setAdapter(adapterhome);
        adapterhome.set(HomeFragment.this);

    }

    public void setData2() {
        ani3.setVisibility(View.GONE);

        homeProductAdapter2 = new HomeProductAdapter2(getActivity(), homeProductModels2);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                2);
        rv3.setLayoutManager(layoutManager);
        rv3.setItemAnimator(new DefaultItemAnimator());
        rv3.setItemViewCacheSize(0);
        rv3.setAdapter(homeProductAdapter2);
        rv3.setHasFixedSize(true);
        homeProductAdapter2.set(HomeFragment.this);

    }

    @Override
    public void productClick(int position, int product_id, int category_id, double product_rating,
                             String product_image, String product_title, int product_sell_price,
                             String product_description, int product_rating_total, int product_reg_price,
                             int product_tax_percent) {
        Intent intent = new Intent(getActivity(), ProductDetailPage2.class);

        intent.putExtra("product_id", product_id);
        intent.putExtra("category_id", category_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);
        intent.putExtra("product_description", product_description);
        intent.putExtra("product_rating_total", product_rating_total);

        startActivity(intent);
    }

    @Override
    public void addToCart(int position, int product_id, int category_id, double product_rating, String product_image,
                          String product_title, int product_sell_price, String product_description, int product_rating_total,
                          int product_reg_price, int product_tax_percent) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (check == false) {
            int pid = product_id;
            String pname = product_title;
            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1,
                    product_image));
            Toast.makeText(getContext(), "" + product_title + " Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void categoryproductClick(int position, int category_id, String category_title) {
        Intent intent = new Intent(getActivity(), Category_Details_Activity.class);
        intent.putExtra("category_id", category_id);
        intent.putExtra("category_title", category_title);
        startActivity(intent);
    }

    @Override
    public void dataUpdate(int position) {
        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        List<Product> products = productDao.getallproduct();
        ((Home) getActivity()).cartNotify(products.size());

    }

    @Override
    public void productClick(int position, int product_id, double product_rating, String product_image,
                             String product_title, int product_sell_price, int product_reg_price, int product_tax_percent) {
        Intent intent = new Intent(getActivity(), ProductDetailPage_Newest2_Activity.class);

        intent.putExtra("product_id", product_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);

        startActivity(intent);
    }

    @Override
    public void addtocart(int position, int product_id, double product_rating, String product_image,
                          String product_title, int product_sell_price, int product_reg_price, int product_tax_percent) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));
        if (check == false) {
            int pid = product_id;
            String pname = product_title;

            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1, product_image));
            Toast.makeText(getActivity(), "" + pname + " Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Product Already in Cart", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void productClickHomeNew(int position, int product_id, int category_id, double product_rating,
                                    String product_image, String product_title, int product_sell_price,
                                    String product_description, int product_rating_total,
                                    int product_reg_price, int product_tax_percent) {
        Intent intent = new Intent(getActivity(), ProductDetails_All_Activity.class);

        intent.putExtra("product_id", product_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);
        intent.putExtra("product_description", product_description);
        intent.putExtra("product_rating_total", product_rating_total);

        startActivity(intent);
    }


    @Override
    public void productClickView(int position, int product_id, int category_id, double product_rating,
                                 String product_image, String product_title, int product_sell_price,
                                 String product_description, int product_rating_total, int product_reg_price,
                                 int product_tax_percent) {
        Intent intent = new Intent(getActivity(), ProductDetailPage2.class);

        intent.putExtra("product_id", product_id);
        intent.putExtra("category_id", category_id);
        intent.putExtra("product_rating", product_rating);
        intent.putExtra("product_image", product_image);
        intent.putExtra("product_title", product_title);
        intent.putExtra("product_sell_price", product_sell_price);
        intent.putExtra("product_reg_price", product_reg_price);
        intent.putExtra("product_description", product_description);
        intent.putExtra("product_rating_total", product_rating_total);


        startActivity(intent);
    }

    @Override
    public void addToCartView(int position, int product_id, int category_id, double product_rating,
                              String product_image, String product_title, int product_sell_price,
                              String product_description, int product_rating_total, int product_reg_price,
                              int product_tax_percent) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));

        if (check == false) {
            int pid = product_id;
            String pname = product_title;
            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1,
                    product_image));
            Toast.makeText(getContext(), "" + product_title + " Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void addToCartHome(int position, int product_id, int category_id, double product_rating,
                              String product_image, String product_title, int product_sell_price,
                              String product_description, int product_rating_total, int product_reg_price, int product_tax_percent) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Tax", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("product_tax_percent", product_tax_percent);
        myEdit.apply();
        myEdit.commit();

        ProductDatabase db = Room.databaseBuilder(getContext(), ProductDatabase.class, "cart_db")
                .allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        Boolean check = productDao.is_exist(Integer.parseInt("" + product_id));

        if (check == false) {
            int pid = product_id;
            String pname = product_title;
            int price1 = product_reg_price;
            int sale1 = product_sell_price;
            int qnt = Integer.parseInt("1");
            productDao.insertrecord(new Product(pid, pname, price1, qnt, sale1,
                    product_image));
            Toast.makeText(getContext(), "" + product_title + " Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void setChip() {
        ((Home) getActivity()).chipNotify();
    }


    public void setChipProfile() {
        ((Home) getActivity()).chipNotifyToProfile();
    }

    public void setChipSearch() {
        ((Home) getActivity()).chipNotifyToSearch();
    }

    private void addDotsIndicator() {
        // Ensure the fragment is attached and context is not null
        if (getContext() == null || dotIndicatorScratch == null || slide_models_Scratch.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorScratch.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_models_Scratch.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorScratch.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicator(0);
    }

    private void addDotsIndicatorProduct() {
        // Ensure the fragment is attached and context is not null
        if (getContext() == null || dotIndicatorProduct == null || slide_model_products.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorProduct.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_products.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorProduct.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicatorProduct(0);
    }

    private void addDotsIndicatorMovie() {
        // Ensure the fragment is attached and context is not null
        if (getContext() == null || dotIndicatorMovie == null || slide_model_movie.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorMovie.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_movie.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorMovie.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicatorMovie(0);
    }

    private void addDotsIndicatorFootwear() {
        // Ensure the fragment is attached and context is not null
        if (getContext() == null || dotIndicatorFootwear == null || slide_model_footwears.isEmpty())
            return;

        // Remove previous dots
        dotIndicatorFootwear.removeAllViews();

        // Add new dots based on the number of images
        for (int i = 0; i < slide_model_footwears.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.dot_unselected);

            // Access resources safely
            int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicatorFootwear.addView(dot);
        }

        // Initially update the first dot
        updateDotIndicatorFootwear(0);
    }

    private void updateDotIndicator(int position) {
        if (dotIndicatorScratch == null) return;

        for (int i = 0; i < dotIndicatorScratch.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorScratch.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void updateDotIndicatorProduct(int position) {
        if (dotIndicatorProduct == null) return;

        for (int i = 0; i < dotIndicatorProduct.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorProduct.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void updateDotIndicatorMovie(int position) {
        if (dotIndicatorMovie == null) return;

        for (int i = 0; i < dotIndicatorMovie.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorMovie.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    private void updateDotIndicatorFootwear(int position) {
        if (dotIndicatorFootwear == null) return;

        for (int i = 0; i < dotIndicatorFootwear.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicatorFootwear.getChildAt(i);
            if (i == position) {
                dot.setImageResource(R.drawable.dot_selected);
            } else {
                dot.setImageResource(R.drawable.dot_unselected);
            }
        }
    }


    private void setupAutoScrolling() {
        handlerScratch = new Handler(Looper.getMainLooper()); // Handler for Slide Models Scratch
        runnableScratch = new Runnable() {
            @Override
            public void run() {
                if (slide_models_Scratch.isEmpty()) return;

                int currentItem = viewPagerScratch.getCurrentItem();
                int nextItem = (currentItem == slide_models_Scratch.size() - 1) ? 0 : currentItem + 1;
                viewPagerScratch.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicator(nextItem); // Update the dot indicator
                handlerScratch.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Scratch
        handlerScratch.postDelayed(runnableScratch, 3000);
    }

    private void setupAutoScrollingProduct() {
        handlerProduct = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableProduct = new Runnable() {
            @Override
            public void run() {
                if (slide_model_products.isEmpty()) return;

                int currentItem = viewPagerProduct.getCurrentItem();
                int nextItem = (currentItem == slide_model_products.size() - 1) ? 0 : currentItem + 1;
                viewPagerProduct.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorProduct(nextItem); // Update the dot indicator
                handlerProduct.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerProduct.postDelayed(runnableProduct, 3000);
    }

    private void setupAutoScrollingMovie() {
        handlerMovie = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableMovie = new Runnable() {
            @Override
            public void run() {
                if (slide_model_movie.isEmpty()) return;

                int currentItem = viewPagerMovie.getCurrentItem();
                int nextItem = (currentItem == slide_model_movie.size() - 1) ? 0 : currentItem + 1;
                viewPagerMovie.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorMovie(nextItem); // Update the dot indicator
                handlerMovie.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerMovie.postDelayed(runnableMovie, 3000);
    }

    private void setupAutoScrollingFootwear() {
        handlerFootwear = new Handler(Looper.getMainLooper()); // Handler for Slide Models Product
        runnableFootwear = new Runnable() {
            @Override
            public void run() {
                if (slide_model_footwears.isEmpty()) return;

                int currentItem = viewPagerFootwear.getCurrentItem();
                int nextItem = (currentItem == slide_model_footwears.size() - 1) ? 0 : currentItem + 1;
                viewPagerFootwear.setCurrentItem(nextItem, true); // Scroll by one position
                updateDotIndicatorFootwear(nextItem); // Update the dot indicator
                handlerFootwear.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };

        // Initialize auto-scrolling for Slide Models Product
        handlerFootwear.postDelayed(runnableFootwear, 3000);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false); // Stop playback
            player.pause();
        }
        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.removeCallbacks(runnableScratch); // Stop auto-scrolling for Slide Models Scratch
        }
        if (handlerProduct != null && runnableProduct != null) {
            handlerProduct.removeCallbacks(runnableProduct); // Stop auto-scrolling for Slide Models Product
        }
        if (handlerMovie != null && runnableMovie != null) {
            handlerMovie.removeCallbacks(runnableMovie); // Stop auto-scrolling for Slide Models Product
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (handlerScratch != null && runnableScratch != null) {
            handlerScratch.postDelayed(runnableScratch, 3000); // Resume auto-scrolling for Slide Models Scratch
        }
        if (handlerProduct != null && runnableProduct != null) {
            handlerProduct.postDelayed(runnableProduct, 3000); // Resume auto-scrolling for Slide Models Product
        }
        if (handlerMovie != null && runnableMovie != null) {
            handlerMovie.postDelayed(runnableMovie, 3000); // Resume auto-scrolling for Slide Models Product
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.release(); // Release resources to avoid memory leaks
            player = null;
        }
    }

//    @Override
//    public void setChipTocategory() {
//        ((Home) getActivity()).chipNotifyTocategory();
//    }
}