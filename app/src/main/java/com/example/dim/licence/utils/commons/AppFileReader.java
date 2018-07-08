package com.example.dim.licence.utils.commons;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class AppFileReader {
    //C:\Users\dim\Downloads\villes_france.sql\MesDepts.txt
    private File file;
    private ArrayList<String> rowBy5k;

    public AppFileReader(String path) {
        file = new File(path);
    }

    public void readFile() {
        if (file.exists()) {
            System.out.println("FOUND ! ");
        } else {
            return;
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Log.e(ARG_DEBUG, "readFile: ", ex);
        }

        ArrayList<String> rows = null;
        try {
            if (fileReader != null) {
                rows = new ArrayList<>();
                BufferedReader buffer = new BufferedReader(fileReader);

                while (buffer.ready()) {
                    String line = buffer.readLine();
                    if (!line.trim().isEmpty()) {
                        rows.add(line);
                    }
                }
                fileReader.close();
                buffer.close();
            }
        } catch (IOException ex) {
            Log.e(ARG_DEBUG, "readFile: ", ex);
        }

        // around 40K lines file
        // some more iterations are going to be computed
        int startIndex = 0;
        int limit = 5000;
        int incrementation = 5000;
        int iterations = 7;

        //for (int i = 0; i < iterations; i =i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ville VALUES (");

            for (int it = startIndex; it < limit; it++) {
                String s = rows.get(it);
                if (s != null) {
                    sb.append(s);
                }
            }
            sb.append(");");
    }

}
