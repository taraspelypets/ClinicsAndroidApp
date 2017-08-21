package com.taraspelypets.clinics.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.taraspelypets.clinics.App;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.fragment.SearchFragment;

public class MainActivity extends Activity implements FragmentRequestListener {


    private FragmentManager fragmentManager;
    private SearchFragment searchFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchFragment = new SearchFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, searchFragment, "search")
                .commit();
    }


    @Override
    public void onFragmentRequest(Fragment fragment) {
        hideKeyboard(this);

        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_fade_in_left,
                        R.animator.fade_out,
                        FragmentTransaction.TRANSIT_NONE,
                        R.animator.slide_fade_out_left)
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCloseMenu() {
        searchFragment.closeMenu();
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

    @Override
    public void onBackPressed() {
        hideKeyboard(this);
        super.onBackPressed();
    }
}
