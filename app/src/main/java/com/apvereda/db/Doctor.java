package com.apvereda.db;

import android.util.Log;

import com.apvereda.utils.DigitalAvatar;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Expression;
import com.couchbase.lite.Meta;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private String id;
    private String name;
    private String specialty;
    private String phone;
    private String email;
    private String address;
    private double projection;

    public Doctor(String name, String specialty, String phone, String email, String id) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
    }

    public Doctor(String name, String specialty, String phone, String email, String address, String id) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Doctor(String name, String specialty, String phone, String email) {
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        address = "Doctor Street, 123";
    }

    public double getProjection() {
        return projection;
    }

    public void setProjection(double projection) {
        this.projection = projection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void createDoctor(Doctor c){
        MutableDocument doctorDoc = new MutableDocument();
        doctorDoc.setString("type", "Doctor");
        doctorDoc.setString("Name", c.getName());
        doctorDoc.setString("Specialty", c.getSpecialty());
        doctorDoc.setString("Email", c.getEmail());
        doctorDoc.setString("Phone", c.getPhone());
        doctorDoc.setString("Address", c.getAddress());

        DigitalAvatar.getDA().saveDoc(doctorDoc);
    }

    public static List<Doctor> getAllDoctors(){
        ArrayList<Doctor> resultList = new ArrayList<Doctor>();
        Query query = QueryBuilder
                .select(SelectResult.expression(Meta.id),
                        SelectResult.property("Name"),
                        SelectResult.property("Specialty"),
                        SelectResult.property("Email"),
                        SelectResult.property("Phone"),
                        SelectResult.property("Address"))
                .from(DigitalAvatar.getDataSource())
                .where(Expression.property("type").equalTo(Expression.string("Doctor")));
        try {
            ResultSet rs = query.execute();
            for (Result result : rs) {
                //Dictionary result = r.getDictionary(0);
                Doctor c = new Doctor(result.getString("Name"), result.getString("Specialty"),
                        result.getString("Phone"), result.getString("Email"),
                        result.getString("Address"), result.getString("id"));
                resultList.add(c);
            }
        } catch (CouchbaseLiteException e) {
            Log.e("CouchbaseError", e.getLocalizedMessage());
        }
        return resultList;
    }

    public static List<Doctor> getDoctorsBySpecialty(String specialty){
        ArrayList<Doctor> resultList = new ArrayList<Doctor>();
        Query query = QueryBuilder
                .select(SelectResult.expression(Meta.id),
                        SelectResult.property("Name"),
                        SelectResult.property("Specialty"),
                        SelectResult.property("Email"),
                        SelectResult.property("Phone"),
                        SelectResult.property("Address"))
                .from(DigitalAvatar.getDataSource())
                .where(Expression.property("type").equalTo(Expression.string("Doctor"))
                        .and(Expression.property("Specialty").equalTo(Expression.string(specialty))));
        try {
            ResultSet rs = query.execute();
            for (Result result : rs) {
                //Dictionary result = r.getDictionary(0);
                Doctor c = new Doctor(result.getString("Name"), result.getString("Specialty"),
                        result.getString("Phone"), result.getString("Email"),
                        result.getString("Address"), result.getString("id"));
                resultList.add(c);
            }
        } catch (CouchbaseLiteException e) {
            Log.e("CouchbaseError", e.getLocalizedMessage());
        }
        return resultList;
    }
}
