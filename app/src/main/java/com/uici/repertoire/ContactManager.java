package com.uici.repertoire;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ContactManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("contacts_prefs", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void saveContacts(List<Contact> contacts) {
        String json = gson.toJson(contacts);
        sharedPreferences.edit().putString("contacts", json).apply();
    }

    public List<Contact> getContacts() {
        String json = sharedPreferences.getString("contacts", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public void addContact(Contact contact) {
        List<Contact> contacts = getContacts();
        contacts.add(contact);
        saveContacts(contacts);
    }

    public void deleteContact(String contactId) {
        List<Contact> contacts = getContacts();
        contacts.removeIf(contact -> contact.getId().equals(contactId));
        saveContacts(contacts);
    }
}
