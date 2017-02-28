package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.grumbybirb.recyclapple.R;

import java.util.List;

public class Results extends AppCompatActivity {

    private InstructionsAdapter instructionsAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        Intent openingIntent = getIntent();
        String barcode = openingIntent.getStringExtra("barcode");

        Location location = GetLocation.getLocation(this);

        InstructionPopulator instructionPopulator = new InstructionPopulator(this, instructionsAdapter);
        instructionPopulator.fetchInstructions(barcode, location);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        GetLocation.requestLocPerms(this);

        ListView listView = (ListView) findViewById(R.id.list_view);
        instructionsAdapter = new InstructionsAdapter(this);
        listView.setAdapter(instructionsAdapter);
    }

}
