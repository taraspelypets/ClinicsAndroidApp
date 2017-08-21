package com.taraspelypets.clinics.ui.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Button;
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
public class DoctorInfoFragment extends Fragment implements View.OnClickListener {

    private static final String DOCTOR = "param1";

    private DataObject.Doctor doctor;

    private FragmentRequestListener fragmentRequestListener;

    private ImageView mImageViewPhoto;


    private ImageButton mButtonBack;
    private Button mButtonNewAppointment;

    private TextView mTextViewName;
    private TextView mTextViewDescription;

    private RecyclerView mRecyclerView;
    private ContactsListAdapter mListAdapter;


    public DoctorInfoFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param doctor doctor entity.
     * @return A new instance of fragment ClinicInfoFragment.
     */
    public static DoctorInfoFragment newInstance(DataObject.Doctor doctor) {
        DoctorInfoFragment fragment = new DoctorInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(DOCTOR, doctor);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = getArguments().getParcelable(DOCTOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_info, container, false);

        mImageViewPhoto = v.findViewById(R.id.imageView_photo);
        Bitmap bitmap = Util.decodeBase64(doctor.photo);
        if(bitmap!=null){
            mImageViewPhoto.setImageBitmap(bitmap);
        }

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mButtonNewAppointment = v.findViewById(R.id.button_new_appointment);
        mButtonNewAppointment.setOnClickListener(this);

        mTextViewName = v.findViewById(R.id.textView_name);
        mTextViewName.setText(doctor.lastName + " " +
                doctor.firstName + " " +
                doctor.middleName + " ");

        mTextViewDescription = v.findViewById(R.id.textView_description);
        mTextViewDescription.setText(doctor.description);

        mListAdapter = new ContactsListAdapter(doctor.contacts, fragmentRequestListener);
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
            case R.id.button_new_appointment:
                fragmentRequestListener.onFragmentRequest(NewAppointmentFragment.newInstance(doctor));
//                showDialog();
                break;
        }

    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        NewAppointmentFragment newFragment = NewAppointmentFragment.newInstance(doctor);

            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.setCustomAnimations(R.animator.show_fullscreen_dialog, R.animator.fade_out);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();

    }
}
