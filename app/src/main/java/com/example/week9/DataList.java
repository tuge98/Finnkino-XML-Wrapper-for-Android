package com.example.week9;

import java.util.ArrayList;

public class DataList {

    //private DataStorage[] DataArray;
    private ArrayList<DataStorage> DataArray;
    ArrayList<String> stringlist;


    public DataList() {
        DataArray = new ArrayList<DataStorage>();


    }

    public ArrayList addingtoList(String s, String y) {
        DataArray.add(new DataStorage(s, y));

        return DataArray;
    }

    public ArrayList converttoString(){
        ArrayList<String> listofStrings = new ArrayList<String>();





        for (int i = 0; i < DataArray.size(); i++) {
            listofStrings.add(DataArray.get(i).getArea());

        }

        return listofStrings;

    }
    public String returnwantedObject(String h){
        String ii = null;
        for(int i = 0; i < DataArray.size(); i++){
            if(DataArray.get(i).getArea() == h){
                ii = DataArray.get(i).getAreaid();
                break;




            } else{
                continue;
            }
        }
        return ii;
    }

}
