package com.carvalhodj.personalhealth.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dev01DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personalhealth.db";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_DNASEQ = "dna_seq";

    public static final String TABLE_DRUG = "drug";
    public static final String COLUMN_APPLICATION = "application";
    public static final String COLUMN_BIOPROF = "biological_profile";

    public Dev01DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_USER + " ( " +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_EMAIL + " TEXT NOT NULL, " +
                        COLUMN_PASS + " TEXT NOT NULL, " +
                        COLUMN_DNASEQ + " TEXT );");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_DRUG + " ( " +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_APPLICATION + " TEXT NOT NULL, " +
                        COLUMN_BIOPROF + " TEXT NOT NULL );");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('HeadDrug', 'headache', 'CTGGTGGT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('NoHeadPain', 'headache', 'CACAGCCT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('HeadFree', 'headache', 'ATCTTCAA');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('NauseaStop', 'nausea', 'CTGGTGGT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('NoNausea', 'nausea', 'CACAGCCT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('Nauseastamin', 'nausea', 'ATCTTCAA');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('StomachPeace', 'stomach ache', 'CTGGTGGT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('Stomachflex', 'stomach ache', 'CACAGCCT');");

        sqLiteDatabase.execSQL(
                "INSERT INTO " + TABLE_DRUG + " (name, application, biological_profile) " +
                        "VALUES ('HealStomach', 'stomach ache', 'ATCTTCAA');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(query);
        this.onCreate(db);
    }

}
