package com.grumbybirb.recyclapple.barcode;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.grumbybirb.recyclapple.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bkazi on 28/02/2017.
 */

public final class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<Component> components = new ArrayList<Component>();
    private Map<String, String> mapVals = new HashMap<String,String>();
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Resources res = getResources();
        String[] keys = res.getStringArray(R.array.materials_array_key);
        String[] values  = res.getStringArray(R.array.materials_array);

        for (int i = 0; i < keys.length; i++) {
            mapVals.put(values[i], keys[i]);
        }

    }

    public void addFields(View view) {
        View v = LayoutInflater.from(this).inflate(R.layout.form_view, null);

        Spinner spinner = (Spinner) v.findViewById(R.id.material_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.materials_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.form_layout);
        layout.addView(v, index++);

    }

    public void submit(View view) {
        LinearLayout yourLinearLayoutView = (LinearLayout) findViewById(R.id.form_layout);

        for(int i=0; i < yourLinearLayoutView.getChildCount(); i++) {
            if (yourLinearLayoutView.getChildAt(i) instanceof LinearLayout) {
                LinearLayout nextLinearLayout = (LinearLayout) yourLinearLayoutView.getChildAt(i);
                AppCompatSpinner spinner = (AppCompatSpinner) nextLinearLayout.getChildAt(1);
                String spinnerText =spinner.getSelectedItem().toString();
                TextInputLayout layoutText = (TextInputLayout) nextLinearLayout.getChildAt(0);
                TextInputEditText editText = (TextInputEditText) ((FrameLayout) layoutText.getChildAt(0)).getChildAt(0);
                Component component = new Component(editText.getText().toString(), mapVals.get(spinnerText));
                components.add(component);

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Component component;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
