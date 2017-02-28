package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
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

    public class FetchInstructionsTask extends AsyncTask<String, Void, List<Instruction>> {

        private static final String TAG = "FetchInstructionsTask";

        @Override
        protected void onPostExecute(List<Instruction> instructions) {
            if (instructions != null) {
                mInstructionsAdapter.clear();
                for (Instruction instruction : instructions) {
                    mInstructionsAdapter.add(instruction);
                }
            }
        }

        @Override
        protected List<Instruction> doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            InputStreamReader reader = null;

            List<Instruction> instructions = new ArrayList<>();

            try {
                final String BASE_URL = "http://185.38.149.59:8081";
                final String ENDPOINT = "recyclapple";
                final String BARCODE_KEY = "barcode";
                final String BARCODE_PARAM = params[0];
                final String LAT_KEY = "latitude";
                final String LAT_PARAM = params[1];
                final String LONG_KEY = "longitude";
                final String LONG_PARAM = params[2];

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
                RequestResults res = gson.fromJson(reader, RequestResults.class);
                instructions = res.getResults();

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

            if (!instructions.isEmpty()) {
                return instructions;
            }
            return null;
        }
    }
}
