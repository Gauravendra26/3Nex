package com.mynexmy.nex.Models;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class RecyclerViewCacheManager {

    private static final String PREFS_NAME = "RecyclerViewCache";
    private static final String KEY_RECYCLER_VIEW_DATA = "recyclerViewData";

    public static void saveRecyclerViewData(Context context, List<HomeCategory_Model> itemList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(itemList);

        editor.putString(KEY_RECYCLER_VIEW_DATA, json);
        editor.apply();
    }

    public static List<HomeCategory_Model> getRecyclerViewData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_RECYCLER_VIEW_DATA, null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<HomeCategory_Model>>(){}.getType();
            return gson.fromJson(json, type);
        } else {
            return null;
        }
    }


}
