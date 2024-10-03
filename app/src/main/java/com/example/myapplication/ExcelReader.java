package com.example.myapplication;


import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<Student> readExcelData(Context context, String fileName) {
        List<Student> students = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student();
                student.setName(data[0]);
                student.setRollNo(Integer.parseInt(data[1]));
                student.setAttendance(Integer.parseInt(data[2]));
                student.setParentsPhoneNumber(data[3]);
                students.add(student);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
