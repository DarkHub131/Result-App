package com.example.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Deletion extends AppCompatActivity {

    private EditText rollNumberEditText;
    private Button deleteButton;
    private ListView studentListView;

    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletion);

        // Initialize views
        rollNumberEditText = findViewById(R.id.roll_number_edit_text);
        deleteButton = findViewById(R.id.delete_button);
        studentListView = findViewById(R.id.student_list_view);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Setup adapter and list for student names
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(adapter);

        // Setup delete button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
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

    private void deleteStudent() {
        // Get the roll number entered in the EditText
        String rollNumber = rollNumberEditText.getText().toString().trim();

        if (!rollNumber.isEmpty()) {
            // Delete student from the database
            dbHelper.deleteStudentByRollNumber(rollNumber);

            // Clear the roll number EditText
            rollNumberEditText.setText("");

            // Display success message
            Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();

            // Refresh the student list
            displayAllStudents();
        } else {
            Toast.makeText(this, "Please enter a roll number", Toast.LENGTH_SHORT).show();
        }
    }
}