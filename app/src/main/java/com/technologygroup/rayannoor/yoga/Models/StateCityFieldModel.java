package com.technologygroup.rayannoor.yoga.Models;

import java.util.ArrayList;
import java.util.List;

public class StateCityFieldModel {

    public List<idname> State;
    public List<idname> City;
    public List<idname> Field;
    public StateCityFieldModel()
    {
        State=new ArrayList<>();
        City=new ArrayList<>();
        Field=new ArrayList<>();
    }
    public List<String> spinnerState()
    {
        List<String> list=new ArrayList<>();
        for (int i=0;i<State.size();i++)
        {
            list.add(State.get(i).name);
        }
        return list;
    }
    public List<String> spinnerCity()
    {
        List<String> list=new ArrayList<>();
        for (int i=0;i<City.size();i++)
        {
            list.add(City.get(i).name);
        }
        return list;
    }
    public List<String> spinnerField()
    {
        List<String> list=new ArrayList<>();
        for (int i=0;i<Field.size();i++)
        {
            list.add(Field.get(i).name);
        }
        return list;
    }
}
