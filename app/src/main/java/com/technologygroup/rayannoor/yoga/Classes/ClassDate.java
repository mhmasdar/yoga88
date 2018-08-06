package com.technologygroup.rayannoor.yoga.Classes;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Created by EHSAN on 2/17/2018.
 */

public class ClassDate {


    public String getDateTime(){

        String imgDate = String.valueOf(System.currentTimeMillis());
        return imgDate;
    }

    public String changeDateToString(int intDate){

        String stringDate;

        String year = String.valueOf(intDate).substring(0,4);
        String month = String.valueOf(intDate).substring(4,6);
        String day = String.valueOf(intDate).substring(6,8);

        stringDate = year + "/" + month + "/" + day;

        return stringDate;
    }

    public String getDate(){

        String date;

        PersianCalendar persianCalendar = new PersianCalendar();
        //date = persianCalendar.getPersianYear() + "" + persianCalendar.getPersianMonth() + "" + persianCalendar.getPersianDay() + "";
        boolean flagMonth = false, flagDay = false;

        if (persianCalendar.getPersianDay() / 10 < 1)
            flagDay = true;
        if ((persianCalendar.getPersianMonth()+1) / 10 < 1)
            flagMonth = true;

        date = persianCalendar.getPersianYear() + "";
        if (flagMonth)
            date += "0" + (persianCalendar.getPersianMonth() + 1);
        else
            date += "" + (persianCalendar.getPersianMonth() + 1);
        if (flagDay)
            date += "0" + persianCalendar.getPersianDay();
        else
            date += "" + persianCalendar.getPersianDay();


        return date;
    }

}
