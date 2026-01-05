package com.uici.repertoire;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uici.repertoire.databinding.ItemContactBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private OnContactActionListener listener;

    public interface OnContactActionListener {
        void onCallClick(Contact contact);
        void onSmsClick(Contact contact);
        void onLocationClick(Contact contact);
        void onDeleteClick(Contact contact);
    }

    public ContactAdapter(List<Contact> contacts, OnContactActionListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private ItemContactBinding binding;

        public ContactViewHolder(ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Contact contact) {
            binding.textViewName.setText(contact.getNom() + " " + contact.getPrenoms());
            binding.textViewPhone.setText(contact.getTelephone());

            String email = contact.getEmail().isEmpty() ? "Pas d'email" : contact.getEmail();
            binding.textViewEmail.setText(email);

            String location = contact.getLieuHabitation().isEmpty() ?
                    "Pas de localisation" : contact.getLieuHabitation();
            binding.textViewLocation.setText(location);

            binding.imageButtonCall.setOnClickListener(v -> listener.onCallClick(contact));
            binding.imageButtonSms.setOnClickListener(v -> listener.onSmsClick(contact));
            binding.imageButtonLocation.setOnClickListener(v -> listener.onLocationClick(contact));
            binding.imageButtonDelete.setOnClickListener(v -> listener.onDeleteClick(contact));
        }
    }
}
