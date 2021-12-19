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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sofkachallenger.Questions;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
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


        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }



    private void addQuestions(Questions questions)
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

    public  void fillingQuestionsTB()
    {
        Questions q1= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,1);
        addQuestions(q1);

        Questions q2= new Questions("Which animal can be seen on the Porsche logo?","Fish","Bird","Horse","cat",3,1);
        addQuestions(q2);

        Questions q3= new Questions("What is the name of the largest ocean on earth?","Atlantic","Pacific","Cauca","Black sea",2,1);
        addQuestions(q3);

        Questions q4= new Questions("Demolition of the Berlin wall separating East and West Germany began in what year?","1989","2015","1915","1935",1,1);
        addQuestions(q4);

        Questions q5= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,1);
        addQuestions(q5);



        Questions q6= new Questions("Which animal can be seen on the Porsche logo?","Fish","Bird","Horse","cat",3,2);
        addQuestions(q6);

        Questions q7= new Questions("What is the name of the largest ocean on earth?","Atlantic","Pacific","Cauca","Black sea",2,2);
        addQuestions(q7);

        Questions q8= new Questions("Demolition of the Berlin wall separating East and West Germany began in what year?","1989","2015","1915","1935",1,2);
        addQuestions(q8);

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
                questions.setCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY)));

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
