package com.apvereda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apvereda.doctors.DoctorsApp;

public class DoctorsReceiver extends BroadcastReceiver {
    private DoctorsApp app;

    public DoctorsReceiver() {
        super();
        app = DoctorsApp.getApp();
    }

    @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DoctorsApp.EV_CHECKDOCTORTRUST)) {

            }

            if (intent.getAction().equals(DoctorsApp.EV_CHECKDOCTORREPUTATION)) {

            }

            else if (intent.getAction().equals("ReferalTrustRequest")) {

            }

            else if (intent.getAction().equals("ReferalTrustResponse")) {

            }
        }
}
