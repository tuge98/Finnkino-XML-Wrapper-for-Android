package com.example.week9;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    List<String> list = new ArrayList<String>();
    Spinner exam_list_spinner;
    DataList dataList = new DataList();
    ListView listView;
    CalendarView calendarView;
    public String dateObject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        exam_list_spinner = (Spinner) findViewById(R.id.spinnerii);
        listView = (ListView) findViewById(R.id.listView);
        readXML();
        spinnerClass();
        calenderManagement();
        this.setTitle("Finnkino XML-API wrapper");
        //showMovies();



    }

    public void readXML() {

        //DataStorage dataStorage = new DataStorage();
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlstring = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlstring);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());

                    //list.add(element.getElementsByTagName("Name").item(0).getTextContent());
                    dataList.addingtoList(element.getElementsByTagName("Name").item(0).getTextContent(), element.getElementsByTagName("ID").item(0).getTextContent());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done");
        }
    }

    public void spinnerClass() {


        ArrayList listofNames = dataList.converttoString();
        exam_list_spinner = (Spinner) findViewById(R.id.spinnerii);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listofNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exam_list_spinner.setAdapter(dataAdapter);
        //String x = exam_list_spinner.getSelectedItem();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showMovies(View v) {
        Date dt = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String jii = dtf.format(now).toString();
        String x = String.valueOf(exam_list_spinner.getSelectedItem());
        String xx = dataList.returnwantedObject(x);
        System.out.println(xx);
        list.clear();

        try {
            SimpleDateFormat firstDT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat secondDT = new SimpleDateFormat("HH:mm");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlstring = String.format("https://www.finnkino.fi/xml/Schedule/?area=%s&dt=%s",xx,dateObject);
            //String urlstring = "https://www.finnkino.fi/xml/Schedule/?area=1016&dt=15.03.2021";
            System.out.println(urlstring);
            Document doc = builder.parse(urlstring);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList2 = doc.getDocumentElement().getElementsByTagName("Show");

            for (int i = 0; i < nList2.getLength(); i++) {
                Node node2 = nList2.item(i);
                if (node2.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node2;
                    String ss = element2.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    try{
                        dt=firstDT.parse(ss);

                    } catch(ParseException e){
                        e.printStackTrace();
                    }
                    String start = secondDT.format(dt);
                    System.out.println(element2.getElementsByTagName("Title").item(0).getTextContent());

                    list.add(element2.getElementsByTagName("Title").item(0).getTextContent() + " " + start);

                    //dataList.addingtoList(element.getElementsByTagName("Name").item(0).getTextContent(), element.getElementsByTagName("ID").item(0).getTextContent());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done");


        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(itemsAdapter);
    }

    public void calenderManagement(){
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                Date xv = null;
                month = month +1;
                String returnString = dayOfMonth + "."+ month + "."+ year;
                //dateObject = returnString;
                System.out.println(returnString);
                String resultss = "";
                SimpleDateFormat newdatechange = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat olddatechange = new SimpleDateFormat("dd.M.yyyy");
                //String temporary = dateObject;

                try{
                    xv = olddatechange.parse(returnString);

                } catch (ParseException e){
                    e.printStackTrace();
                }
                String newdateValue = newdatechange.format(xv);
                System.out.println(newdateValue);
                dateObject = newdateValue;
            }

        });

    }
}
