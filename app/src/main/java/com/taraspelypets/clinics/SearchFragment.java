package com.taraspelypets.clinics;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;

import com.taraspelypets.clinics.entity.BaseEntity;
import com.taraspelypets.clinics.entity.Clinic;
import com.taraspelypets.clinics.listadapters.SearchResultAdapter;
import com.taraspelypets.clinics.service.SearchService;
import com.taraspelypets.clinics.service.impl.SearchServiceImpl;
import com.taraspelypets.clinics.service.impl.SearchServiceStub;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements TabLayout.OnTabSelectedListener, SearchService.OnResultListener{
    private static final int REQUEST_CLINICS = 0;
    private static final int REQUEST_DOCTORS = 1;

    private TabLayout mTabLayout;
    private RecyclerView mRecyclerSearchResults;
    private ArrayList<BaseEntity> results = new ArrayList<>();
    private SearchResultAdapter mAdapterClinics;

    private SearchService searchService = new SearchServiceImpl();
    private SearchResultAdapter.OnItemChosen onItemChosen = new SearchResultAdapter.OnItemChosen() {
        @Override
        public void onItemChosen(BaseEntity entity) {
            if ( entity instanceof Clinic){
                Clinic clinic = (Clinic)entity;
                if(mListener != null){
                    mListener.onFragmentRequest(ClinicInfoFragment.newInstance(clinic));
                }
            }

        }
    };

    private FragmentRequestListener mListener;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchService.searchClinicsByName("fjh", 0, 10, this, REQUEST_CLINICS);

        mAdapterClinics = new SearchResultAdapter(results, onItemChosen);
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mTabLayout = v.findViewById(R.id.tabLayout_search_filter);
        mTabLayout.addTab(mTabLayout.newTab().setText("Clinic"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Doctor"));
        mTabLayout.addOnTabSelectedListener(this);

        mRecyclerSearchResults = v.findViewById(R.id.listView_search_results);
        mRecyclerSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerSearchResults.setItemAnimator(new DefaultItemAnimator());
        mRecyclerSearchResults.setAdapter(mAdapterClinics);
        return v;
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
        Log.d("tabl", "" + tab.getPosition());
        switch (tab.getPosition()){
            case 0:
                searchService.searchClinicsByName("fjh", 0, 10, this, REQUEST_CLINICS);
                break;
            case 1:
                searchService.searchDoctorsByName("fjh", 0, 10, this, REQUEST_CLINICS);

                break;
            case 2:
                break;
        }
        mAdapterClinics.notifyDataSetChanged();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onResult(List<BaseEntity> entityList, int requestId) {
        results.clear();

        switch (requestId){
            case REQUEST_CLINICS:
                results.addAll(entityList);
                break;
            case REQUEST_DOCTORS:
                results.addAll(entityList);
                break;

        }
    }
}
