package xyz.getsoft.sendsms;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileReader {

    private String filePath;

    public MyFileReader(String filePath){
        this.filePath = filePath;
    }


    public ArrayList readLines(){
        ArrayList arrayList = new ArrayList();

        int i=0;

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                //Log.d("line", line);
                arrayList.add(line);
                ++i;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("total", i+ "lines");

        return arrayList;
    }
}
