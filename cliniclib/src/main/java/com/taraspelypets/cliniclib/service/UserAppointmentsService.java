package com.taraspelypets.cliniclib.service;

import com.taraspelypets.cliniclib.Listeners;

import java.util.List;

/**
 * Created by Taras on 05.08.2017.
 */

public interface UserAppointmentsService {
    void getAppointments(String token, Listeners listeners, int requestId);

}
