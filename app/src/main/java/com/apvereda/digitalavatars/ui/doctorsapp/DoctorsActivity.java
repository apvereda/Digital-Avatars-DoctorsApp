package com.apvereda.digitalavatars.ui.doctorsapp;

import android.content.Intent;
import android.os.Bundle;

import com.apvereda.digitalavatars.R;
import com.apvereda.doctors.DoctorsApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DoctorsActivity extends AppCompatActivity {

    private String specialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DoctorsApp.getApp(null);
        specialty = "Cardiology";
        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"Cardiology", "Dermatology", "Neurology", "Pneumology", "Pediatrics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                specialty = adapterView.getItemAtPosition(i).toString();
                /*switch (adapterView.getItemAtPosition(i).toString()){
                    case "Cardiology" :

                        break;
                    case "Dermatology" :

                        break;
                    case "Neurology" :

                        break;
                    case "Pneumology" :

                        break;
                    case "Pediatrics" :

                        break;
                }*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fabresult = findViewById(R.id.fabresult);
        fabresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ResultDoctorsFragment.class);
                i.putExtra("specialty", specialty);
                startActivity(i);
            }
        });
    }
}