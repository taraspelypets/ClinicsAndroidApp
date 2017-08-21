package com.taraspelypets.cliniclib.service;

import com.taraspelypets.cliniclib.Listeners;

/**
 * Created by Taras on 04.08.2017.
 */

public interface AuthenticationService {
    void signIn(String login, String password, Listeners listener, int requestId);


}
