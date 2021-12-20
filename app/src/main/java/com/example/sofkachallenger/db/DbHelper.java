package com.example.sofkachallenger.db;

import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_ANSWER;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_CATEGORY;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION1;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION2;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION3;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION4;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_QUESTION;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.TABLE_NAME;
import static com.example.sofkachallenger.db.Config.SQL_CREATE_QUESTIONS_TABLE;
import static com.example.sofkachallenger.db.Config.SQL_DELETE_USER;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SofkaChallenger.db";

    private Context context;
    SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {

        this.db = db;
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillingQuestionsTB();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)  //si se le hace un cambio nuevo a la bd, se le cambia la versión de la db y se ejecuta este comando
    {


        db.execSQL(SQL_DELETE_USER); // se ejecuta la creacion de la tabla
        onCreate(db);
    }



    private void addQuestions(Questions questions) // se agregan las preguntas a la tabla
    {

        ContentValues values = new ContentValues();
        values.put(Config.QuestionsTable1.COLUMN_QUESTION, questions.getQuestion());
        values.put(Config.QuestionsTable1. COLUMN_OPTION1, questions.getOption1());
        values.put( Config.QuestionsTable1.COLUMN_OPTION2, questions.getOption2());
        values.put( Config.QuestionsTable1.COLUMN_OPTION3, questions.getOption3());
        values.put( Config.QuestionsTable1.COLUMN_OPTION4, questions.getOption4());
        values.put( Config.QuestionsTable1.COLUMN_ANSWER, questions.getAnswer());
        values.put( Config.QuestionsTable1.COLUMN_CATEGORY, questions.getCategory());
        db.insert(Config.QuestionsTable1.TABLE_NAME,null,values);






    }

    public  void fillingQuestionsTB() //banco de preguntas
    {   //general
        Questions q1= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,"General");
        addQuestions(q1);

        Questions q2= new Questions("Which animal can be seen on the Porsche logo?","Fish","Bird","Horse","cat",3,"General");
        addQuestions(q2);

        Questions q3= new Questions("What is the name of the largest ocean on earth?","Atlantic","Pacific","Cauca","Black sea",2,"General");
        addQuestions(q3);

        Questions q4= new Questions("Demolition of the Berlin wall separating East and West Germany began in what year?","1989","2015","1915","1935",1,"General");
        addQuestions(q4);

        Questions q5= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,"General");
        addQuestions(q5);


        //maths
        Questions q6= new Questions("Which number is equivalent to 3^(4)÷3^(2)","9","150","300","450",1,"Maths");
        addQuestions(q6);

        Questions q7= new Questions("I am an odd number. Take away one letter and I become even. What number am I?","One","Seven","Eight","Nine",2,"Maths");
        addQuestions(q7);

        Questions q8= new Questions("Using only an addition, how do you add eight 8’s and get the number 1000?","1000","2015","1915","125",1,"Maths");
        addQuestions(q8);

        Questions q9= new Questions("41 years ago, when Sally was 13 and her mother was 39","41 years ago","11 years ago","4 years ago","12 years ago",1,"Maths");
        addQuestions(q9);

        Questions q10= new Questions("How to get a number 100 by using four sevens (7’s) and a one (1)?"," 177 – 77 = 100"," 170 – 77 = 100"," 17 – 7 = 10"," 1 + 7 = 8",1,"Maths");
        addQuestions(q10);

        //history

        Questions q11= new Questions("What important document was signed in 1776?","Declaration of Independence","St. Louis, Missouri","George Washington","Neil Armstrong",1,"History");
        addQuestions(q11);

        Questions q12= new Questions(" Who invented the lightbulb?","John Adams","Thomas Edison","Samuel Wilson","Santiago Cifuentes",2,"History");
        addQuestions(q12);

        Questions q13= new Questions("What state seceded from Virginia in 1863?","North Virginia","West Virginia","South Virginia","East Virginia",1,"History");
        addQuestions(q13);

        Questions q14= new Questions(" How old was Queen Elizabeth II when she became queen?","27 years old","37 years old","47 years old","57 years old",1,"History");
        addQuestions(q14);

        Questions q15= new Questions("Which group of people once ruled Norway?","Vikings"," Christians","Jews"," Nazis",1,"History");
        addQuestions(q15);


    }



    @SuppressLint("Range")
    public ArrayList<Questions> getAllQuestions() //método para crear un arraylist y guardar en él las preguntas y asignárselas a cada columna
    {
        ArrayList<Questions> questionsList = new ArrayList<>();

        db = getReadableDatabase();
        String[] Projection =
                {
                        Config.QuestionsTable1._ID,
                        COLUMN_QUESTION,
                        COLUMN_OPTION1,
                        COLUMN_OPTION2,
                        COLUMN_OPTION3,
                        COLUMN_OPTION4,
                        COLUMN_ANSWER,
                        COLUMN_CATEGORY,
                };

        @SuppressLint("Recycle")
        Cursor cursor = db.query
                (TABLE_NAME,Projection,null,
                        null,null,null,null);//todos estos parámetros van null ya que no se necesitan en las querys


        if (cursor.moveToFirst())//en las siguientes línea de código se agrega el banco de preguntas
        {
            do
            {
                Questions questions = new Questions();
                questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                questions.setOption1(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1)));
                questions.setOption2(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2)));
                questions.setOption3(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3)));
                questions.setOption4(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION4)));
                questions.setAnswer(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));
                questions.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));

                questionsList.add(questions);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  questionsList;
    }


    @SuppressLint("Range")
    public ArrayList<Questions> getAllQuestionsWithCategory(String category) //método para crear un arraylist y guardar en él las preguntas por categoría y asignárselas a cada columna
    {
        ArrayList<Questions> questionsList = new ArrayList<>();

        db = getReadableDatabase();
        String[] Projection =
                {
                        Config.QuestionsTable1._ID,
                        COLUMN_QUESTION,
                        COLUMN_OPTION1,
                        COLUMN_OPTION2,
                        COLUMN_OPTION3,
                        COLUMN_OPTION4,
                        COLUMN_ANSWER,
                        COLUMN_CATEGORY,
                };

        String selection = COLUMN_CATEGORY + " = ? ";
        String[] categorySelection = {category} ;


        @SuppressLint("Recycle")
        Cursor cursor = db.query
                (TABLE_NAME,Projection,selection,
                        categorySelection,null,null,null);//todos estos parámetros van null ya que no se necesitan en las querys


        if (cursor.moveToFirst())//en las siguientes línea de código se agrega el banco de preguntas
        {
            do
            {
                Questions questions = new Questions();
                questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                questions.setOption1(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1)));
                questions.setOption2(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2)));
                questions.setOption3(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3)));
                questions.setOption4(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION4)));
                questions.setAnswer(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));
                questions.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));

                questionsList.add(questions);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  questionsList;
    }





    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
