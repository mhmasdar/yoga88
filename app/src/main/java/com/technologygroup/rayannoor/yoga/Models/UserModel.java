package com.technologygroup.rayannoor.yoga.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EHSAN on 4/16/2018.
 */

public class UserModel {

    public int id;
    public String Name;
    public String lName;
    public String Mobile;
    public String Email;
    public String Password;
    public boolean IsVerified;
    public JSONArray userType;
    public int cityid;
    public int SessionKey;
    public String cityName;
    public String state;
    public String RoleName;
    public int IDProfileImage;
    public String ProfileImageName;
    public void getfromjson(JSONObject Object)
    {
        try {
//            this.Email = Object.getString("Email");
            this.Name = Object.getString("FirstName");
//            this.Password = Object.getString("Password");
            this.lName = Object.getString("LastName");
            this.id = Object.getInt("ID");
 //           this.Mobile = Object.getString("Mobile");
            this.IsVerified = Object.getBoolean("IsVerified");
//            JSONObject rolej = Object.getJSONObject("Role");
//            this.Roleid = rolej.getInt("ID");
//            this.RoleName = rolej.getString("Name");
            JSONObject Cityj = Object.getJSONObject("City");
            this.cityid = Cityj.getInt("ID");
            this.cityName = Cityj.getString("Name");
            this.state = Cityj.getString("State");
         // this.SessionKey = Object.getInt("SessionKey");
            JSONObject ProfileImagej = Object.getJSONObject("ProfileImage");
            this.ProfileImageName = ProfileImagej.getString("Name");
            this.IDProfileImage = ProfileImagej.getInt("ID");

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
