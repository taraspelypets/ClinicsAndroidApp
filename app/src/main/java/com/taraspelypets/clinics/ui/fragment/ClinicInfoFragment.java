package com.taraspelypets.clinics.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.activity.FragmentRequestListener;
import com.taraspelypets.clinics.ui.listadapter.ContactsListAdapter;
import com.taraspelypets.clinics.util.Util;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClinicInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicInfoFragment extends Fragment implements View.OnClickListener {

    private static final String CLINIC = "param1";
    private static final String CLINIC_NAME = "cname";

    private DataObject.Clinic clinic;

    private FragmentRequestListener fragmentRequestListener;

    private ImageView mImageViewPhoto;

    private ImageButton mButtonBack;

    private TextView mTextViewName;
    private TextView mTextViewDescription;

    private RecyclerView mRecyclerView;
    private ContactsListAdapter mListAdapter;


    public ClinicInfoFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clinic clinic entity.
     * @return A new instance of fragment ClinicInfoFragment.
     */
    public static ClinicInfoFragment newInstance(DataObject.Clinic clinic) {
        ClinicInfoFragment fragment = new ClinicInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(CLINIC, clinic);

        fragment.setArguments(args);
        return fragment;
    }

    public static ClinicInfoFragment newInstance(String clinicNaame) {
        ClinicInfoFragment fragment = new ClinicInfoFragment();
        Bundle args = new Bundle();
        args.putString(CLINIC_NAME, clinicNaame);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clinic = getArguments().getParcelable(CLINIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clinic_info, container, false);

        mImageViewPhoto = v.findViewById(R.id.imageView_photo);
        Bitmap bitmap = Util.decodeBase64(clinic.photo);
        if(bitmap!=null){
            mImageViewPhoto.setImageBitmap(bitmap);
        }

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mTextViewName = v.findViewById(R.id.textView_name);
        mTextViewName.setText(clinic.name);

        mTextViewDescription = v.findViewById(R.id.textView_description);
        mTextViewDescription.setText(clinic.description);

        mListAdapter = new ContactsListAdapter(clinic.contacts, fragmentRequestListener);
        mRecyclerView = v.findViewById(R.id.recyclerView_contacts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mListAdapter);


        return v;
}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentRequestListener) {
            fragmentRequestListener = (FragmentRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentRequestListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
        }

    }
}
