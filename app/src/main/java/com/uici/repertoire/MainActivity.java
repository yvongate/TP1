package com.uici.repertoire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uici.repertoire.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactActionListener {

    private ActivityMainBinding binding;
    private ContactManager contactManager;
    private ContactAdapter contactAdapter;
    private List<Contact> contacts;

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_SMS_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactManager = new ContactManager(this);
        contacts = new ArrayList<>();
        loadContacts();

        setupRecyclerView();
        setupFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void setupRecyclerView() {
        contactAdapter = new ContactAdapter(contacts, this);
        binding.recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewContacts.setAdapter(contactAdapter);
    }

    private void setupFab() {
        binding.fabAddContact.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddContactActivity.class));
        });
    }

    private void loadContacts() {
        contacts.clear();
        contacts.addAll(contactManager.getContacts());
        if (contactAdapter != null) {
            contactAdapter.notifyDataSetChanged();
        }
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (contacts.isEmpty()) {
            binding.textViewEmpty.setVisibility(View.VISIBLE);
            binding.recyclerViewContacts.setVisibility(View.GONE);
        } else {
            binding.textViewEmpty.setVisibility(View.GONE);
            binding.recyclerViewContacts.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCallClick(Contact contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION
            );
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contact.getTelephone()));
            startActivity(intent);
        }
    }

    @Override
    public void onSmsClick(Contact contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SMS_PERMISSION
            );
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + contact.getTelephone()));
            startActivity(intent);
        }
    }

    @Override
    public void onLocationClick(Contact contact) {
        if (contact.getLatitude() != 0.0 && contact.getLongitude() != 0.0) {
            String uri = String.format("geo:%f,%f?q=%f,%f(%s)",
                    contact.getLatitude(), contact.getLongitude(),
                    contact.getLatitude(), contact.getLongitude(),
                    contact.getLieuHabitation());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(browserIntent);
            }
        } else {
            Toast.makeText(this, "Localisation non disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(Contact contact) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer le contact")
                .setMessage("Voulez-vous vraiment supprimer " + contact.getNom() + " " + contact.getPrenoms() + " ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    contactManager.deleteContact(contact.getId());
                    loadContacts();
                    Toast.makeText(MainActivity.this, "Contact supprimé", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION || requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
