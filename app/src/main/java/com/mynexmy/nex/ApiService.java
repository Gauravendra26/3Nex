package com.mynexmy.nex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
        @GET("banners")
        Call<BannerResponse> fetchVideo();
}
