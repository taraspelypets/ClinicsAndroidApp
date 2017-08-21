package com.taraspelypets.cliniclib.service;

import com.taraspelypets.cliniclib.Listeners;

/**
 * Created by Taras on 07.08.2017.
 */

public interface MapService {
    void loadAllClinicsLocations(int requestId, Listeners listeners);
}
