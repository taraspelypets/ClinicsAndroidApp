package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.taraspelypets.clinics.App;
import com.taraspelypets.clinics.R;

/**
 * Created by Taras on 07.08.2017.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sharedPreferences;

    private ImageButton mButtonBack;
    private EditText mEditTexHostURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mEditTexHostURL = v.findViewById(R.id.editText_host_url);
        sharedPreferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_SETTINGS, 0);
        mEditTexHostURL.setText(sharedPreferences.getString(App.SETTINGS_HOST_URL, ""));
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences.edit()
                .putString(App.SETTINGS_HOST_URL, mEditTexHostURL.getText().toString())
                .apply();
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
