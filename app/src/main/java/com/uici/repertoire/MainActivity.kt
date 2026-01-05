package com.uici.repertoire

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uici.repertoire.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactManager: ContactManager
    private lateinit var contactAdapter: ContactAdapter
    private var contacts = mutableListOf<Contact>()

    companion object {
        private const val REQUEST_CALL_PERMISSION = 1
        private const val REQUEST_SMS_PERMISSION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactManager = ContactManager(this)
        loadContacts()

        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(
            contacts,
            onCallClick = { contact -> makeCall(contact) },
            onSmsClick = { contact -> sendSms(contact) },
            onLocationClick = { contact -> showLocation(contact) },
            onDeleteClick = { contact -> deleteContact(contact) }
        )

        binding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }
    }

    private fun setupFab() {
        binding.fabAddContact.setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }
    }

    private fun loadContacts() {
        contacts.clear()
        contacts.addAll(contactManager.getContacts())
        if (::contactAdapter.isInitialized) {
            contactAdapter.notifyDataSetChanged()
        }
        updateEmptyState()
    }

    private fun updateEmptyState() {
        if (contacts.isEmpty()) {
            binding.textViewEmpty.visibility = android.view.View.VISIBLE
            binding.recyclerViewContacts.visibility = android.view.View.GONE
        } else {
            binding.textViewEmpty.visibility = android.view.View.GONE
            binding.recyclerViewContacts.visibility = android.view.View.VISIBLE
        }
    }

    private fun makeCall(contact: Contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PERMISSION
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${contact.telephone}")
            startActivity(intent)
        }
    }

    private fun sendSms(contact: Contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                REQUEST_SMS_PERMISSION
            )
        } else {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:${contact.telephone}")
            startActivity(intent)
        }
    }

    private fun showLocation(contact: Contact) {
        if (contact.latitude != 0.0 && contact.longitude != 0.0) {
            val uri = "geo:${contact.latitude},${contact.longitude}?q=${contact.latitude},${contact.longitude}(${contact.lieuHabitation})"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(browserIntent)
            }
        } else {
            Toast.makeText(this, "Localisation non disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteContact(contact: Contact) {
        AlertDialog.Builder(this)
            .setTitle("Supprimer le contact")
            .setMessage("Voulez-vous vraiment supprimer ${contact.nom} ${contact.prenoms} ?")
            .setPositiveButton("Oui") { _, _ ->
                contactManager.deleteContact(contact.id)
                loadContacts()
                Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Non", null)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL_PERMISSION, REQUEST_SMS_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
