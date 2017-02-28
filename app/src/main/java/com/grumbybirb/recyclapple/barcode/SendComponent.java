package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bkazi on 28/02/2017.
 */

public class SendComponent {
    Activity mActivity;
    List<Component> componentList;

    public SendComponent(Activity mActivity, List<Component> componentList) {
        this.mActivity = mActivity;
        this.componentList = componentList;
    }

    public void sendComponent(String barcode) {
        SendComponentTask sendComponentTask = new SendComponentTask(barcode);
        sendComponentTask.execute(componentList);
    }

    public class SendComponentTask extends AsyncTask<List<Component>, Void, Boolean> {
        String barcode;

        public SendComponentTask(String barcode) {
            this.barcode = barcode;
        }

        @Override
        protected Boolean doInBackground(List<Component>... params) {
            final String TAG = "Do in background POST";

            HttpURLConnection httpURLConnection = null;
            OutputStreamWriter writer = null;
            InputStreamReader reader = null;

            Boolean result = false;

            try {
                ItemData itemData = new ItemData(params[0]);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String jsonOutput = gson.toJson(itemData);

                final String BASE_URL = "http://185.38.149.59:8081";
                final String ENDPOINT = "recyclapple";
                final String BARCODE_KEY = "barcode";
                final String BARCODE_PARAM = barcode;

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(ENDPOINT)
                        .appendQueryParameter(BARCODE_KEY, BARCODE_PARAM)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.d("URL STRING", url.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty( "Content-Type", "application/json" );
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                writer = new OutputStreamWriter(outputStream, "UTF-8");
                writer.write(jsonOutput);
                writer.flush();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) {
                    return null;
                }
                reader = new InputStreamReader(inputStream);
                RequestResults res = gson.fromJson(reader, RequestResults.class);

                result = res.getError().length() == 0;
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: Error closing stream", e);
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "doInBackground: Error closing stream", e);
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if (b == Boolean.TRUE) {
                Intent intent = new Intent(mActivity, BarcodeCaptureActivity.class);
                intent.putExtra("success", b);
                mActivity.startActivity(intent);
            } else {
                Toast toast = Toast.makeText(mActivity, "Item failed to add", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}