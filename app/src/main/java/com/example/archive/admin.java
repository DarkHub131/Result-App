package com.example.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {
    private Button btnAddstu;
    private Button btnviewstu;
    private Button btndelestu;
    private Button btnsearchstu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        btnAddstu = findViewById(R.id.btnAddStudent);

        btnAddstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(admin.this, Registration.class);
                startActivity(intent);
            }
        });
        btnviewstu = findViewById(R.id.btnViewAllStudents);
        btnviewstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(admin.this, viewStudent.class);
                startActivity(intent);
            }
        });
        btnsearchstu =findViewById(R.id.btnSearchStudent);
        btnsearchstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(admin.this, viewStudent.class);
                startActivity(intent);
            }
        });
        btndelestu =findViewById(R.id.btnDeleteStudent);
        btndelestu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(admin.this, Deletion.class);
                startActivity(intent);
            }
        });


    }

}