package com.taraspelypets.clinics.ui.listadapter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;

import com.taraspelypets.clinics.util.Util;

import java.util.List;

/**
 * Created by Taras on 14.07.2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {


    List<Object> objects;
    OnItemChosen onItemChosen;

    public SearchResultAdapter(List<Object> products, OnItemChosen onItemChosen) {
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
        final Object entity = (Object) getItem(position);

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

    private Bitmap getPhoto(Object object) {
        Bitmap bitmap = null;
        if (object instanceof DataObject.Clinic) {
            bitmap = Util.decodeBase64(((DataObject.Clinic) object).photo);
        } else if (object instanceof DataObject.Doctor) {
            bitmap = Util.decodeBase64(((DataObject.Doctor) object).photo);
        }
        return getRoundedRectBitmap(bitmap);
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap) {
        try {
            Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
//            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth()/2,
                    bitmap.getHeight()/2, bitmap.getHeight()/2, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return result;
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o){}
        return null;
    }

    private String getDescription(Object object) {
        String description = "";
        if (object instanceof DataObject.Clinic) {
            description = ((DataObject.Clinic) object).description;
        } else if (object instanceof DataObject.Doctor) {
            description = ((DataObject.Doctor) object).description;
        }
        return description;
    }

    private String resolveName(Object object) {
        String name = "";
        if (object instanceof DataObject.Clinic) {
            name = ((DataObject.Clinic) object).name;
        } else if (object instanceof DataObject.Doctor) {
            DataObject.Doctor doctor = (DataObject.Doctor) object;
            name =
                    doctor.lastName + " "
                            + doctor.firstName + " "
                            + doctor.middleName;
        }
        return name;
    }

    public interface OnItemChosen {
        void onItemChosen(Object entity);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView textViewName;
        TextView textViewDescription;
        Object entity;

        public MyViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.imageView_photo);
            textViewName = view.findViewById(R.id.textView_name);
        }
    }

}
