package com.taraspelypets.clinics.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stacktips.view.CustomCalendarView;
import com.taraspelypets.clinics.R;

/**
 * Created by Taras on 09.08.2017.
 */

public class DatePickerDialogFragment extends DialogFragment {

    private CustomCalendarView mCalendarView;

//    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_fragment_date_picker, null);
        mCalendarView = v.findViewById(R.id.calendar_view);

        builder.setView(v)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatePickerDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.dialog_fragment_date_picker, container, false);
//        mCalendarView = v.findViewById(R.id.calendar_view);
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}
