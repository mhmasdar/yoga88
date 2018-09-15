package com.technologygroup.rayannoor.yoga.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hesi100 on 9/15/2018.
 */

public class ZanguleModel {
    public int id;
    public String title;
    public String image;
    public String Body;
    public String Date;
    public UserModel user;
    public void getJsonUser(JSONObject User) throws JSONException {
        user.id=User.getInt("ID");
        user.Name=User.getString("FirstName");
        user.lName=User.getString("LastName");
        user.RoleName=User.getString("RoleName");
    }
}
