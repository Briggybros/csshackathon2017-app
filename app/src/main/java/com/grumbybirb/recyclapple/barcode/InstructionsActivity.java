package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import com.grumbybirb.recyclapple.R;

import java.util.List;

public class InstructionsActivity extends AppCompatActivity {

    private InstructionsAdapter instructionsAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        Intent openingIntent = getIntent();
        String barcode = openingIntent.getStringExtra("barcode");
        String latitude = openingIntent.getStringExtra("latitude");
        String longitude = openingIntent.getStringExtra("longitude");

        InstructionPopulator instructionPopulator = new InstructionPopulator(this, instructionsAdapter);
        instructionPopulator.fetchInstructions(barcode, latitude, longitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        this.setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ListView listView = (ListView) findViewById(R.id.list_view);
        instructionsAdapter = new InstructionsAdapter(this);
        listView.setAdapter(instructionsAdapter);
    }

}
