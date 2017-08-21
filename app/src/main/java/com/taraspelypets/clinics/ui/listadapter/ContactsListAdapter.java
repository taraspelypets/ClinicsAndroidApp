package com.taraspelypets.clinics.ui.listadapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.activity.FragmentRequestListener;
import com.taraspelypets.clinics.ui.fragment.MapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 02.08.2017.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.MyViewHolder>  {

    private List<Contact> contacts = new ArrayList<>();
    DataObject.Contacts contactsDTO;
    private FragmentRequestListener mListener;


    private class Contact{
        String type;
        String value;

        public Contact(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public ContactsListAdapter(DataObject.Contacts contactsDTO,FragmentRequestListener listener){
        this.contactsDTO = contactsDTO;
        this.mListener = listener;


        if(contactsDTO.phones!=null){
            for (String phone: contactsDTO.phones){
                contacts.add(new Contact("phone", phone));
            }
        }


        if (contactsDTO.email != null){
            contacts.add(new Contact("email", contactsDTO.email));
        }

        if (contactsDTO.address != null){
            contacts.add(new Contact("address", contactsDTO.address));
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Contact contact = contacts.get(position);

        holder.textViewValue.setText(contact.value);

        switch (contact.getType()){
            case "phone":
                holder.imageViewIcon.setImageResource(R.drawable.ic_phone_white_24dp);
                break;
            case "address":
                holder.imageViewIcon.setImageResource(R.drawable.ic_business_white_24dp);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataObject.ClinicLatLng latLng= new DataObject.ClinicLatLng();
                        latLng.lat = contactsDTO.latitude;
                        latLng.lng = contactsDTO.longitude;
                        MapFragment mapFragment = MapFragment.newInstance(latLng);
                        mListener.onFragmentRequest(mapFragment);
                    }
                });
                break;
            case "email":
                holder.imageViewIcon.setImageResource(R.drawable.ic_email_white_24dp);

                break;
        }
    }

    @Override
    public int getItemCount() {
             return contacts.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

         ImageView imageViewIcon;
         TextView textViewValue;

         MyViewHolder(View view) {
             super(view);

             imageViewIcon = view.findViewById(R.id.imageView_icon);
             textViewValue = view.findViewById(R.id.textView_value);
         }
    }
}
