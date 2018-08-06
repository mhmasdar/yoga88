package com.technologygroup.rayannoor.yoga;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Timer;

/**
 * Created by mohamadHasan on 22/11/2017.
 */

public class app extends Application {


    public static Context context;
    public static boolean isScheduled = false;
    public static Timer swipeTimer = new Timer();


        @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  (ConnectivityManager) app.context.getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        try {
            if (connec.getNetworkInfo(0).getState() != null)
            {
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    return true;
                }
                else if (
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    return false;
                }
            }
            else
            {
                if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING) {
                    return true;
                }
                else if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING) {
                return true;
            }
            else if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                return false;
            }
        }
        return false;
    }

}
