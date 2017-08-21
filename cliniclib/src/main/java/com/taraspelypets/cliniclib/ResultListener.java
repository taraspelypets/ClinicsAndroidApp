package com.taraspelypets.cliniclib;

import java.util.List;

/**
 * Created by Taras on 06.08.2017.
 */

public interface ResultListener {
    void onStartRequest(int requestId);
    void onSuccess(int requestId, List<DataObject> data);
    void onFailure(int requestId, DataObject error);

}
