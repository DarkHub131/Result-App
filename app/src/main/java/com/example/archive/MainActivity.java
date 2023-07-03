package com.example.archive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnViewAsAdmin;
    private Button btnviewstu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnViewAsAdmin = findViewById(R.id.btnAdmin);

        btnViewAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(MainActivity.this, admin.class);
                startActivity(intent);
            }
        });
        btnviewstu = findViewById(R.id.btnStudent);
        btnviewstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AdminActivity
                Intent intent = new Intent(MainActivity.this, viewStudent.class);
                startActivity(intent);
            }
        });
    }
}