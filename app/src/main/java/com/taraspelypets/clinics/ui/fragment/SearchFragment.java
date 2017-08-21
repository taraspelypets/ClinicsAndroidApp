package com.taraspelypets.clinics.ui.fragment;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.clinics.App;
import com.taraspelypets.clinics.R;

import com.taraspelypets.clinics.ui.activity.FragmentRequestListener;
import com.taraspelypets.clinics.ui.listadapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements DrawerLayout.DrawerListener, SearchView.OnQueryTextListener, TabLayout.OnTabSelectedListener, View.OnClickListener, ResultListener {
    private static final int REQUEST_CLINICS = 0;
    private static final int REQUEST_DOCTORS = 1;

    private static final String STATE_TAB_INDEX = "STATE_TAB_INDEX";


    private int currentTabIndex = 0;

    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager;
    private NavigationFragment navigationFragment;

    private View mViewLoadingAnim;
    private View mViewNothingFound;


    private ImageButton mButtonMenu;
    private SearchView mSearchView;

    private TabLayout mTabLayout;
    private RecyclerView mRecyclerSearchResults;
    private ArrayList<Object> results = new ArrayList<>();
    private SearchResultAdapter mAdapterClinics;

    private FragmentRequestListener mListener;
    private SearchResultAdapter.OnItemChosen onItemChosen = new SearchResultAdapter.OnItemChosen() {
        @Override
        public void onItemChosen(Object entity) {
            if (entity instanceof DataObject.Clinic) {
                DataObject.Clinic clinic = (DataObject.Clinic) entity;
                if (mListener != null) {
                    mListener.onFragmentRequest(ClinicInfoFragment.newInstance(clinic));
                }
            }
            if (entity instanceof DataObject.Doctor) {
                DataObject.Doctor doctor = (DataObject.Doctor) entity;
                if (mListener != null) {
                    mListener.onFragmentRequest(DoctorInfoFragment.newInstance(doctor));
                }
            }

        }
    };

    public SearchFragment() {
        // Required empty public constructor
    }

    private void initDrawer(View v) {
        mDrawerLayout = v.findViewById(R.id.drawer_layout_menu);
        mDrawerLayout.addDrawerListener(this);
        fragmentManager = getFragmentManager();
        navigationFragment = new NavigationFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.left_drawer, navigationFragment)
                .commit();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClinicLib.getInstance().searchClinics("", 0, 10, REQUEST_CLINICS, this);
        mAdapterClinics = new SearchResultAdapter(results, onItemChosen);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        restoreState(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        initDrawer(v);
        initView(v);
        Log.d("SearhFrag", "onCrVie");
        return v;
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d("SearhFrag", "restoreState");
            currentTabIndex = savedInstanceState.getInt(STATE_TAB_INDEX);
        }

    }

    private void initView(View v) {
        mButtonMenu = v.findViewById(R.id.button_menu);
        mButtonMenu.setOnClickListener(this);

        mSearchView = v.findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(this);


        mTabLayout = v.findViewById(R.id.tabLayout_search_filter);
        mTabLayout.addTab(mTabLayout.newTab().setText("Clinic"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Doctor"));
        TabLayout.Tab tab = mTabLayout.getTabAt(currentTabIndex);
        tab.select();
        mTabLayout.addOnTabSelectedListener(this);


        mRecyclerSearchResults = v.findViewById(R.id.listView_search_results);
        mRecyclerSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerSearchResults.setItemAnimator(new DefaultItemAnimator());
        mRecyclerSearchResults.setAdapter(mAdapterClinics);

        mViewLoadingAnim = v.findViewById(R.id.view_loading_anim);
        mViewLoadingAnim.setVisibility(View.GONE);

        mViewNothingFound = v.findViewById(R.id.view_loading_nothing_found);
        mViewNothingFound.setVisibility(View.GONE);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("SearhFrag", "onSaveinstSt");
        super.onSaveInstanceState(outState);
        try{
            outState.putInt(STATE_TAB_INDEX, mTabLayout.getSelectedTabPosition());
        } catch (NullPointerException e){}

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentRequestListener) {
            mListener = (FragmentRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        currentTabIndex = tab.getPosition();
        Log.d("tabl", "" + tab.getPosition());

        triggerSearch(mSearchView.getQuery().toString());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_menu:
                mDrawerLayout.openDrawer(getView().findViewById(R.id.left_drawer));
                hideKeyboard(getActivity());

                break;
        }
    }

    public void closeMenu() {
        mDrawerLayout.closeDrawer(getView().findViewById(R.id.left_drawer));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        hideKeyboard(getActivity());
        triggerSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            triggerSearch(newText);
        }
        return false;
    }

    private void triggerSearch(String text) {
        switch (currentTabIndex) {
            case 0:
                ClinicLib.getInstance().searchClinics(text, 0, 10, REQUEST_CLINICS, this);
                break;
            case 1:
                ClinicLib.getInstance().searchDoctors(text, 0, 10, REQUEST_DOCTORS, this);
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onStartRequest(int requestId) {
        // TODO show loading animation
        try{
            results.clear();
            mAdapterClinics.notifyDataSetChanged();

            mRecyclerSearchResults.setVisibility(View.GONE);
            mViewNothingFound.setVisibility(View.GONE);
            mViewLoadingAnim.setVisibility(View.VISIBLE);
        }catch (NullPointerException e){


        }




    }

    @Override
    public void onSuccess(int requestId, List<DataObject> data) {
        results.clear();
        results.addAll(data);

        try{
            mViewLoadingAnim.setVisibility(View.GONE);
            if(data.isEmpty()){
                mViewNothingFound.setVisibility(View.VISIBLE);
            }else {
                mRecyclerSearchResults.setVisibility(View.VISIBLE);
            }
        }catch (NullPointerException e){


        }

        //        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),
//                R.animator.hide_loading_animation);
//        set.setTarget(mViewLoadingAnim);
//        set.start();


        mAdapterClinics.notifyDataSetChanged();

    }

    @Override
    public void onFailure(int requestId, DataObject error) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        hideKeyboard(getActivity());
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
