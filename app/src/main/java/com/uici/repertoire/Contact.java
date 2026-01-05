package com.uici.repertoire;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String id;
    private String nom;
    private String prenoms;
    private String email;
    private String telephone;
    private String lieuHabitation;
    private double latitude;
    private double longitude;

    public Contact(String id, String nom, String prenoms, String email, String telephone,
                   String lieuHabitation, double latitude, double longitude) {
        this.id = id;
        this.nom = nom;
        this.prenoms = prenoms;
        this.email = email;
        this.telephone = telephone;
        this.lieuHabitation = lieuHabitation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Contact(Parcel in) {
        id = in.readString();
        nom = in.readString();
        prenoms = in.readString();
        email = in.readString();
        telephone = in.readString();
        lieuHabitation = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLieuHabitation() {
        return lieuHabitation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nom);
        dest.writeString(prenoms);
        dest.writeString(email);
        dest.writeString(telephone);
        dest.writeString(lieuHabitation);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
