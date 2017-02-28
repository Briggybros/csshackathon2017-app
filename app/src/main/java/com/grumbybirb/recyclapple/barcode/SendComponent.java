package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    }

    public class SendComponentTask extends AsyncTask<List<Component>, Void, Void> {
        String barcode;

        public SendComponentTask(String barcode) {
            this.barcode = barcode;
        }

        @Override
        protected Void doInBackground(List<Component>... params) {
            ItemData itemData = new ItemData(params[0]);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String jsonOutput = gson.toJson(itemData);
            Log.d("JSON OUTPUT", "doInBackground: " + jsonOutput);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

}