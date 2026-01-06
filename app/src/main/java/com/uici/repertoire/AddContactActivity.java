package com.uici.repertoire;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uici.repertoire.databinding.ActivityAddContactBinding;

import java.util.UUID;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding binding;
    private ContactManager contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nouveau Contact");
        }

        contactManager = new ContactManager(this);

        binding.buttonSave.setOnClickListener(v -> saveContact());
        binding.buttonCancel.setOnClickListener(v -> finish());
    }

    private void saveContact() {
        String nom = binding.editTextNom.getText().toString().trim();
        String prenoms = binding.editTextPrenoms.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String telephone = binding.editTextTelephone.getText().toString().trim();
        String lieuHabitation = binding.editTextLieuHabitation.getText().toString().trim();
        String lienMap = binding.editTextLienMap.getText().toString().trim();

        if (nom.isEmpty() || prenoms.isEmpty() || telephone.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhoneNumber(telephone)) {
            Toast.makeText(this, "Numéro de téléphone invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.isEmpty() && !isValidEmail(email)) {
            Toast.makeText(this, "Adresse email invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(
                UUID.randomUUID().toString(),
                nom,
                prenoms,
                email,
                telephone,
                lieuHabitation,
                lienMap
        );

        contactManager.addContact(contact);
        Toast.makeText(this, "Contact enregistré", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^[+]?[0-9]{8,15}$");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
