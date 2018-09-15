package com.technologygroup.rayannoor.yoga.Classes;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by EHSAN on 3/1/2018.
 */

public class App extends Application {


    // urls
    public static String apiAddr = "http://varzeshkarplus.ir/api/";
//    public static String apiAddr = "http://yuga.gsharing.ir/api/";
//    public static String apiAddr = "http://172.16.42.68/api/";
//    public static String imgAddr = "http://yuga.gsharing.ir/content/upload/Images/";
    public static String imgAddr = "http://varzeshkarplus.ir/Images/";


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  (ConnectivityManager) App.context.getSystemService(CONNECTIVITY_SERVICE);
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
