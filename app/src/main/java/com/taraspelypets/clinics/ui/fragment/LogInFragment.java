package com.taraspelypets.clinics.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.clinics.R;

import java.util.List;

/**
 * Created by Taras on 04.08.2017.
 */

public class LogInFragment extends Fragment implements View.OnClickListener{

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;
    private Button mButtonSubmit;
    private ImageButton mButtonBack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);

        mEditTextLogin = v.findViewById(R.id.editText_sign_in_email);
        mEditTextPassword = v.findViewById(R.id.editText_sign_in_password);

        mButtonSubmit = v.findViewById(R.id.button_sign_in);
        mButtonSubmit.setOnClickListener(this);

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        return v;
    }

    private void signIn(){
        Log.d("SignInFrag", "doInBackground");

        String email = mEditTextLogin.getText().toString();
        String password = mEditTextPassword.getText().toString();

        ClinicLib.getInstance().authenticate(email, password, 0, new ResultListener() {
            @Override
            public void onStartRequest(int requestId) {

            }

            @Override
            public void onSuccess(int requestId, List<DataObject> data) {
                try {
                    DataObject.CurrentUser user = (DataObject.CurrentUser) data.get(0);
                    ClinicLib.getInstance().setCurrentUser(user);
                    getActivity().onBackPressed();
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestId, DataObject error) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sign_in:
                hideKeyboard(getActivity());
                signIn();
                break;
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
