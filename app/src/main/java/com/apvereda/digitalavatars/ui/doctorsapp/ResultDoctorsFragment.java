package com.apvereda.digitalavatars.ui.doctorsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.apvereda.db.Doctor;
import com.apvereda.digitalavatars.R;
import com.apvereda.doctors.DoctorsApp;
import com.apvereda.utils.DigitalAvatar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ResultDoctorsFragment extends AppCompatActivity {
    DigitalAvatar da;
    ResultsAdapter adapter;
    ListView list;
    DoctorsApp app;
    String specialty;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        specialty = "Cardiology";
        if (!getIntent().getExtras().isEmpty())
            specialty = getIntent().getExtras().getString("specialty");

        /*CollapsingToolbarLayout layout = root.findViewById(R.id.friend_list_toolbar_layout);
        Toolbar toolbar = root.findViewById(R.id.friend_list_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Digital Avatars", "Intentando abrir el cajon");
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });*/
        da = DigitalAvatar.getDA();
        app = DoctorsApp.getApp();
        List<Doctor> doctors = app.selectDoctor(specialty);
        //List<Doctor> trips = Doctor.getDoctorsBySpecialty(specialty);//app.getTrustedProposals();
        adapter = new ResultsAdapter(this, doctors);
        list = (ListView) findViewById(R.id.listResults);
        list.setAdapter(adapter);

        /*NavController navController1 = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController1.getGraph()).build();
        NavigationUI.setupWithNavController(layout, toolbar, navController1, appBarConfiguration);*/

        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    public void updateDoctors(){
        List<Doctor> doctors = app.selectDoctor(specialty);
        Log.i("Digital Avatar", "Estos son los doctores que pongo en la lista:"+doctors);
        adapter.setData(doctors);
        list.setAdapter(adapter);
    }
}
