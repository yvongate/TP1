package com.uici.repertoire

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uici.repertoire.databinding.ActivityAddContactBinding
import java.util.UUID

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var contactManager: ContactManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nouveau Contact"

        contactManager = ContactManager(this)

        binding.buttonSave.setOnClickListener {
            saveContact()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveContact() {
        val nom = binding.editTextNom.text.toString().trim()
        val prenoms = binding.editTextPrenoms.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val telephone = binding.editTextTelephone.text.toString().trim()
        val lieuHabitation = binding.editTextLieuHabitation.text.toString().trim()
        val latitudeStr = binding.editTextLatitude.text.toString().trim()
        val longitudeStr = binding.editTextLongitude.text.toString().trim()

        if (nom.isEmpty() || prenoms.isEmpty() || telephone.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPhoneNumber(telephone)) {
            Toast.makeText(this, "Numéro de téléphone invalide", Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isNotEmpty() && !isValidEmail(email)) {
            Toast.makeText(this, "Adresse email invalide", Toast.LENGTH_SHORT).show()
            return
        }

        val latitude = latitudeStr.toDoubleOrNull() ?: 0.0
        val longitude = longitudeStr.toDoubleOrNull() ?: 0.0

        val contact = Contact(
            id = UUID.randomUUID().toString(),
            nom = nom,
            prenoms = prenoms,
            email = email,
            telephone = telephone,
            lieuHabitation = lieuHabitation,
            latitude = latitude,
            longitude = longitude
        )

        contactManager.addContact(contact)
        Toast.makeText(this, "Contact enregistré", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^[+]?[0-9]{8,15}$"))
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
