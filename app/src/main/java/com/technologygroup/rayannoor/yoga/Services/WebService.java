package com.technologygroup.rayannoor.yoga.Services;

import android.util.Log;

import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ChartModel;
import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.Models.CoachEduModel;
import com.technologygroup.rayannoor.yoga.Models.CoachGymsModel;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.CoachResumeModel;
import com.technologygroup.rayannoor.yoga.Models.CommentModel;
import com.technologygroup.rayannoor.yoga.Models.CourseModel;
import com.technologygroup.rayannoor.yoga.Models.FAQmodel;
import com.technologygroup.rayannoor.yoga.Models.GalleryModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.Models.GymModel;
import com.technologygroup.rayannoor.yoga.Models.MainPageModel;
import com.technologygroup.rayannoor.yoga.Models.TeachTextImage;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.Models.UserModel;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.Models.idname;
import com.technologygroup.rayannoor.yoga.Models.navigationMenuModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by EHSAN on 3/1/2018.
 */

public class WebService {

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

    private String connectToServerByJson(String address, String requestMethod, String JsonDATA) {

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
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
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


    //android upload file to server
    public int uploadFile(boolean isInternetAvailable, String selectedFilePath, String fileName) {

        if (isInternetAvailable) {

            int serverResponseCode = 0;

            HttpURLConnection connection;
            DataOutputStream dataOutputStream;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File selectedFile = new File(selectedFilePath);


            String[] parts = selectedFilePath.split("/");
            //final String fileName = parts[parts.length - 1];

            selectedFilePath = "";

            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    selectedFilePath += fileName;
                } else {

                    selectedFilePath += parts[i];
                    selectedFilePath += "/";
                }
            }


            if (!selectedFile.isFile()) {
                //dialog.dismiss();

                // Toast.makeText(g, "فایل یافت نشد", Toast.LENGTH_LONG).show();

                return -1;

            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
                    URL url = new URL(App.apiAddr + "Upload/UploadFiles");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);//Allow Inputs
                    connection.setDoOutput(true);//Allow Outputs
                    connection.setUseCaches(false);//Don't use a cached Copy
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    connection.setRequestProperty("uploaded_file", selectedFilePath);

                    //creating new dataoutputstream
                    dataOutputStream = new DataOutputStream(connection.getOutputStream());

                    //writing bytes to data outputstream
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + selectedFilePath + "\"" + lineEnd);

                    dataOutputStream.writeBytes(lineEnd);

                    //returns no. of bytes present in fileInputStream
                    bytesAvailable = fileInputStream.available();
                    //selecting the buffer size as minimum of available bytes or 1 MB
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    //setting the buffer as byte array of size of bufferSize
                    buffer = new byte[bufferSize];

                    //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                    while (bytesRead > 0) {
                        //write the bytes read from inputstream
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    serverResponseCode = connection.getResponseCode();
                    String serverResponseMessage = connection.getResponseMessage();

                    Log.i(GifHeaderParser.TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                    //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    return 1;
//                }

                    //closing the input and output streams
                    fileInputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return -2;

                    //Toast.makeText(getContext(), "مشکل در آپلود فایل", Toast.LENGTH_SHORT).show();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //Toast.makeText(getContext(), "مشکل در اتصال برای آپلود", Toast.LENGTH_SHORT).show();
                    return -3;
                } catch (IOException e) {
                    e.printStackTrace();
                    return -4;
                    //Toast.makeText(getContext(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
                }

                return serverResponseCode;
            }
        }
        return -5;
    }


    public UserModel userLogin(boolean isInternetAvailable, String userName, String pass) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "User/Login?mobile=" + Long.parseLong(userName) + "&password=" + pass, "GET");

            Log.i("LOG", response + "");

            if (response != null) {

                UserModel model = new UserModel();

                if (response.equals("-1")) {

                    model.id = -1;
                    return model;

                } else {


                    try {

                        JSONObject Object = new JSONObject(response);
                        model.Email = Object.getString("Email");
                        model.Name = Object.getString("FirstName");
                        model.Password = Object.getString("Password");
                        model.lName = Object.getString("LastName");
                        model.id = Object.getInt("ID");
                        model.Mobile = Object.getString("Mobile");
//                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.userType=Object.getJSONArray("UserRoles");
                        JSONObject Cityj=Object.getJSONObject("City");
                        model.cityid=Cityj.getInt("ID");
                        model.cityName=Cityj.getString("Name");
                        model.state=Cityj.getString("State");
                        model.SessionKey=Object.getInt("SessionKey");
                        JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                        model.ProfileImageName=ProfileImagej.getString("Name");
                        model.IDProfileImage=ProfileImagej.getInt("ID");

                        return model;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;

        } else
            return null;
    }

    public String userRegister(boolean isInternetAvailable, UserModel model) {

        if (isInternetAvailable) {

//            String req = "{\"idCity\":-1,\"Name\":\"" + model.Name + "\",\"lName\":\"" + model.lName + "\",\"Mobile\":\"" + model.Mobile + "\",\"Email\":\"" + model.Email + "\",\"Password\":\"" + model.Password + "\",\"Type\":3,\"isVisible\":true,\"lastUpdate\":1}";
//            String response = connectToServerByJson(App.apiAddr + "User/add", "POST", req);
            String username;
            username=model.Name.replace(" ", "%20");
            String userlname;
            userlname=model.lName.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "User/Register?name=" + username + "&lname=" +userlname + "&mobile=" + model.Mobile + "&email=" + model.Email + "&password=" + model.Password, "GET");
            Log.i("LOG", response + "");
            return response;
        } else
            return null;
    }


    public List<idname> getStates(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "state/get", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<idname> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        idname model = new idname();

                        model.id = Object.getInt("ID");
                        model.name = Object.getString("Name");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<idname> getCiteies(boolean isInternetAvailable, int idState) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "City/GetByStateID/" + idState, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<idname> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        idname model = new idname();

                        model.id = Object.getInt("ID");
                        model.name = Object.getString("Name");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<idname> getFields(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Field/get", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<idname> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        idname model = new idname();

                        model.id = Object.getInt("ID");
                        model.name = Object.getString("Name");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public CoachModel getCoachInfo(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/getuserrolebyid/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                CoachModel model = new CoachModel();

                try {


                    JSONObject Object = new JSONObject(response);
                    model.Email = Object.getString("Email");
                    model.id = Object.getInt("ID");
                    model.fName = Object.getString("fName");
                    model.RegisteredDate = Object.getString("RegisteredDate");
                    model.Instagram = Object.getString("Instagram");
                    model.IsVerified = Object.getBoolean("IsVerified");
                    model.like = Object.getInt("like");
                    model.lName = Object.getString("lName");
                    model.Mobile = Object.getString("Mobile");
                    model.Rate = Object.getDouble("Rate");
                    model.Telegram = Object.getString("Telegram");
                    model.sorosh = Object.getString("sorosh");
                    JSONObject Rolej=Object.getJSONObject("UserRoles");
                    model.rolename=Rolej.getString("Name");
                    model.roleid=Rolej.getInt("ID");
                    JSONObject cityj=Object.getJSONObject("City");
                    model.City = cityj.getString("Name");
                    model.idCity = cityj.getInt("ID");
                    model.State = cityj.getString("State");
                    JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                    model.IdImg=ProfileImagej.getInt("ID");
                    model.ImgName=ProfileImagej.getString("Name");

                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public String getPanelInfo(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/getuserrolebyid/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                return response;
            }
            return null;

        } else
            return null;
    }

    public List<CoachResumeModel> getCoachResume(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "WorkResume/Get/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachResumeModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachResumeModel model = new CoachResumeModel();
                        model.startDate=Object.getString("StartDate");
                        model.endDate=Object.getString("EndDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.id = Object.getInt("ID");
                        model.Title = Object.getString("Title");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String AddCoachResume(boolean isInternetAvailable, CoachResumeModel model) {

        if (isInternetAvailable) {

            String endDate;

            if (model.endDate.equals(""))
                endDate = "";
            else
                endDate = model.endDate.substring(0, 4);

//            String req = "{\"id\":-1,\"idCoach\":" + model.idCoach + ",\"Title\":\"" + model.Title + "\",\"startDate\":" + model.startDate.substring(0, 4) + ",\"endDate\":" + endDate + ",\"lastUpdate\":0}";
//            String response = connectToServerByJson(App.apiAddr + "Resume/add", "POST", req);
            String mytitle;
            mytitle=model.Title.replace(" ", "%20");
            Log.i("LOG", App.apiAddr + "WorkResume/Add?uid=" + model.idCoach + "&title=" + mytitle + "&startDate=" + model.startDate + "&endDate=" + endDate);
            String response = connectToServer(App.apiAddr + "WorkResume/Add?uid=" + model.idCoach + "&title=" + mytitle + "&startDate=" + model.startDate + "&endDate=" + model.endDate, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String editCoachResume(boolean isInternetAvailable, CoachResumeModel model) {

        if (isInternetAvailable) {
//            String req = "{\"id\":" + model.id + ",\"idCoach\":" + model.idCoach + ",\"Title\":\"" + model.Title + "\",\"startDate\":" + model.startDate.substring(0, 4) + ",\"endDate\":" + endDate + ",\"lastUpdate\":0}";
//            String response = connectToServerByJson(App.apiAddr + "Resume/update", "POST", req);
            String mytitle;
            mytitle=model.Title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "WorkResume/Edit?rid=" + model.id + "&title=" + mytitle + "&startDate=" + model.startDate + "&endDate=" + model.endDate, "GET");
            Log.i("LOG", response + "");
            return response;
        } else
            return null;
    }

    public String deleteCoachResume(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "WorkResume/Delete/"+id, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String deletegymnotif(boolean isInternetAvailable, int id) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("ID",id);
        if (isInternetAvailable) {

            String response = connectToServerByJson(App.apiAddr + "Post/DeletePost" , "POST",jsonObject.toString());
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String deleteImage(boolean isInternetAvailable, int id) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("ID",id);
        if (isInternetAvailable) {

            String response = connectToServerByJson(App.apiAddr + "upload/DeleteFile" , "POST",jsonObject.toString());
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String editCoachBio(boolean isInternetAvailable, String bio, int id,String type) throws JSONException {

        if (isInternetAvailable) {
           JSONObject bioj=new JSONObject();
           bioj.put("ID",id);
           bioj.put("Bio",bio);
            String response = connectToServerByJson(App.apiAddr + "user/editbio", "POST",bioj.toString());
            Log.i("LOG", response + "");
            return response;
        } else
            return null;
    }

    public List<CoachCourseModel> getCoachCourses(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {
            Log.i("LOG","Course/GetCoursesByUserRoleID/"+id);
            String response = connectToServer(App.apiAddr + "Course/GetCoursesByUserRoleID/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachCourseModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachCourseModel model = new CoachCourseModel();
                        model.id = Object.getInt("ID");
                        model.title = Object.getString("Name");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<TeachTextImage> getMoves(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Training/GetById/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<TeachTextImage> list = new ArrayList<>();

                try {

                    JSONObject Arrey = new JSONObject(response);
                    JSONArray ArreyBodies = Arrey.getJSONArray("Bodies");
                    for (int i = 0; i < ArreyBodies.length(); i++) {
                        JSONObject Object = ArreyBodies.getJSONObject(i);
                        TeachTextImage model = new TeachTextImage();
                        model.Text = Object.getString("Body");
                        model.Title = Arrey.getString("Title");
                        JSONArray imagej=Object.getJSONArray("Images");
                        try {
                            JSONObject im=imagej.getJSONObject(0);
                            model.Image=im.getString("Name");
                        }
                        catch (JSONException e)
                        {
                            model.Image="";
                        }

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }








    // below codes are not edited


    public String editUserInfo(boolean isInternetAvailable, UserModel model) {

        if (isInternetAvailable) {

            String req = "{\"id\":" + model.id + ",\"idCity\":"  + ",\"Name\":\"" + model.Name + "\",\"lName\":\"" + model.lName + "\",\"Mobile\":\"" + model.Mobile + "\",\"Email\":\"" + model.Email + "\",\"Password\":\"" + model.Password + "\",\"Type\":3,\"isVisible\":true,\"lastUpdate\":1}";
            String response = connectToServerByJson(App.apiAddr + "User/update", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }


    public String editCoachInfo(boolean isInternetAvailable, CoachModel model) {

        if (isInternetAvailable) {

            String req = "{\"id\":" + model.id + ",\"idCity\":" + -1 + ",\"idCurrentPlan\":" + -1 + ",\"fName\":\"" + model.fName + "\",\"lName\":\"" + model.lName + "\",\"natCode\":" + model.natCode + ",\"like\":" + -1 + ",\"Rate\":" + -1 + ",\"rateCount\":" + -1 + ",\"Mobile\":" + model.Mobile + ",\"Telegram\": \"" + model.Telegram + "\",\"Email\": \"" + model.Email + "\",\"Instagram\": \"" + model.Instagram + "\",\"lastUpdate\":0,\"image\":\"" + model.ImgName + "\"}";
            String response = connectToServerByJson(App.apiAddr + "coach/update", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public List<CoachEduModel> getCoachEdu(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Evidence/get?urid="+id+"&type=educational", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachEduModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachEduModel model = new CoachEduModel();

                        model.id = Object.getInt("ID");
//                        model.Date = Object.getString("Date");
                        model.Title = Object.getString("Title");
                        JSONObject imagej=Object.getJSONObject("Image");
                        model.ImgName = imagej.getString("Name");
                        model.ImgID = imagej.getInt("ID");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<CoachHonorModel> getCoachHonor(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Evidence/get?urid="+id+"&type=evidence", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachHonorModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachHonorModel model = new CoachHonorModel();

                        model.id = Object.getInt("ID");
//                        model.evidencetypeid = Object.getInt("EvidenceTypeId");
//                        model.UserRoleId = Object.getInt("UserRoleId");
//                        model.Date = Object.getString("Date");
                        model.Title = Object.getString("Title");
                        JSONObject imagej=Object.getJSONObject("Image");
                        model.ImgName = imagej.getString("Name");
                        model.ImgID =imagej.getInt("ID");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<TeachesModel> getTeaches(boolean isInternetAvailable, int fid,int sid,String type) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Training/getbystateid?fid="+fid+"&sid="+sid+"&type="+type, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<TeachesModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        TeachesModel model = new TeachesModel();

                        model.id = Object.getInt("ID");
                        model.Date = Object.getString("PublishDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.IsUpdated=Object.getBoolean("IsUpdated");
                        model.user.getfromjson(Object.getJSONObject("User"));
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Bodies");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<TeachesModel> getTeachesArticle(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + " /Article/Get", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<TeachesModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        TeachesModel model = new TeachesModel();

                        model.id = Object.getInt("ID");
                        model.Date = Object.getString("PublishDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.IsUpdated=Object.getBoolean("IsUpdated");
                        model.user.getfromjson(Object.getJSONObject("User"));
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Bodies");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<TeachesModel> getTeachesByuser(boolean isInternetAvailable, int fid,int id,String type) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr+"training/getbyuserid?fid="+fid+"&uid="+id+"&type="+type, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<TeachesModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        TeachesModel model = new TeachesModel();

                        model.id = Object.getInt("ID");
                        model.Date = Object.getString("PublishDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.IsUpdated=Object.getBoolean("IsUpdated");
                        model.user.getfromjson(Object.getJSONObject("User"));
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Bodies");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;


        } else
            return null;
    }
    public List<TeachesModel> getTeachesOfown(boolean isInternetAvailable,int fid,int id,String type) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr+"training/GetByUserRoleId?fid="+fid+"&urid="+id+"&type="+type, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<TeachesModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        TeachesModel model = new TeachesModel();

                        model.id = Object.getInt("ID");
                        model.Date = Object.getString("PublishDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.IsUpdated=Object.getBoolean("IsUpdated");
                        model.user.getfromjson(Object.getJSONObject("User"));
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Bodies");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<CoachGymsModel> getCoachGyms(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {
            Log.i("LOG", App.apiAddr + "user/GetCoachGyms/" + id);
            String response = connectToServer(App.apiAddr + "user/GetCoachGyms/" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachGymsModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachGymsModel model = new CoachGymsModel();
                        model.id = Object.getInt("ID");
                        model.Img = Object.getJSONObject("ProfileImage").getString("Name");
                        model.Name = Object.getString("Name");
                        model.like=Object.getInt("Likes");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<CommentModel> getComments(boolean isInternetAvailable, boolean isGym, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "comment/select?isgym=" + isGym + "&idrow=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CommentModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CommentModel model = new CommentModel();

                        model.id = Object.getInt("id");
                        model.name = Object.getString("name");
                        model.body = Object.getString("body");
                        model.date = Object.getInt("date");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public String AddCoachEdu(boolean isInternetAvailable,int id ,CoachEduModel model) {

        if (isInternetAvailable) {

            String test;
            test=model.Title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Evidence/Add?urid="+id+"&title="+test+"&type=educational", "GET");
            return response;
        } else
            return "ok";
    }

    public String AddCoachHonor(boolean isInternetAvailable, CoachHonorModel model,int id) {

        if (isInternetAvailable) {

            String test;
            test=model.Title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Evidence/Add?urid="+id+"&title="+test+"&type=evidence ", "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String AddComment(boolean isInternetAvailable, CommentModel model) {

        if (isInternetAvailable) {


            String req = "{\"id\":-1,\"idRow\":" + model.idRow + ",\"idUser\":" + model.idUser + ",\"Body\":\"" + model.body + "\",\"Date\":" + model.date + ",\"isGym\":" + model.isGym + ",\"isActive\":true,\"lastUpdets\":1,\"isRead\":false}";
            String response = connectToServerByJson(App.apiAddr + "comment/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String AddTeaches(boolean isInternetAvailable, JSONObject j) {

        if (isInternetAvailable) {

            Log.i("LOG", j.toString() + "");
            String response = connectToServerByJson(App.apiAddr + "Training/addTraining", "POST", j.toString());
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }


    public String editCoachEdu(boolean isInternetAvailable, CoachEduModel model) {

        if (isInternetAvailable) {

            String mytitle;
            mytitle=model.Title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Evidence/Edit?eid="+model.id+"&title="+mytitle,"GET");
            return response;
        } else
            return null;
    }
    public String editCoachCourse(boolean isInternetAvailable, CoachCourseModel model) {

        if (isInternetAvailable) {

            String test;
            test=model.title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Course/EditCourse?cid="+model.id+"&title="+test, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String AddCoachCourse(boolean isInternetAvailable, String title,int id) {

        if (isInternetAvailable) {

            //  String req = "{\"id\":" + model.id + ",\"idCoach\":" + model.idCoach + ",\"Name\":\"" + model.Name + "\",\"image\":\"" + model.Img + "\",\"gettingPlace\":\"" + model.gettingPlace + "\",\"lastUpdate\":0,\"date\":" + model.Date.substring(0, 4) + "}";
            String mytitle;
            mytitle=title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Course/AddCourse?urid="+id+"&title="+mytitle, "GET");
            Log.i("LOG", response + " ");

            return response;
        } else
            return null;
    }
    public String editCoachHonor(boolean isInternetAvailable, CoachHonorModel model) {

        if (isInternetAvailable) {
            //String req = "{\"Date\":" + model.Date.substring(0, 4) + ",\"Des\":\"" + model.Des + "\",\"id\":" + model.id + ",\"idRow\":" + ",\"isGym\":false,\"lastUpdate\":0,\"Title\":\"" + model.Title + "\",\"Image\":\""  + "\",\"Name\":\"" + model.Name + "\"}";
            String mytitle;
            mytitle=model.Title.replace(" ", "%20");
            String response = connectToServer(App.apiAddr + "Evidence/Edit?eid="+model.id+"&title="+mytitle,"GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String editTeaches(boolean isInternetAvailable, TeachesModel model) {

        if (isInternetAvailable) {

            String req = " {\"id\":" + model.id + ",\"idRow\":"  + ",\"Title\":\"" + model.Title + "\",\"Body\":\"" + model.Body + "\",\"Images\":\"" + model.Images + "\",\"Date\":" + model.Date + ",\"isVisible\":true,\"isGym\":false,\"lastUpdate\":1}";
            String response = connectToServerByJson(App.apiAddr + "Training/update", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }


    public String deleteCoachEdu(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Evidence/Delete?eid="+id, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String deleteCoachCourse(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Course/DeleteCourse?cid="+id, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String deleteCoachHhonor(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Evidence/Delete?eid="+id, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String deleteTeaches(boolean isInternetAvailable, int id)  {

        if (isInternetAvailable) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("ID",id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Training/delete", "POST", jsonObject.toString());
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String deleteImgDetails(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "images/delete?id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                return response;

            }
            return null;
        } else
            return null;
    }


    public String postLike(boolean isInternetAvailable, int idCoachOrGym) {

        if (isInternetAvailable) {


            String response = connectToServer(App.apiAddr + "user/like/"+idCoachOrGym, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }
    public String postdisLike(boolean isInternetAvailable, int idCoachOrGym) {

        if (isInternetAvailable) {


            String response = connectToServer(App.apiAddr + "user/dislike/"+idCoachOrGym, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String postRate(boolean isInternetAvailable, int idCoachOrGym, String type, float rate) {

        if (isInternetAvailable) {
            //user/Rate?uid=4&rate=4.5&role=coach
            String response = connectToServer(App.apiAddr + "user/Rate?urid="+idCoachOrGym+"&rate="+rate+"&role="+type, "GET");
            Log.i("LOG", response + "");
            return response;
        } else
            return null;
    }

    public String postPlanId(boolean isInternetAvailable, int idPlan, int idUser) {

        if (isInternetAvailable) {

            String req = "{\"idUser\":" + idUser + ",\"idPlan\":" + idPlan + "}";
            String response = connectToServerByJson(App.apiAddr + "payment/pay", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public List<CoachModel> getCoachesByField(boolean isInternetAvailable, int fid,int cid) {

        if (isInternetAvailable) {
            Log.i("LOG", App.apiAddr+"user/get?fid="+fid+"&cid="+cid+"&rid=6");
            String response = connectToServer(App.apiAddr+"user/get?fid="+fid+"&cid="+cid+"&rid=6" , "GET");

            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachModel> list = new ArrayList<>();

                try {

                    JSONArray Array = new JSONArray(response);

                    for (int i = 0; i < Array.length(); i++) {

                        JSONObject Object = Array.getJSONObject(i);
                        CoachModel model = new CoachModel();
//                        model.Email = Object.getString("Email");
                        model.id = Object.getInt("ID");
                        model.fName = Object.getString("FirstName");
//                        model.RegisteredDate = Object.getString("RegisteredDate");
//                        model.Instagram = Object.getString("Instagram");
                        model.IsVerified = Object.getBoolean("IsVerified");
                        model.like = Object.getInt("Likes");
                        // model.like = Object.getInt("like");
                        model.lName = Object.getString("LastName");
//                        model.Mobile = Object.getString("Mobile");
                        // model.Rate = Object.getDouble("Rate");
                        // model.Telegram = Object.getString("Telegram");
                        //model.sorosh = Object.getString("sorosh");
//                        JSONObject Rolej=Object.getJSONObject("Role");
                        //                      model.rolename=Rolej.getString("Name");
                        //                    model.roleid=Rolej.getInt("ID");
                        //                  JSONObject cityj=Object.getJSONObject("City");
                        ///                     model.City = cityj.getString("Name");
                        ///                  model.idCity = cityj.getInt("ID");
                        //               model.State = cityj.getString("State");
                        JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                        model.IdImg=ProfileImagej.getInt("ID");
                        model.ImgName=ProfileImagej.getString("Name");
                        list.add(model);
                    }

                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }
    public List<ZanguleModel> getCoachKaryabi(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {
            Log.i("LOG", App.apiAddr+"Post/GetKaryabi/"+id);
            String response = connectToServer(App.apiAddr+"Post/GetKaryabi/"+id , "GET");

            Log.i("LOG", response + "");

            if (response != null) {

                List<ZanguleModel> list = new ArrayList<>();

                try {

                    JSONArray Array = new JSONArray(response);

                    for (int i = 0; i < Array.length(); i++) {

                        JSONObject Object = Array.getJSONObject(i);
                        JSONObject imagej;
                        ZanguleModel model = new ZanguleModel();
                        model.id=Object.getInt("ID");
                        model.title=Object.getString("Title");
                        model.Date=Object.getString("PublishedDate");
                        model.IsVerified=Object.getBoolean("IsVerified");
                        model.Body=Object.getString("Body");
                        imagej=Object.getJSONObject("Image");
                        model.image=imagej.getString("Name");
                        list.add(model);
                    }

                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }
    public CoachModel getCoachDetail(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {
            Log.i("LOG", App.apiAddr+"user/getuserrolebyid/"+id);
            String response = connectToServer(App.apiAddr+"user/getuserrolebyid/"+id , "GET");

            Log.i("LOG", response + "");

            if (response != null) {



                try {
                    JSONObject Object = new JSONObject(response);
                    CoachModel model = new CoachModel();
                    model.Email = Object.getString("Email");
                    model.id = Object.getInt("ID");
                    model.fName = Object.getString("FirstName");
                    model.RegisteredDate = Object.getString("RegisteredDate");
                    model.Instagram = Object.getString("Instagram");
                    JSONArray UserRoles = Object.getJSONArray("UserRoles");
                    JSONObject user=UserRoles.getJSONObject(0);
                    model.IsVerified = user.getBoolean("IsVerified");
                    model.like = Object.getInt("Likes");
                    model.lName = Object.getString("LastName");
                    model.Mobile = Object.getString("Mobile");
                    model.Rate = Object.getDouble("Rate");
                    model.Telegram = Object.getString("Telegram");
                    model.sorosh = Object.getString("Surush");
                    model.Bio = Object.getString("Bio");
//                    JSONObject Rolej=Object.getJSONObject("UserRoles");
//                    model.rolename=Rolej.getString("Name");
//                    model.roleid=Rolej.getInt("ID");
                    JSONObject cityj=Object.getJSONObject("City");
                    model.City = cityj.getString("Name");
                    model.idCity = cityj.getInt("ID");
                    JSONObject Statej = cityj.getJSONObject("State");

                    model.State = Statej.getString("Name");
                    model.idState = Statej.getInt("ID");
                    JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                    model.IdImg=ProfileImagej.getInt("ID");
                    model.ImgName=ProfileImagej.getString("Name");



                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public CoachModel getReffreDetail(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {
            Log.i("LOG", App.apiAddr+"user/getuserrolebyid/"+id);
            String response = connectToServer(App.apiAddr+"user/getuserrolebyid/"+id , "GET");

            Log.i("LOG", response + "");

            if (response != null) {



                try {
                    JSONObject Object = new JSONObject(response);
                    CoachModel model = new CoachModel();
                    model.Email = Object.getString("Email");
                    model.id = Object.getInt("ID");
                    model.fName = Object.getString("FirstName");
                    model.RegisteredDate = Object.getString("RegisteredDate");
                    model.Instagram = Object.getString("Instagram");
                    JSONArray UserRoles = Object.getJSONArray("UserRoles");
                    JSONObject user=UserRoles.getJSONObject(0);
                    model.IsVerified = user.getBoolean("IsVerified");
                    model.like = Object.getInt("Likes");
                    model.lName = Object.getString("LastName");
                    model.Mobile = Object.getString("Mobile");
                    model.Rate = Object.getDouble("Rate");
                    model.Telegram = Object.getString("Telegram");
                    model.sorosh = Object.getString("Surush");
                    model.Bio = Object.getString("Bio");
//                    JSONObject Rolej=Object.getJSONObject("UserRoles");
//                    model.rolename=Rolej.getString("Name");
//                    model.roleid=Rolej.getInt("ID");
                    JSONObject cityj=Object.getJSONObject("City");
                    model.City = cityj.getString("Name");
                    model.idCity = cityj.getInt("ID");
                    JSONObject Statej = cityj.getJSONObject("State");
                    model.State = Statej.getString("Name");
                    model.idState = Statej.getInt("ID");
                    JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                    model.IdImg=ProfileImagej.getInt("ID");
                    model.ImgName=ProfileImagej.getString("Name");



                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }
    public List<GymModel> getGymsByField(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "gym/selectlist", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<GymModel> list = new ArrayList<>();

                try {

                    JSONArray Array = new JSONArray(response);

                    for (int i = 0; i < Array.length(); i++) {

                        JSONObject Object = Array.getJSONObject(i);
                        GymModel model = new GymModel();

                        model.idCity = Object.getInt("idCity");
                        model.Des = Object.getString("Des");
                        model.idCurrentSMSPlan = Object.getInt("idCurrentSMSPlan");
                        model.Lat = Object.getDouble("Lat");
                        model.Lon = Object.getDouble("Lon");
                        model.Name = Object.getString("Name");
                        model.notifCount = Object.getInt("notifCount");
                        model.Tell = Object.getString("Tell");
                        model.workTime = Object.getString("workTime");
                        model.Email = Object.getString("Email");
                        model.fname = Object.getString("fname");
                        model.id = Object.getInt("id");
                        model.idCurrentPlan = Object.getInt("idCurrentPlan");
                        model.Instagram = Object.getString("Instagram");
                        model.lastUpdate = Object.getString("lastUpdate");
                        model.like = Object.getInt("like");
                        model.lName = Object.getString("LName");
                        model.Address = Object.getString("Address");
                        model.Rate = Object.getDouble("Rate");
                        model.Telegram = Object.getString("Telegram");
                        model.City = Object.getString("City");
                        model.State = Object.getString("State");
                        model.ImgName = Object.getString("Img");

                        list.add(model);


                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public GymModel getGymInfo(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr+"user/GetUserRoleByID/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                GymModel model = new GymModel();

                try {

                    JSONObject Object = new JSONObject(response);
                    model.Email = Object.getString("Email");
                    model.fname = Object.getString("FirstName");
                    model.Instagram = Object.getString("Instagram");
                    model.lName = Object.getString("LastName");

                    model.Telegram = Object.getString("Telegram");
                    model.Address = Object.getString("Address");

                    //  model.Img = Object.getString("Img");
                    //  model.id = Object.getInt("id");
                    //  model.idCity = Object.getInt("idCity");
                    // model.notifCount = Object.getInt("notifCount");
                    // model.idCurrentPlan = Object.getInt("idCurrentPlan");
                    model.like = Object.getInt("Likes");
                    // model.lastUpdate = Object.getString("lastUpdate");
//                    model.Tell = Object.getString("Tell");
                    model.workTime = Object.getString("WorkingTime");
                    model.Rate = Object.getDouble("Rate");
                    //  model.City = Object.getString("City");
                    //   model.State = Object.getString("State");
                    // model.Des = Object.getString("Des");
//                    model.idCurrentSMSPlan = Object.getInt("idCurrentSMSPlan");
                    model.Lat = Object.getDouble("Latituide");
                    model.Lon = Object.getDouble("Longtuide");
                    model.Name = Object.getString("Name");
                    model.Address = Object.getString("Address");
                    model.About = Object.getString("About");
                    model.Mobile= Object.getString("Mobile");
                    JSONArray UserRoles = Object.getJSONArray("UserRoles");
                    JSONObject user=UserRoles.getJSONObject(0);
                    model.IsVerified = user.getBoolean("IsVerified");
                    JSONObject cityj=Object.getJSONObject("City");
                    model.City=cityj.getString("Name");
                    model.idCity=cityj.getInt("ID");
                    JSONObject Statej=cityj.getJSONObject("State");
                    model.State=Statej.getString("Name");
                    model.idState=Statej.getInt("ID");
                    JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                    model.ImgName=ProfileImagej.getString("Name");
                    model.idImg=ProfileImagej.getInt("ID");
                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<CoachHonorModel> getGymHonor(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "honor/SelectGymHonorrs/" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachHonorModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CoachHonorModel model = new CoachHonorModel();

                        model.id = Object.getInt("ID");
                        model.Date = Object.getString("Date");

                        // model.idRow = Object.getInt("idRow");
                        model.Name = Object.getString("Name");
                        model.Title = Object.getString("Title");

                        // model.Des = Object.getString("Des");
                        // model.isGym = Object.getBoolean("isGym");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<GalleryModel> getGymGallery(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Gallery/GetByUserRoleId/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<GalleryModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        GalleryModel model = new GalleryModel();

                        model.id = Object.getInt("ID");
                        model.Title=Object.getString("Title");
                        model.Description=Object.getString("Description");
                        JSONObject Imagej=Object.getJSONObject("Image");
                        model.img = Imagej.getString("Name");
//                        model.Date = Object.getString("Date");
//                        model.lastUpdate = Object.getString("lastUpdate");
//                        model.idRow = Object.getInt("idRow");
//                        model.Name = Object.getString("Name");
//                        model.Title = Object.getString("Title");
//                        model.Img = Object.getString("Img");
//                        model.Des = Object.getString("Des");
//                        model.isGym = Object.getBoolean("isGym");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<GymCoachesModel> getGymCoaches(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/GetGymCoaches/" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<GymCoachesModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        GymCoachesModel model = new GymCoachesModel();

                        model.idUser = Object.getInt("ID");
                        model.Img = Object.getJSONObject("ProfileImage").getString("Name");
                        model.fName = Object.getString("FirstName");
                        model.lName = Object.getString("LastName");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<CourseModel> getGymCourses(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "GymTerm/GetByGymID/"+id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CourseModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CourseModel model = new CourseModel();

                        model.idTerm = Object.getInt("ID");
                        model.startDate = Object.getString("StartDate");
                        model.endDate = Object.getString("EndDate");
                        model.coachName = Object.getJSONObject("Coach").getString("FirstName")+" "+Object.getJSONObject("Coach").getString("LastName");
                        model.Title = Object.getString("Title");
                        // model.Times = Object.getString("Times");
                        model.Days = Object.getString("Days");
                        model.idcoach=Object.getJSONObject("Coach").getInt("ID");

                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<CoachModel> getReffres(boolean isInternetAvailable, int fid,int cid) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/get?fid="+fid+"&cid="+cid+"&rid=4", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CoachModel> list = new ArrayList<>();

                try {

                    JSONArray Array = new JSONArray(response);

                    for (int i = 0; i < Array.length(); i++) {

                        JSONObject Object = Array.getJSONObject(i);
                        CoachModel model = new CoachModel();
//                        model.Email = Object.getString("Email");
                        model.id = Object.getInt("ID");
                        model.fName = Object.getString("FirstName");
                        model.like = Object.getInt("Likes");
                        //model.RegisteredDate = Object.getString("RegisteredDate");
//                        model.Instagram = Object.getString("Instagram");
                        model.IsVerified = Object.getBoolean("IsVerified");
                        // model.like = Object.getInt("like");
                        model.lName = Object.getString("LastName");
                        // model.Mobile = Object.getString("Mobile");
                        // model.Rate = Object.getDouble("Rate");
                        // model.Telegram = Object.getString("Telegram");
                        //model.sorosh = Object.getString("sorosh");
                        // JSONObject Rolej=Object.getJSONObject("Role");
                        // model.rolename=Rolej.getString("Name");
                        // model.roleid=Rolej.getInt("ID");
                        // JSONObject cityj=Object.getJSONObject("City");
                        // model.City = cityj.getString("Name");
                        // model.idCity = cityj.getInt("ID");
                        // model.State = cityj.getString("State");
                        JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                        model.IdImg=ProfileImagej.getInt("ID");
                        model.ImgName=ProfileImagej.getString("Name");
                        list.add(model);
                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }
    public List<GymModel> getGymByField(boolean isInternetAvailable, int fid,int cid) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr+"user/get?fid="+fid+"&cid="+cid+"&rid=5" , "GET");

            Log.i("LOG", response + "");

            if (response != null) {

                List<GymModel> list = new ArrayList<>();

                try {

                    JSONArray Array = new JSONArray(response);

                    for (int i = 0; i < Array.length(); i++) {

                        JSONObject Object = Array.getJSONObject(i);
                        GymModel model = new GymModel();
//                        model.Email = Object.getString("Email");
                        model.id = Object.getInt("ID");
                        model.fname = Object.getString("FirstName");
                        model.lName = Object.getString("LastName");
                        model.Name=Object.getString("Name");
//                        model.RegisteredDate = Object.getString("RegisteredDate");
//                        model.Instagram = Object.getString("Instagram");
                        model.IsVerified = Object.getBoolean("IsVerified");
                        model.like = Object.getInt("Likes");

//                        model.Mobile = Object.getString("Mobile");
//                        // model.Rate = Object.getDouble("Rate");
//                        // model.Telegram = Object.getString("Telegram");
//                        //model.sorosh = Object.getString("sorosh");
//                        JSONObject Rolej=Object.getJSONObject("Role");
//                        model.rolename=Rolej.getString("Name");
//                        model.roleid=Rolej.getInt("ID");
//                        JSONObject cityj=Object.getJSONObject("City");
//                        model.City = cityj.getString("Name");
//                        model.idCity = cityj.getInt("ID");
//                        model.State = cityj.getString("State");
                        JSONObject ProfileImagej=Object.getJSONObject("ProfileImage");
                        model.ImgID=ProfileImagej.getInt("ID");
                        model.ImgName=ProfileImagej.getString("Name");
                        list.add(model);
                    }

                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }


    public navigationMenuModel getAboutUs(boolean isInternetAvailable, String key) {


        if (isInternetAvailable) {


            String response = connectToServer(App.apiAddr + "kesho/getbyKey?key="+key, "GET");


            if (response != null) {

                try
                {
                    navigationMenuModel model = new navigationMenuModel();
                    JSONObject Object = new JSONObject(response);
                    model.value = Object.getString("KeshoValue");

                    JSONArray images = Object.getJSONArray("Images");

                    for (int i=0 ; i<images.length() ; i++)
                    {
                        JSONObject img = images.getJSONObject(i);
                        model.images.add(img.getString("Name"));
                    }


                    return model;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }

            }

            return null;
        } else
            return null;
    }

    public List<FAQmodel> getFAQ(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "FAQ/Get", "GET");

            if (response != null) {

                List<FAQmodel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        FAQmodel model = new FAQmodel();

                        model.question = Object.getString("Question");
                        model.answer = Object.getString("Answer");


                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public String addgymcoach(boolean isInternetAvailable, int idgym,int idcoach) {
        if (isInternetAvailable) {
            String response = connectToServer(App.apiAddr + "user/AddGymCoach?gid="+ idgym +"&cid="+idcoach, "GET");
            return response;
        } else
            return null;
    }
    public String deleteِgymcoach(boolean isInternetAvailable, int idgym,int idcoach) {
        if (isInternetAvailable) {
            String response = connectToServer(App.apiAddr + "user/DeleteGymCoach?gid="+ idgym +"&cid="+idcoach, "GET");
            return response;
        } else
            return null;
    }
    public String deletegymcoach(boolean isInternetAvailable, int idgym,int idcoach) {

        String result="";

        if (isInternetAvailable) {


            String response = connectToServer(App.apiAddr + "user/DeleteGymCoach?gid="+ idgym +"&cid="+idcoach, "GET");
            return response;
        } else
            return null;
    }
    public String getBio(boolean isInternetAvailable,int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/getbio?urid="+id, "GET");
            return response;
        } else
            return null;
    }
    public String getworktime(boolean isInternetAvailable,int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "user/GetgymTime?urid="+id, "GET");
            response=response.replace("\"","");
            response=response.replace("\\n","\n");
            return response;
        } else
            return null;
    }
    public List<ZanguleModel> getZangule(boolean isInternetAvailable,int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Post/Get/"+id, "GET");
            Log.i("LOG", response + "");
            if (response != null) {

                List<ZanguleModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ZanguleModel model = new ZanguleModel();
                        model.id=Object.getInt("ID");
                        model.title=Object.getString("Title");
                        model.Date=Object.getString("PublishedDate");
                        model.Body=Object.getString("Body");
                        model.image=Object.getJSONObject("Image").getString("Name");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<ZanguleModel> getZanguleGym(boolean isInternetAvailable,int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Post/GetbyGymId/"+id, "GET");
            Log.i("LOG", response + "");
            if (response != null) {

                List<ZanguleModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ZanguleModel model = new ZanguleModel();
                        model.id=Object.getInt("ID");
                        model.title=Object.getString("Title");
                        model.Date=Object.getString("PublishedDate");
                        model.Body=Object.getString("Body");
                        model.image=Object.getJSONObject("Image").getString("Name");
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public List<ZanguleModel> getZanguleKaryabi(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Post/Get/"+3, "GET");
            Log.i("LOG", response + "");
            if (response != null) {

                List<ZanguleModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ZanguleModel model = new ZanguleModel();
                        model.id=Object.getInt("ID");
                        model.title=Object.getString("Title");
                        model.Date=Object.getString("PublishedDate");
                        model.Body=Object.getString("Body");
                        model.image=Object.getJSONObject("Image").getString("Name");
                        model.getJsonUser(Object.getJSONObject("User"));
                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }
    public ZanguleModel getZanguleDetail(boolean isInternetAvailable,int id) {

        if (isInternetAvailable) {
            String response = connectToServer(App.apiAddr + "Post/GetPost/"+id, "GET");
            Log.i("LOG", response + "");
            if (response != null) {
                try {

                    JSONObject Object = new JSONObject(response);
                        ZanguleModel model = new ZanguleModel();
                        model.id=Object.getInt("ID");
                        model.title=Object.getString("Title");
                        model.Date=Object.getString("PublishedDate");
                        model.Body=Object.getString("Body");
                        model.image=Object.getJSONObject("Image").getString("Name");
                        model.getJsonUser(Object.getJSONObject("User"));
                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public List<ChartModel> getChart(boolean isInternetAvailable, int FieldID, int StateID, boolean isCommittee) {

        if (isInternetAvailable) {

            String response;
            if (!isCommittee)
                response = connectToServer(App.apiAddr + "Chart/GetByFieldID?fid="+FieldID+"&sid="+StateID, "GET");
            else
                response = connectToServer(App.apiAddr + "Chart/Get?sid="+StateID, "GET");



            if (response != null) {

                List<ChartModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ChartModel model = new ChartModel();

                        model.title = Object.getString("Post");

                        JSONObject User = Object.getJSONObject("User");
                        JSONObject ProfileImage = User.getJSONObject("ProfileImage");
                        model.name = User.getString("FirstName") + " " + User.getString("LastName");
                        model.image = ProfileImage.getString("Name");


                        list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String sendSuggestion(boolean isInternetAvailable , String req) {

        if (isInternetAvailable) {

            String response = connectToServerByJson(App.apiAddr + "Support/Send", "POST", req);


            if (response != null)
                return String.valueOf(response);


            else
                return null;
        }
        return null;
    }

    public String getSliderImage(boolean isInternetAvailable , int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Slider/GetByFieldID/"+id, "GET");
            String result = null;

            if (response != null) {

                try
                {

                    JSONObject Object = new JSONObject(response);
                    JSONObject Image = Object.getJSONObject("Image");

                    result = Image.getString("Name");
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }

    public MainPageModel getMainPageCounts(boolean isInternetAvailable, int idState, int idField) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Main/Get?fid="+idField+"&sid="+idState, "GET");


            if (response != null) {

                try
                {
                    MainPageModel model = new MainPageModel();
                    JSONObject Object = new JSONObject(response);

                    model.notifsCount = Object.getString("ElanatCount");
                    model.teachsCount = Object.getString("TrainingCount");

                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }

    public String getCommitteeName(boolean isInternetAvailable , int id) {

        if (isInternetAvailable) {

            String response = connectToServer(App.apiAddr + "Field/GetById/"+id, "GET");
            String result = null;

            if (response != null) {

                try
                {

                    JSONObject Object = new JSONObject(response);

                    result = Object.getString("Name");
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }
    //////////////////////////////////////////////jsons function
    public String editGymWorkTime(boolean isInternetAvailable, String Time, int id) {

        if (isInternetAvailable) {
//            String mytitle;
//            mytitle=bio.replace(" ", "%20");
            JSONObject j=new JSONObject();
            try {
                j.put("ID", "" + id + "");
                j.put("WorkingTime", Time);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/editgymtime", "POST",j.toString());
            Log.i("LOG", response + "");
            return response;
        } else
            return null;
    }
    public Integer AddGymTerm(boolean isInternetAvailable ,int gymid,int coachid,String title,String days,String StartDate,String EndDate) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID","");
                j.put("GymID",gymid);
                j.put("CoachID",coachid);
                j.put("Title",title);
                j.put("Days",days);
                j.put("StartDate",StartDate);
                j.put("EndDate",EndDate);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "GymTerm/AddGymTerm", "POST",j.toString());
            if (response != null) {
                try
                {
                    JSONObject Object = new JSONObject(response);
                    return Object.getInt("ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }
    public String EditGymTerm(boolean isInternetAvailable ,int id,int gymid,int coachid,String title,String days,String StartDate,String EndDate) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",id);
                j.put("GymID",gymid);
                j.put("CoachID",coachid);
                j.put("Title",title);
                j.put("Days",days);
                j.put("StartDate",StartDate);
                j.put("EndDate",EndDate);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "GymTerm/EditGymTerm", "POST",j.toString());
            if (response != null) {
                return response;
            }

            else
                return null;
        }
        return null;
    }
    public String DeleteGymTerm(boolean isInternetAvailable ,int idterm) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",idterm);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "GymTerm/DeleteGymTerm", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String DeletePost(boolean isInternetAvailable ,int idpost) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",idpost);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Post/DeletePost", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String DeleteTrain(boolean isInternetAvailable ,int idpost) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",idpost);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Training/DeleteTraining", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String DeleteGallery(boolean isInternetAvailable ,int idpost) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                String idsend=""+idpost+"";
                j.put("ID",idsend);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Gallery/DeleteGallery", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String ForgetPass(boolean isInternetAvailable ,String phone) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("Mobile",phone);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/ForgetPassword", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String ChangePass(boolean isInternetAvailable ,int rid,String oldpass,String newpass) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("UserRoleID",rid);
                j.put("OldPassword",oldpass);
                j.put("NewPassword",newpass);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/EditPassword", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String EditCoachProfile(boolean isInternetAvailable ,CoachModel model) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",model.id);
                j.put("FirstName",model.fName);
                j.put("LastName",model.lName);
                j.put("MelliCode","");
                j.put("surush",model.sorosh);
                j.put("Instagram",model.Instagram);
                j.put("Telegram",model.Telegram);
                j.put("Mobile",model.Mobile);
                j.put("Email",model.Email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/EditUser", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String EditUserProfile(boolean isInternetAvailable ,UserModel model) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",model.id);
                j.put("FirstName",model.Name);
                j.put("LastName",model.lName);
                j.put("MelliCode","");
                j.put("surush","");
                j.put("Instagram","");
                j.put("Telegram","");
                j.put("Mobile",model.Mobile);
                j.put("Email",model.Email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/EditUser", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String EditGymProfile(boolean isInternetAvailable ,GymModel model) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",model.id);
                j.put("FirstName",model.fname);
                j.put("LastName",model.lName);
                j.put("Name",model.Name);
                j.put("MelliCode","");
                j.put("surush","");
                j.put("Instagram",model.Instagram);
                j.put("Telegram",model.Telegram);
                j.put("Mobile",model.Mobile);
                j.put("Email",model.Email);
                j.put("Address",model.Address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "user/EditUser", "POST",j.toString());
            if (response != null) {
                return response;
            }
            else
                return null;
        }
        return null;
    }
    public String EditKaryabi(boolean isInternetAvailable ,int id,String title,String body) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",id);
                j.put("Title",title);
                j.put("Body",body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Post/EditKaryabi", "POST",j.toString());
            if (response != null) {
                return response;
            }

            else
                return null;
        }
        return null;
    }
    public String EditElanat(boolean isInternetAvailable ,int id,String title,String body) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID",id);
                j.put("Title",title);
                j.put("Body",body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Post/EditElanat", "POST",j.toString());
            if (response != null) {
                return response;
            }

            else
                return null;
        }
        return null;
    }
    public Integer AddElanat(boolean isInternetAvailable ,int Rid,String title,String body) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID","");
                j.put("UserRoleID",Rid);
                j.put("Title",title);
                j.put("Body",body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Post/AddElanat", "POST",j.toString());
            if (response != null) {

                try {
                    return new JSONObject(response).getInt("ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }
    public Integer AddKaryabi(boolean isInternetAvailable ,int Rid,String title,String body) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {
                j.put("ID","");
                j.put("UserRoleID",Rid);
                j.put("Title",title);
                j.put("Body",body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Post/AddKaryabi", "POST",j.toString());
            if (response != null) {

                try {
                    return new JSONObject(response).getInt("ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else
                return null;
        }
        return null;
    }
    public Integer AddGallery(boolean isInternetAvailable ,int Rid,String title,String body) {

        if (isInternetAvailable) {
            JSONObject j=new JSONObject();
            try {

                j.put("UserRoleID",Rid);
                j.put("Title",title);
                j.put("Description",body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = connectToServerByJson(App.apiAddr + "Gallery/AddGallery", "POST",j.toString());
            if (response != null) {

                try {
                    return new JSONObject(response).getInt("ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return -1;
                }
            }

            else
                return -1;
        }
        return -1;
    }
    public String sendFileDetails(boolean isInternetAvailable , String name, int imgType, int objectID) {

        if (isInternetAvailable) {


            String req = "{\"Name\":\"" + name + "\",\"ObjectID\":" + objectID +",\"ImageTypeID\":" + imgType +"}";
            String response = connectToServerByJson(App.apiAddr + "upload/Addfile", "POST", req);

            String result = null;

            if (response != null && response.equals("OK"))
                return "ok";
            else
                return "failed";

        }
        return null;
    }
}
