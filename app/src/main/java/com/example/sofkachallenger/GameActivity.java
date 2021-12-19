package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofkachallenger.db.DbHelper;
import com.example.sofkachallenger.db.DbQuestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {
   /* private int id_answers[] =
            {
                    R.id.rdbtn_ans1,R.id.rdbtn_ans2,R.id.rdbtn_ans3,R.id.rdbtn_ans4
            };*/

    RadioGroup radioGroup;
    TextView txtvwQuestions, txtvwQuestionCount,txtvwCorrect,txtvwScore,txtvwWrong,txtvwCountDown;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    Button buttonNext;

    public ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestion;
    private Boolean answer;

    private Handler handler = new Handler();

    private ColorStateList btnLabelColor;

    private int correctAnswer = 0, wrongAnswer = 0 ;

    private  FinalScoreDialog finalScoreDialog;

    private int totalSizeofQuestions = 0 ;

    int score = 0 ;

    private static  final long Countdown = 30000;
    private CountDownTimer countDownTimer;
    private  long timeLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        /*
        for (int i = 0 ; i < id_answers.length; i++)
        {
            RadioButton radioButton = (RadioButton) findViewById(id_answers[i]);
            radioButton.setText(String.format("Respuesta %d",i));

        }*/

        idInitializers();
        dbInitializer();

        btnLabelColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);


    }

    private void idInitializers()//método que inicializa y llama a los ids que se van a necesitar en la activity
    {
        txtvwQuestions = findViewById(R.id.game_question);
        txtvwQuestionCount = findViewById(R.id.questions_count);
        txtvwScore = findViewById(R.id.game_score);
        txtvwCorrect = findViewById(R.id.game_CorrectAnsw);
        txtvwWrong = findViewById(R.id.game_wrongAnsw);
        txtvwCountDown = findViewById(R.id.game_timer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rdbtn_ans1);
        rb2 = findViewById(R.id.rdbtn_ans2);
        rb3 = findViewById(R.id.rdbtn_ans3);
        rb4 = findViewById(R.id.rdbtn_ans4);


        buttonNext = findViewById(R.id.btn_gameNext);

    }

    private void dbInitializer() {

        DbHelper dbHelper = new DbHelper(this);

        questionList = dbHelper.getAllQuestions();

        startGame();

    } // incializa la base de datos

    private void startGame() {
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList); //aleatoriza la lista de preguntas
        showQuestions();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) // al momento de seleccionar una respuesta correcta, se pondrá de los colores que son
            // ya que tuve un error en el que al pasar de pregunta, igual se quedaba el color de la opción
            {
                switch (id) {
                    case R.id.rdbtn_ans1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        break;

                    case R.id.rdbtn_ans2:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        break;

                    case R.id.rdbtn_ans3:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));

                        break;

                    case R.id.rdbtn_ans4:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected));

                        break;


                }

            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() { //si no se se selecciona ningúna respuesta saldrá el toast
            @Override
            public void onClick(View view) {
                if (!answer) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        gameOperations();
                    } else {
                        Toast.makeText(GameActivity.this, "Plase select an option", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    } // inicializa las preguntas,

    private void gameOperations() {
        answer = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr = radioGroup.indexOfChild(rbSelected) + 1;
        checkSolution(answerNr, rbSelected);
    }

    private void checkSolution(int answerNr, RadioButton rbSelected) {
        switch (currentQuestion.getAnswer()) // se va a validar que la pregunte esté buena o no
        {
            case 1: //se repite el mismo método ya que este le agrega el delay por 1 segundo a las preguntas correctas o incorrectas
                if (currentQuestion.getAnswer() == answerNr) {

                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer));
                    correctAnswer++;
                    txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer)); //cuenta el numero de preguntas buenas

                    score += 10 ;
                    txtvwScore.setText("Score: " + String.valueOf(score));


                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);

                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAnswer++;
                    txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));

                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);

                    //handler.postDelayed(this::showQuestions, 500);
                }
                break;
            case 2:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer));

                    correctAnswer++;
                    txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer));

                    score += 10 ;
                    txtvwScore.setText("Score: " + String.valueOf(score));


                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAnswer++;
                    txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));

                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                }
                break;
            case 3:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer));

                    score += 10 ;
                    txtvwScore.setText("Score: " + String.valueOf(score));


                    correctAnswer++;
                    txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer));

                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAnswer++;
                    txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));

                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                }
                break;
            case 4:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer));

                    correctAnswer++;
                    txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer));

                    score += 10 ;
                    txtvwScore.setText("Score: " + String.valueOf(score));


                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAnswer++;
                    txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));

                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions();
                        }
                    },1000);
                }
                break;

        }

        if (questionCounter == questionTotalCount) //cuando se llega a la última pregunta
        {
            buttonNext.setText("Confirm and finish");
        }


    }

    private void changetoIncorrectColor(RadioButton rbSelected) {
        rbSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer));
    }

    public void showQuestions() {
        radioGroup.clearCheck(); //limpia lo seleccionado

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));

        if (questionCounter < questionTotalCount) // se muestra la pregunta siempre y cuando no se haya llegado al límite de preguntas
        {
            currentQuestion = questionList.get(questionCounter); //obtiene el total de preguntas del arraylist


            txtvwQuestions.setText(currentQuestion.getQuestion()); // en esta línea  y las 4 de abajo, se le asigna los valores de la BD a las opciones que se van a mostrar

            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());


            questionCounter++;
            answer = false;

            buttonNext.setText("Confirm");

            txtvwQuestionCount.setText("Questions : " + questionCounter + "/" + questionTotalCount);

        } else //si no, sse devuelve de nuevo a la actividad
        {
            totalSizeofQuestions = questionList.size();



            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalScoreDialog.finalScoreDialog(correctAnswer,wrongAnswer,totalSizeofQuestions);
                }
            }, 2000);
        }
    }


  /*  private void score(int score) {

        txtvwScore = findViewById(R.id.txtvwScore);
        txtvwScore.setText("Score: " + String.valueOf(score));
    }*/


    private void updateCountDownText()
    {
        int minutes = (int) (timeLeft/1000) / 60;
        int seconds = (int) (timeLeft/1000) % 60 ;

        //String castingTimer = String.format(Locale.getDefault(), "")
    }


}