package com.mynexmy.nex;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BannerResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("video")
        private List<String> video;

        public List<String> getVideo() {
            return video;
        }

        public void setVideo(List<String> video) {
            this.video = video;
        }
    }
}
