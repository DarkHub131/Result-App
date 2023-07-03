package com.example.archive;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    private EditText etName, etRoll, etResults;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize EditText fields
        etName = findViewById(R.id.etName);
        etRoll = findViewById(R.id.etRoll);
        etResults = findViewById(R.id.etResults);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRegistration();
            }
        });
    }

    private void saveRegistration() {
        // Get the entered values
        String name = etName.getText().toString().trim();
        String roll = etRoll.getText().toString().trim();
        String results = etResults.getText().toString().trim();

        // Insert the data into the database
        long newRowId = dbHelper.insertRegistration(name, roll, results);

        // Check if the data was inserted successfully
        if (newRowId == -1) {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            // Clear the EditText fields after saving the data
            clearFields();
        }
    }

    private void clearFields() {
        etName.setText("");
        etRoll.setText("");
        etResults.setText("");
    }
}