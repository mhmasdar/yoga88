package com.technologygroup.rayannoor.yoga.Models;

/**
 * Created by EHSAN on 4/17/2018.
 */

public class TeachesModel {

    public int id;

    public String Date;
    public boolean IsVerified;
    public boolean IsUpdated;
    public UserModel user;
    public String Title;
    public String Body;
    public String Images;
    public String Name;
    public String ImagePersonal;
    public TeachesModel()
    {
        user=new UserModel();
    }

}
//{"ID":2,"Title":"آموزش یوگا","PublishDate":null,"IsVerified":true,"IsUpdated":false,"User":{"ID":4,"FirstName":"حسین","LastName":"شوقی فر","Mobile":null,"Email":null,"Password":null,"IsVerified":false,"RegisteredDate":null,"Role":{"ID":0,"Name":null},"City":{"ID":0,"Name":null,"State":null},"SessionKey":null,"ProfileImage":{"ID":0,"Name":null}},"Bodies":null}
