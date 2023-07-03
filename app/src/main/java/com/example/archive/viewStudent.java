package com.example.archive;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class viewStudent extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView studentListView;

    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        // Initialize views
        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.search_button);
        studentListView = findViewById(R.id.student_list_view);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Setup adapter and list for student names
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(adapter);

        // Setup search button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStudents();
            }
        });

        // Display all students initially
        displayAllStudents();
    }

    private void displayAllStudents() {
        // Retrieve all student names from the database
        ArrayList<String> allStudents = dbHelper.getAllStudents();

        // Clear the current list
        studentList.clear();

        // Add all student names to the list
        studentList.addAll(allStudents);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    private void searchStudents() {
        // Get the roll number entered in the search field
        String rollNumber = searchEditText.getText().toString().trim();

        // Retrieve the student information based on the roll number from the database
        Cursor cursor = dbHelper.getStudentByRollNumber(rollNumber);

        // Clear the current list
        studentList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            // Iterate through the cursor and retrieve student information
            do {
                @SuppressLint("Range") String studentName = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnName()));
                @SuppressLint("Range") String studentRoll = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnRoll()));
                @SuppressLint("Range") String studentResults = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnResults()));

                // Append the student information to the list
                studentList.add("Name: " + studentName + "\nRoll: " + studentRoll + "\nResults: " + studentResults);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // No student found with the entered roll number
            Toast.makeText(this, "No student found", Toast.LENGTH_SHORT).show();
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
}