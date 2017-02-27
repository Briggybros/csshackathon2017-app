package com.grumbybirb.recyclapple.barcode;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Joe on 27/02/2017.
 */

class APIRequests extends AsyncTask<Integer, Void, String> {

    private Exception exception;

    @Override
    protected String doInBackground(Integer ... params) {
        // Do some validation here

        try {
            URL url = new URL(API_URL] + "/" + params[1] + "/" + barcode);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        )
    }

    protected void onPreExecute() {

    }

    protected String doInBackground(int authID, int barcode, String URL) {


    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        Log.i("INFO", response);
    }
}