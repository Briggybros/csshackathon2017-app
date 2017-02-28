package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.MonthDisplayHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bkazi on 27/02/2017.
 */

public class InstructionPopulator {
    private InstructionsAdapter mInstructionsAdapter;
    private Activity mActivity;

    public InstructionPopulator(Activity mActivity, InstructionsAdapter mInstructionsAdapter) {
        this.mActivity = mActivity;
        this.mInstructionsAdapter = mInstructionsAdapter;
    }

    public void fetchInstructions(String barcode, String latitude, String longitude) {
        FetchInstructionsTask fetchInstructionsTask = new FetchInstructionsTask();
        fetchInstructionsTask.execute(barcode, latitude, longitude);
    }

    public class FetchInstructionsTask extends AsyncTask<String, Void, RequestResults> {

        private static final String TAG = "FetchInstructionsTask";
        private String barcode;
        private String latitude;
        private String longitude;

        @Override
        protected void onPostExecute(RequestResults res) {
            List<Instruction> instructions = res.getResults();
            String error = res.getError();
            if (error.equals("item not registered")) {
                Intent intent = new Intent(mActivity, AddItemActivity.class);
                intent.putExtra("barcode", barcode);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                mActivity.startActivity(intent);
            }
            else if (!instructions.isEmpty()) {
                mInstructionsAdapter.clear();
                for (Instruction instruction: instructions) {
                    mInstructionsAdapter.add(instruction);
                }
            }
        }

        @Override
        protected RequestResults doInBackground(String... params) {
            barcode = params[0];
            latitude = params[1];
            longitude = params[2];
            HttpURLConnection httpURLConnection = null;
            InputStreamReader reader = null;

            RequestResults res;

            try {
                final String BASE_URL = "http://185.38.149.59:8081";
                final String ENDPOINT = "recyclapple";
                final String BARCODE_KEY = "barcode";
                final String BARCODE_PARAM = barcode;
                final String LAT_KEY = "latitude";
                final String LAT_PARAM = latitude;
                final String LONG_KEY = "longitude";
                final String LONG_PARAM = longitude;

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(ENDPOINT)
                        .appendQueryParameter(BARCODE_KEY, BARCODE_PARAM)
                        .appendQueryParameter(LAT_KEY, LAT_PARAM)
                        .appendQueryParameter(LONG_KEY, LONG_PARAM)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.d("URL STRING", url.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) {
                    return null;
                }
                reader = new InputStreamReader(inputStream);

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
//                Type movieListType = new TypeToken<List<Movie>>(){} .getType();
                res = gson.fromJson(reader, RequestResults.class);

            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: Error closing stream", e);
                    }
                }
            }

            return res;
        }
    }
}
