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
import android.widget.ListView;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String jii = dtf.format(now).toString();

        String x = String.valueOf(exam_list_spinner.getSelectedItem());

        //System.out.println(x);

        String xx = dataList.returnwantedObject(x);
        System.out.println(xx);


        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlstring = String.format("https://www.finnkino.fi/xml/Schedule/?area=%s&dt=%s",xx,jii);
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

                    System.out.println(element2.getElementsByTagName("Title").item(0).getTextContent());

                    list.add(element2.getElementsByTagName("Title").item(0).getTextContent());
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

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
    }


}
