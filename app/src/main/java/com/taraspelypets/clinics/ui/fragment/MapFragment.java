package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.cliniclib.ResultListener;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.MapItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 07.08.2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, ResultListener, View.OnClickListener{
    private static final String SINGULAR = "Singular";
    private static final String LAT_LNG = "Singular";

    private ImageButton mButtonBack;

    private GoogleMap mGoogleMap;

    private ClusterManager<MapItem> mClusterManager;

    private boolean isSingular;

    public static MapFragment newInstance(DataObject.ClinicLatLng latlng) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putBoolean(SINGULAR, true);
        args.putParcelable(LAT_LNG, latlng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isSingular = getArguments().getBoolean(SINGULAR);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        com.google.android.gms.maps.MapFragment mapFragment = new com.google.android.gms.maps.MapFragment();

        fragmentManager.beginTransaction().replace(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mClusterManager = new ClusterManager<MapItem>(getActivity(), mGoogleMap);
        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        mGoogleMap.setOnMarkerClickListener(mClusterManager);
        if(isSingular){
            MapItem mapItem = getArguments().getParcelable(LAT_LNG);
            mClusterManager.addItem(mapItem);
        } else{
            loadLocations();
        }
    }

    private void loadLocations(){
        ClinicLib.getInstance().getClinicsLocations(0, this);
    }


    private void addItems(List<DataObject> data) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (DataObject ob: data){
            try {
                DataObject.ClinicLatLng clinicLatLng = (DataObject.ClinicLatLng) ob;
                MapItem mapItem = new MapItem(
                        clinicLatLng.lat,
                        clinicLatLng.lng,
                        clinicLatLng.name,
                        "",
                        clinicLatLng.id);
                mClusterManager.addItem(mapItem);
                builder.include(new LatLng(clinicLatLng.lat, clinicLatLng.lng));


            } catch (ClassCastException e){
                e.printStackTrace();
            }
        }

        for (Marker m : mClusterManager.getClusterMarkerCollection().getMarkers()) {
        }
        try {
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mGoogleMap.moveCamera(cu);

        }catch (IllegalStateException e){

        }



    }


    @Override
    public void onStartRequest(int requestId) {

    }

    @Override
    public void onSuccess(int requestId, List<DataObject> data) {
        addItems(data);
    }

    @Override
    public void onFailure(int requestId, DataObject error) {

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
