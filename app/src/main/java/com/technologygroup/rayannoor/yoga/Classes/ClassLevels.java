package com.technologygroup.rayannoor.yoga.Classes;

/**
 * Created by EHSAN on 3/1/2018.
 */

public class ClassLevels {

    public String getCoachLevelName(int idLevel){

        String levelName;

        switch (idLevel) {
            case -1:
                levelName = "غیر فعال";
                break;
            case 1:
                levelName = "فعال";
                break;
            default:
                levelName = "غیر فعال";
        }

        return levelName;
    }

}
