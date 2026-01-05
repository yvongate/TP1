package com.uici.repertoire

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uici.repertoire.databinding.ItemContactBinding

class ContactAdapter(
    private val contacts: List<Contact>,
    private val onCallClick: (Contact) -> Unit,
    private val onSmsClick: (Contact) -> Unit,
    private val onLocationClick: (Contact) -> Unit,
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.textViewName.text = "${contact.nom} ${contact.prenoms}"
            binding.textViewPhone.text = contact.telephone
            binding.textViewEmail.text = if (contact.email.isNotEmpty()) contact.email else "Pas d'email"
            binding.textViewLocation.text = if (contact.lieuHabitation.isNotEmpty())
                contact.lieuHabitation else "Pas de localisation"

            binding.imageButtonCall.setOnClickListener { onCallClick(contact) }
            binding.imageButtonSms.setOnClickListener { onSmsClick(contact) }
            binding.imageButtonLocation.setOnClickListener { onLocationClick(contact) }
            binding.imageButtonDelete.setOnClickListener { onDeleteClick(contact) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}
