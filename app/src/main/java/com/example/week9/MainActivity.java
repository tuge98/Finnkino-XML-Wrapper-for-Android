package com.example.week9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    List<String> list = new ArrayList<String>();
    Spinner exam_list_spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        exam_list_spinner = (Spinner) findViewById(R.id.spinnerii);
        spinnerClass();


    }

    public void readXML (View v){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlstring = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlstring);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "+ doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for(int i = 0; i < nList.getLength();i++){
                Node node = nList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    
                    System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());
                    //System.out.println("City");
                    list.add(element.getElementsByTagName("Name").item(0).getTextContent());
                }
            }
            for(int i = 0; i<list.size();i++){
                System.out.println(list.get(i));
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done");
        }
    }
    public void spinnerClass(){
        exam_list_spinner = (Spinner) findViewById(R.id.spinnerii);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exam_list_spinner.setAdapter(dataAdapter);
    }
}