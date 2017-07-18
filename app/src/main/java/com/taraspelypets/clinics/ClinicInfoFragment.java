package com.taraspelypets.clinics;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taraspelypets.clinics.entity.Clinic;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClinicInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicInfoFragment extends Fragment {

    private static final String CLINIC = "param1";

    private Clinic clinic;

    private FragmentRequestListener fragmentRequestListener;

    private TextView mTextViewName;
    private TextView mTextViewDescription;





    public ClinicInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clinic clinic entity.
     * @return A new instance of fragment ClinicInfoFragment.
     */
    public static ClinicInfoFragment newInstance(Clinic clinic) {
        ClinicInfoFragment fragment = new ClinicInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(CLINIC, clinic);

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
        mTextViewName = v.findViewById(R.id.textView_name);
        mTextViewName.setText(clinic.getClinic_name());

        mTextViewDescription = v.findViewById(R.id.textView_description);
        mTextViewDescription.setText(clinic.getDescription());

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

}
