package com.mynexmy.nex;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CacheManager {

    private Context context;

    // Constructor to initialize context
    public CacheManager(Context context) {
        this.context = context;
    }

    // Method to get the cache file by cache key (video URL hash)
    public File getCacheFile(String cacheKey) {
        // Cache directory for the app
        File cacheDir = context.getCacheDir();
        return new File(cacheDir, cacheKey);
    }

    // Method to cache the video file by downloading it and saving to the local storage
    public void cacheVideo(String videoUrl, String cacheKey) {
        // Download the video asynchronously
        new DownloadVideoTask().execute(videoUrl, cacheKey);
    }

    // AsyncTask to download video in the background
    private class DownloadVideoTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String videoUrl = params[0];
            String cacheKey = params[1];
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            File cacheFile = getCacheFile(cacheKey);

            try {
                // Create the URL connection to the video URL
                URL url = new URL(videoUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                // Open the input stream from the connection
                inputStream = urlConnection.getInputStream();
                fileOutputStream = new FileOutputStream(cacheFile);

                // Read the video file from input stream and write to the cache file
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, length);
                }

                return true; // Success
            } catch (IOException e) {
                Log.e("CacheManager", "Error downloading video: " + e.getMessage());
                return false; // Failure
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    Log.e("CacheManager", "Error closing streams: " + e.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                Log.d("CacheManager", "Video cached successfully.");
            } else {
                Log.d("CacheManager", "Failed to cache the video.");
            }
        }
    }
}

