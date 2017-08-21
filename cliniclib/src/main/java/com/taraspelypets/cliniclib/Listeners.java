package com.taraspelypets.cliniclib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 06.08.2017.
 */

public class Listeners {
    private List<ResultListener> listeners = new ArrayList<>();

    public void addListener(ResultListener listener){
        listeners.add(listener);
    }

    public void callOnStart(int requestId){
        for(ResultListener l: listeners){
            if(l == null){
                break;
            }
            l.onStartRequest(requestId);
        }
    }

    public void callOnSuccess(List<DataObject> list, int requestId){
        for(ResultListener l: listeners){
            if(l == null){
                break;
            }
            l.onSuccess(requestId, list);
        }
    }

    public void callOnFailure(DataObject error, int requestId){
        for(ResultListener l: listeners){
            if(l == null){
                break;
            }
            l.onFailure(requestId, error);
        }
    }
}
