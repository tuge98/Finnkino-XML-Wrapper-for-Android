package com.example.week9;

public class DataStorage {

     String area;

     String areaid;


    //private List<String> listarray = new ArrayList<String>();





    public DataStorage(String dataarea, String idarea) {


        this.area = dataarea;
        this.areaid = idarea;
    }



    public void setArea(String component){
        this.area = component;
    }



    public void setAreaid(String component){
        this.areaid = areaid;
    }



    public String getArea(){
        return this.area;
    }




    public String getAreaid() {return this.areaid;}


}
