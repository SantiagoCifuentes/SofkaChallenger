package com.example.sofkachallenger.db;

import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_ANSWER;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_CATEGORY;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION1;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION2;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION3;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_OPTION4;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.COLUMN_QUESTION;
import static com.example.sofkachallenger.db.Config.QuestionsTable1.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.sofkachallenger.Questions;

import java.util.ArrayList;
import java.util.List;

public  class  DbQuestions extends DbHelper
{
    SQLiteDatabase db;
    @SuppressLint("StaticFieldLeak")
    static Context context; //se pone static ya que, como solo se va a necesitar el método fillinquestions
    // nos ahorramos la creación de instancias u objetos del método anteriormente descrito

    public DbQuestions(Context context)
    {
        super(context);
        DbQuestions.context = context;

    }

   /* public static void fillingQuestionsTB()
    {
        Questions q1= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,1);
        insert(q1);

        Questions q2= new Questions("Which animal can be seen on the Porsche logo?","Fish","Bird","Horse","cat",3,1);
        insert(q2);

        Questions q3= new Questions("What is the name of the largest ocean on earth?","Atlantic","Pacific","Cauca","Black sea",2,1);
        insert(q3);

        Questions q4= new Questions("Demolition of the Berlin wall separating East and West Germany began in what year?","1989","2015","1915","1935",1,1);
        insert(q4);

        Questions q5= new Questions("What does “www” stand for in a website browser?","Third world war","World Wide Web","William Wallace Wagon","No one",2,1);
        insert(q5);



        Questions q6= new Questions("Which animal can be seen on the Porsche logo?","Fish","Bird","Horse","cat",3,2);
        insert(q6);

        Questions q7= new Questions("What is the name of the largest ocean on earth?","Atlantic","Pacific","Cauca","Black sea",2,2);
        insert(q7);

        Questions q8= new Questions("Demolition of the Berlin wall separating East and West Germany began in what year?","1989","2015","1915","1935",1,2);
        insert(q8);

    }*/


   /* public static void insert (Questions questions)
    {

        try //lo realizo en un try y catch para saber si se insertaron bien los datos o no
        {
            DbHelper dbHelper = new DbHelper(context); //constructor para poder utilizar los datos
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //al tratarse de un método para insertar datos, esta línea sirve para escribir datos en la bd,

            ContentValues values = new ContentValues();
            values.put(COLUMN_QUESTION, questions.getQuestion());
            values.put( COLUMN_OPTION1, questions.getOption1());
            values.put( COLUMN_OPTION2, questions.getOption2());
            values.put( COLUMN_OPTION3, questions.getOption3());
            values.put( COLUMN_OPTION4, questions.getOption4());
            values.put( COLUMN_ANSWER, questions.getAnswer());
            values.put(COLUMN_CATEGORY,questions.getCategory());

           // result = db.insert(TABLE_NAME,null,values);
            db.insert(TABLE_NAME,null,values);
            Toast.makeText(context, "Se creó la bd", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(context, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
      //  return  result;
    }*/



    /*public ArrayList<Questions>getAllQuestions() //método para crear un arraylist y guardar en él las preguntas y asignárselas a cada columna
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
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1)));
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2)));
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3)));
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION4)));
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER)));
                    questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));

                    questionsList.add(questions);
                }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  questionsList;
    }*/






}
