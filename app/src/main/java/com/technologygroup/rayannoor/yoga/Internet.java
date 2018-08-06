package com.technologygroup.rayannoor.yoga;

import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by hesi100 on 4/4/2018.
 */

public class Internet {

    private String mainAddress = "http://www.varzesh.buludweb.com/api/";

    public boolean isInternetOn() {
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
//    public static String connectToServerByJson(String address, String requestMethod, String JsonDATA) {
//        if(isInternetOn()) {
//            String JsonResponse;
//
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            try {
//                URL url = new URL(address);
//                urlConnection = (HttpURLConnection) url.openConnection();
//               // urlConnection.setDoOutput(true);
//                // is output buffer writter
//                urlConnection.setRequestMethod(requestMethod);
//               urlConnection.setDoOutput(true);
//               urlConnection.setRequestProperty("Content-Type", "application/json");
//               urlConnection.setRequestProperty("Accept", "application/json");
////set headers and method
//                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
//                writer.write(JsonDATA);
//                writer.flush();
//// json data
//
//                InputStream inputStream = urlConnection.getInputStream();
////input stream
//                StringBuffer buffer = new StringBuffer();
//                writer.close();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return "fail1";
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String inputLine;
//                while ((inputLine = reader.readLine()) != null)
//                    buffer.append(inputLine);
//                if (buffer.length() == 0) {
//                    // Stream was empty. No point in parsing.
//                    return "fail2";
//                }
//                JsonResponse = buffer.toString();
//
////response data
//                Log.i(TAG, JsonResponse);
//                //send to post execute
//                return JsonResponse;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e(TAG, "Error closing stream", e);
//                    }
//                }
//            }
//            return "fail3";
//        }
//        else
//        {
//            Toast.makeText(app.context, "اینترنت خود را باز کنید", Toast.LENGTH_LONG).show();
//            return "fail";
//        }
//    }



    private String connectToServer(String address, String RequestMethod) {
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(RequestMethod);
            return inputStreamToString(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String connectToServerGet(String address, String requestMethod) {

        String JsonResponse = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));

// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return "input";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return "buffer";
            }
            JsonResponse = buffer.toString();
//response data
            Log.i(TAG, JsonResponse);
            //send to post execute
            return JsonResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;

    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String nextLine;
        try {
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

//    public String userLogin(boolean isInternetAvailable, String user) {
//
//        if (isInternetAvailable) {
//
//            String response = connectToServerByJson(mainAddress + "log_in2.php", "POST");
//
//            Log.i("LOG", response + "");
//
//            if (response != null) {
//                return response;
//            }
//            return null;
//
//        } else
//            return null;
//    }
    public String getReferee(boolean isInternetAvailable,String fid,String cid)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/get?fid="+fid+"&cid="+cid+"&role=Referee";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getGym(boolean isInternetAvailable,String fid,String cid)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/get?fid="+fid+"&cid="+cid+"&role=GYM";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getCoach(boolean isInternetAvailable,String fid,String cid)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/get?fid="+fid+"&cid="+cid+"&role=Coach";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String State(boolean isInternetAvailable)
    {
        if (isInternetAvailable) {
            String address;
            address = "state/get";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getCity(boolean isInternetAvailable,String stateid)
    {
        if (isInternetAvailable) {
            String address;
            address = "City/GetByStateID/"+stateid;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getReshte(boolean isInternetAvailable)
    {
        if (isInternetAvailable) {
            String address;
            address = "Field/get";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String deleteResume(boolean isInternetAvailable,String ResumeId)
    {
        if (isInternetAvailable) {
            String address;
            address = "WorkResume/Delete/"+ResumeId;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String EditResume(boolean isInternetAvailable,String ResumeId,String title,String sdate,String edate)
    {
        if (isInternetAvailable) {
            String address;
            address = "WorkResume/Edit?rid="+ResumeId+"&title="+title+"&startDate="+sdate+"&endDate="+edate ;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String AddResume(boolean isInternetAvailable,String ResumeId,String title,String sdate,String edate)
    {
        if (isInternetAvailable) {
            String address;
            address = "WorkResume/Add?rid="+ResumeId+"&title="+title+"&startDate="+sdate+"&endDate="+edate ;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getResume(boolean isInternetAvailable,String ResumeId)
    {
        if (isInternetAvailable) {
            String address;
            address = "WorkResume/Get/"+ResumeId;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String Signup(boolean isInternetAvailable,String Name,String LastName,String Mobile,String Email,String Password)
    {
        if (isInternetAvailable) {
            String address;
            address = "User/Register?name="+Name +"&lname="+LastName+"&mobile="+Mobile+"&email="+Email+"&password="+Password;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String Login(boolean isInternetAvailable,String Mobile,String Password)
    {
        if (isInternetAvailable) {
            String address;
            address = "User/Login?mobile="+Mobile+"&password=+"+Password;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getDoreCoach(boolean isInternetAvailable,String UserID)
    {
        if (isInternetAvailable) {
            String address;
            address = "User/GetCourses/"+UserID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String Rate(boolean isInternetAvailable,String UserID,String Rate)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/Rate?uid="+UserID+"&rate="+Rate;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String EditBio(boolean isInternetAvailable,String UserID,String BioGraphy,String role)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/editbio?uid="+UserID+"&bio="+BioGraphy+"&role="+role;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String Like(boolean isInternetAvailable,String UserID,String RoleName)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/like?uid?="+UserID+"&role="+RoleName;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getListofuserbycity(boolean isInternetAvailable,String fieldID,String RoleName,String CityID)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/get?fid="+fieldID+"&cid="+CityID+"&role="+RoleName;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getTraining(boolean isInternetAvailable,String trainingID)
    {
        if (isInternetAvailable) {
            String address;
            address = "Training/GetByid/"+trainingID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getTrainingByState(boolean isInternetAvailable,String StateID,String Type)
    {
        if (isInternetAvailable) {
            String address;
            address = "Training/getbystateid?sid="+StateID+"&type="+Type;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getTrainingByUser(boolean isInternetAvailable,String UserID,String Type)
    {
        if (isInternetAvailable) {
            String address;
            address = "training/getbyuserid?uid="+UserID+"&type="+Type;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getUserById(boolean isInternetAvailable,String UserID,String RoleName)
    {
        if (isInternetAvailable) {
            String address;
            address = "user/getuserbyid?uid="+UserID+"&role="+RoleName;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getState(boolean isInternetAvailable)
    {
        if (isInternetAvailable) {
            String address;
            address = "state/get";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getSlider(boolean isInternetAvailable,String FieldID)
    {
        if (isInternetAvailable) {
            String address;
            address = "Slider/GetByFieldID/"+FieldID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getKesho(boolean isInternetAvailable,String KeshoKey)
    {
        if (isInternetAvailable) {
            String address;
            address = "kesho/getbyKey?key="+KeshoKey;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getDoreGym(boolean isInternetAvailable,String GymID)
    {
        if (isInternetAvailable) {
            String address;
            address = "GymTerm/GetByGymID/"+GymID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getListGallery(boolean isInternetAvailable,String UserID)
    {
        if (isInternetAvailable) {
            String address;
            address = "Gallery/GetByUserId/"+UserID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getListFaq(boolean isInternetAvailable)
    {
        if (isInternetAvailable) {
            String address;
            address = "FAQ/Get";
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String DeleteEvidence(boolean isInternetAvailable,String EvidenceID)
    {
        if (isInternetAvailable) {
            String address;
            address = "Evidence/Delete?eid="+EvidenceID;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String EditEvidence(boolean isInternetAvailable,String EvidenceID,String Title)
    {
        if (isInternetAvailable) {
            String address;
            address = "Evidence/Edit?eid="+EvidenceID+"&title="+Title;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String AddEvidence(boolean isInternetAvailable,String UserID,String Title,String Type)
    {
        if (isInternetAvailable) {
            String address;
            address = "Evidence/Add?uid="+UserID+"&title="+Title+"&type="+Type;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
    public String getEvidences(boolean isInternetAvailable,String UserID,String EvidenceType)
    {
        if (isInternetAvailable) {
            String address;
            address = "Evidence/get?uid="+UserID+"&type="+EvidenceType;
            String response = connectToServerGet(mainAddress + address, "POST");

            Log.i("LOG", response + "");

            if (response != null) {
                return response;
            }
            return null;

        } else
            return null;
    }
}
