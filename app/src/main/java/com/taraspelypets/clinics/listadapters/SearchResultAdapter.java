package com.taraspelypets.clinics.listadapters;


import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.entity.BaseEntity;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.entity.Doctor;
import com.taraspelypets.clinics.util.Util;

import java.util.ArrayList;

/**
 * Created by Taras on 14.07.2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {


    ArrayList<BaseEntity> objects;
    OnItemChosen onItemChosen;

    public SearchResultAdapter(ArrayList<BaseEntity> products, OnItemChosen onItemChosen) {
        objects = products;
        this.onItemChosen = onItemChosen;
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BaseEntity entity = (BaseEntity) getItem(position);

        String name = resolveName(entity);
        Bitmap photoBitmap = getPhoto(entity);

        holder.entity = entity;
        holder.textViewName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChosen.onItemChosen(entity);
            }
        });
        if (photoBitmap!=null){
            holder.photo.setImageBitmap(photoBitmap);

        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private Bitmap getPhoto(BaseEntity entity) {
        Bitmap bitmap = null;
        if (entity instanceof Clinic) {
            bitmap = Util.decodeBase64(((Clinic) entity).getPhoto());
        } else if (entity instanceof Doctor) {
            bitmap = Util.decodeBase64(((Doctor) entity).getPhoto());
        }
        return bitmap;
    }

    private String getDescription(BaseEntity entity) {
        String description = "";
        if (entity instanceof Clinic) {
            description = ((Clinic) entity).getDescription();
        } else if (entity instanceof Doctor) {
            description = ((Doctor) entity).getDescription();
        }
        return description;
    }

    private String resolveName(BaseEntity entity) {
        String name = "";
        if (entity instanceof Clinic) {
            name = ((Clinic) entity).getClinic_name();
        } else if (entity instanceof Doctor) {
            Doctor doctor = (Doctor) entity;
            name =
                    doctor.getLastname() + " "
                            + doctor.getFirstname() + " "
                            + doctor.getMiddlename();
        }
        return name;
    }

    public interface OnItemChosen {
        void onItemChosen(BaseEntity entity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView textViewName;
        TextView textViewDescription;
        BaseEntity entity;

        public MyViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.imageView_photo);
            textViewName = view.findViewById(R.id.textView_name);
        }
    }

}
