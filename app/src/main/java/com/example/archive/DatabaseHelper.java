package com.example.archive;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "registration.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "registration";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ROLL = "roll";
    private static final String COLUMN_RESULTS = "results";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ROLL + " TEXT, " +
                COLUMN_RESULTS + " TEXT);";

        db.execSQL(createTableQuery);

        // Insert 180 students with random names and CGPA
        insertRandomStudents(db, 180, 1503001);
        insertRandomStudents(db, 180, 1603001);
        insertRandomStudents(db, 180, 1403001);

    }

    private void insertRandomStudents(SQLiteDatabase db, int count, int startRollNumber) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, getRandomName());
            values.put(COLUMN_ROLL, String.valueOf(startRollNumber + i));
            values.put(COLUMN_RESULTS, getRandomCGPA());

            db.insert(TABLE_NAME, null, values);
        }
    }

    private String getRandomName() {
        String[] names = {
                "Aafreen", "Aadil", "Abir", "Abul Kalam", "Achala", "Aditi", "Adhora", "Aftab", "Agam", "Agastya",
                "Ahsan", "Akter", "Alam", "Al Amin", "Alishba", "Aliya", "Almas", "Aloka", "Alomgir", "Alpana",
                "Alvira", "Alvina", "Amir", "Ananta", "Anika", "Arman", "Arfan", "Ashraf", "Asad", "Asif", "Aziz",
                "Badar", "Fahim", "Faiyaz", "Farhan", "Farhad", "Faris", "Fatima", "Fida", "Habib", "Haider",
                "Hasib", "Hasan", "Helal", "Humayun", "Imtiaz", "Islam", "Kabir", "Karim", "Kazi", "Khaled",
                "Khan", "Khair", "Khalil", "Mahbub", "Mahir", "Mahmood", "Manik", "Masud", "Mehedi", "Moin",
                "Mohammad", "Monirul", "Mujibur", "Mustafizur", "Nahid", "Naimul", "Najmul", "Nasir", "Nazim",
                "Nazrul", "Noor", "Obaid", "Omar", "Owais", "Parvez", "Quazi", "Rashid", "Rauf", "Rayhan",
                "Reza", "Riaz", "Robin", "Saad", "Sabina", "Sahar", "Saleh", "Salim", "Samir", "Sania",
                "Sarwar", "Shahab", "Shahid", "Shahriar", "Shakil", "Sharif", "Shawon", "Siddique", "Sohel",
                "Tahmid", "Tariq", "Taufiq", "Touhid", "Umme", "Yasmin", "Zakir",
        };

        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }

    private String getRandomCGPA() {
        Random random = new Random();
        double cgpa = 2.80 + random.nextDouble() * (4.0 - 2.80);
        return String.format(Locale.getDefault(), "%.2f", cgpa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertRegistration(String name, String roll, String results) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ROLL, roll);
        values.put(COLUMN_RESULTS, results);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public ArrayList<String> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_NAME, COLUMN_ROLL, COLUMN_RESULTS};

        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);

        ArrayList<String> studentList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String roll = cursor.getString(cursor.getColumnIndex(COLUMN_ROLL));
                @SuppressLint("Range") String results = cursor.getString(cursor.getColumnIndex(COLUMN_RESULTS));

                String studentInfo = "Name: " + name + "\nRoll: " + roll + "\nResults: " + results;
                studentList.add(studentInfo);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return studentList;
    }

    public void deleteRegistration(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    @SuppressLint("Range")
    public Cursor getStudentByRollNumber(String rollNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_NAME, COLUMN_ROLL, COLUMN_RESULTS};
        String selection = COLUMN_ROLL + " = ?";
        String[] selectionArgs = {rollNumber};

        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }
    public String getColumnName() {
        return COLUMN_NAME;
    }
    public String getColumnRoll() {
        return COLUMN_ROLL;
    }
    public void deleteStudentByRollNumber(String rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_ROLL + " = ?";
        String[] selectionArgs = {rollNumber};

        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }
    public String getColumnResults() {
        return COLUMN_RESULTS;
    }
}