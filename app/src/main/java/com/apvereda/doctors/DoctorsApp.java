package com.apvereda.doctors;

import android.util.Log;

import com.apvereda.db.Avatar;
import com.apvereda.db.Doctor;
import com.apvereda.db.ReputationOpinion;
import com.apvereda.db.TrustOpinion;
import com.apvereda.uDataTypes.SBoolean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DoctorsApp {
    public static final String EV_CHECKDOCTORTRUST ="TripShare_TripQuery";
    public static final String EV_CHECKDOCTORREPUTATION ="TripShare_TripProposal";

    private static DoctorsApp app;

    public static DoctorsApp getApp(){
        if (app == null){
            app = new DoctorsApp();
        }
        return app;
    }

    private DoctorsApp(){  }

    public void checkDoctorsReputation(String specialty) {
        List<Doctor> doctors = Doctor.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors){
            checkDoctorReputation(doctor.getEmail());
        }
    }

    public List<Doctor> selectDoctor(String specialty) {
        // Compute my own trust
        checkDoctorsTrust(specialty);
        // Search for a valid reputation
        checkDoctorsReputation(specialty);
        // Fusion trust and reputation to obtain my opinion
        List<Doctor> doctors = obtainDoctorsOpinion(specialty);
        // Order by opinion projection
        Collections.sort(doctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                return Double.compare(o2.getProjection(), o1.getProjection());
            }
        });
        /*doctors.sort(new Comparator<Doctor>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                return Double.compare(o1.getProjection(), o2.getProjection());
            }
        });*/
        return doctors;
    }

    private List<Doctor> obtainDoctorsOpinion(String specialty) {
        List<Doctor> doctors = Doctor.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors){
            double projection = obtainDoctorOpinion(doctor.getEmail());
            doctor.setProjection(projection);
        }
        return doctors;
    }

    private double obtainDoctorOpinion(String email) {
        List<SBoolean> opinions = new ArrayList<>();
        List<TrustOpinion> trust = TrustOpinion.getDirectOpinionForTrustee(email);
        if (trust.isEmpty()){
            opinions.add(new SBoolean(0,0,1,0.5));
        } else {
            opinions.add(trust.get(0).getTrust());
        }
        List<ReputationOpinion> reputation = ReputationOpinion.getReputationforReputee(email);
        if (reputation.isEmpty()){
            opinions.add(new SBoolean(0,0,1,0.5));
        } else {
            opinions.add(reputation.get(0).getReputation());
        }
        return SBoolean.weightedBeliefFusion(opinions).projection();
    }

    private void checkDoctorReputation(String email) {
        if (!ReputationOpinion.getReputationforReputee(email).isEmpty()){
            // return reputation
        } else {
            // return SBoolean(0,0,1,0.5)
            // findReputation(email);
        }
    }

    public void checkDoctorsTrust(String specialty) {
        List<Doctor> doctors = Doctor.getDoctorsBySpecialty(specialty);
        for (Doctor doctor : doctors){
            //double projection =
            checkDoctorTrust(doctor.getEmail());
            //doctor.setProjection(projection);
        }
        //return doctors;
    }

    private void checkDoctorTrust(String email) {
        //List<TrustOpinion> direct = TrustOpinion.getDirectOpinionForTrustee(Contact.getContactByEmail(email).getUID());
        List<TrustOpinion> direct = TrustOpinion.getDirectOpinionForTrustee(email);
        if(!direct.isEmpty()){
            Log.i("DigitalAvatars", "Tengo una opinion directa sobre "+email+" con valor "+direct.get(0).getTrust()+" y proyección "+direct.get(0).getTrust().projection());
            //return direct.get(0).getTrust().projection();
            //return (direct.get(0).getTrust().projection() >= 0.5) ? true : false;
        } else {
            //List<TrustOpinion> contactsFunctionalTrust = TrustOpinion.getContactsOpinionForTrustee(Contact.getContactByEmail(email).getUID());
            List<TrustOpinion> contactsFunctionalTrust = TrustOpinion.getContactsOpinionForTrustee(email);
            List<SBoolean> discounts = new ArrayList<>();
            for(TrustOpinion t : contactsFunctionalTrust){
                Log.i("DigitalAvatars", "Tengo opiniones indirectas sobre "+email);
                List<TrustOpinion> aux = TrustOpinion.getReferralOpinionforTrustee(t.getTruster());
                if(!aux.isEmpty()) {
                    Log.i("DigitalAvatars", "Tengo referral sobre "+t.getTruster());
                    SBoolean s = t.getTrust().discount(aux.get(0).getTrust());
                    discounts.add(s);
                }
            }
            if(!discounts.isEmpty()){
                SBoolean s = SBoolean.cumulativeBeliefFusion(discounts);
                Log.i("DigitalAvatars", "Tengo opiniones indirectas sobre "+email+" con valor "+s+" y proyección "+s.projection());
                // return (s.projection() >= 0.5) ? true : false;
                // createTrustOpinion(me, email, s);
                TrustOpinion.createOpinion(new TrustOpinion(Avatar.getAvatar().getUID(), email, "DoctorsApp", s, "123",false));
                //return s.projection();
            } else {
                Log.i("DigitalAvatars", "No hay opiniones sobre "+email);
                //return -1;
            }
        }
    }
}
