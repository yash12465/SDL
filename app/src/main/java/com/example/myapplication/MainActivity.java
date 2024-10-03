package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView studentListView;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentListView = findViewById(R.id.studentListView);

        // Load data
        String fileName = "MyFile.csv";
        new Thread(() -> {
            students = ExcelReader.readExcelData(this, fileName);
            runOnUiThread(() -> {
                List<String> studentNames = new ArrayList<>();
                for (Student student : students) {
                    if (student.getAttendance() < 60) {
                        studentNames.add(student.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
                studentListView.setAdapter(adapter);

                studentListView.setOnItemClickListener((parent, view, position, id) -> {
                    Student selectedStudent = students.get(position);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + selectedStudent.getParentsPhoneNumber()));
                    if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    } else {
                        // Request permission if not granted
                        requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    }
                });
            });
        }).start();
    }
}
