package com.apvereda.digitalavatars.ui.doctorsapp;

import android.content.Intent;
import android.os.Bundle;

import com.apvereda.db.Avatar;
import com.apvereda.db.Doctor;
import com.apvereda.db.ReputationOpinion;
import com.apvereda.db.TrustOpinion;
import com.apvereda.digitalavatars.R;
import com.apvereda.doctors.DoctorsApp;
import com.apvereda.uDataTypes.SBoolean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DoctorsActivity extends AppCompatActivity {

    private String specialty;
    private DoctorsApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = DoctorsApp.getApp();
        specialty = "Cardiology";
        createDoctorsData();
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

    private void createDoctorsData() {
        String me = Avatar.getAvatar().getUID();
        if(Doctor.getAllDoctors().isEmpty()){
            Doctor doctor = new Doctor("Dr. Liendre", "Cardiology", "123456789", "liendre@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Antonio", "Cardiology", "234567891", "antonio@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Nathalie", "Cardiology", "345678912", "nathalie@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Alejandro", "Dermatology", "456789123", "alejandro@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Carlos", "Dermatology", "567891234", "carlos@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. House", "Neurology", "678912345", "house@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Strange", "Neurology", "789123456", "strange@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Who", "Neurology", "891234567", "who@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Zhivago", "Pneumology", "912345678", "zhivago@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Dolittle", "Pediatrics", "111111111", "dolittle@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Doctor", "Pediatrics", "222222222", "doctor@doctor.com");
            Doctor.createDoctor(doctor);
            doctor = new Doctor("Dr. Grey", "Pediatrics", "333333333", "grey@doctor.com");
            Doctor.createDoctor(doctor);


            TrustOpinion trust;
            ReputationOpinion reputation;
            trust = new TrustOpinion(me, "house@doctor.com", "DoctorsApp", new SBoolean(0,0.7,0.3,0.5), "123", false);
            TrustOpinion.createOpinion(trust);
            reputation = new ReputationOpinion("123", "house@doctor.com", "DoctorsApp", new SBoolean(0,0.9,0.1,0.5));
            ReputationOpinion.createReputation(reputation);

            trust = new TrustOpinion("alguien", "strange@doctor.com", "DoctorsApp", new SBoolean(0.1,0.8,0.1,0.5), "123", false);
            TrustOpinion.createOpinion(trust);
            trust = new TrustOpinion("otroAlguien", "strange@doctor.com", "DoctorsApp", new SBoolean(0.8,0.1,0.1,0.5), "123", false);
            TrustOpinion.createOpinion(trust);
            trust = new TrustOpinion(me, "alguien", "DoctorsApp", new SBoolean(0.9,0,0.1,0.5), "123", true);
            TrustOpinion.createOpinion(trust);
            trust = new TrustOpinion(me,"otroAlguien", "DoctorsApp", new SBoolean(0.6,0.3,0.1,0.5), "123", true);
            TrustOpinion.createOpinion(trust);

            reputation = new ReputationOpinion("123", "who@doctor.com", "DoctorsApp", new SBoolean(0.5,0.5,0,0.5));
            ReputationOpinion.createReputation(reputation);
        }
    }
}