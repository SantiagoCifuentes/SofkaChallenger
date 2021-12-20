package com.example.sofkachallenger.db;

import android.provider.BaseColumns;

public final class Config
{



    private Config()
    {
        //constructor sin parámetros
    }

    //Configuracion de las tablas en la bd

    public static class QuestionsTable1 implements BaseColumns
    {
        public final static String TABLE_NAME = "quiz_questions";
        public final static String COLUMN_QUESTION = "questions";
        public final static String COLUMN_OPTION1 = "option1";
        public final static String COLUMN_OPTION2 = "option2";
        public final static String COLUMN_OPTION3 = "option3";
        public final static String COLUMN_OPTION4 = "option4";
        public final static String COLUMN_ANSWER  = "answer";
        public final static String COLUMN_CATEGORY = "category";
    }


    protected  final  static  String  SQL_CREATE_QUESTIONS_TABLE = //
            "CREATE TABLE " + QuestionsTable1.TABLE_NAME + " (" +
                    QuestionsTable1._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    QuestionsTable1.COLUMN_QUESTION + " TEXT NOT NULL," +
                    QuestionsTable1.COLUMN_OPTION1 + " TEXT NOT NULL," +
                    QuestionsTable1.COLUMN_OPTION2 + " TEXT NOT NULL," +
                    QuestionsTable1.COLUMN_OPTION3 + " TEXT NOT NULL," +
                    QuestionsTable1.COLUMN_OPTION4 + " TEXT NOT NULL," +
                    QuestionsTable1.COLUMN_ANSWER + " INTEGER NOT NULL," +
                    QuestionsTable1.COLUMN_CATEGORY + " INTEGER NOT NULL)" ;



    protected  final  static  String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + QuestionsTable1.TABLE_NAME;




}
