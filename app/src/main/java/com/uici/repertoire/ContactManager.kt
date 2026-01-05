package com.uici.repertoire

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("contacts_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveContacts(contacts: List<Contact>) {
        val json = gson.toJson(contacts)
        sharedPreferences.edit().putString("contacts", json).apply()
    }

    fun getContacts(): MutableList<Contact> {
        val json = sharedPreferences.getString("contacts", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Contact>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun addContact(contact: Contact) {
        val contacts = getContacts()
        contacts.add(contact)
        saveContacts(contacts)
    }

    fun deleteContact(contactId: String) {
        val contacts = getContacts()
        contacts.removeAll { it.id == contactId }
        saveContacts(contacts)
    }
}
