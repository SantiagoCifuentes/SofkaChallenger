package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofkachallenger.db.DbHelper;

import com.example.sofkachallenger.db.Questions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

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

    int cont= 0 ;





    @SuppressLint("SetTextI18n")
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

    /*    intent = new Intent(this,GameActivity.class); // en estas 5(78-82) l??neas de c??digo se extrae los datos del contador de las cateogr??as, preguntas buenas y malas y el puntaje
        cont = getIntent().getExtras().getInt("cont");
        correctAnswer = getIntent().getExtras().getInt("correctAnswer");
        wrongAnswer = getIntent().getExtras().getInt("wrongAnswer");
        score = getIntent().getExtras().getInt("score");*/



        SharedPreferences prefs = getApplicationContext().getSharedPreferences("datos", MODE_PRIVATE); //shared preferences  para guardar los datos del contador de las cateogr??as, preguntas buenas y malas y el puntaje
        cont = prefs.getInt("cont", 0);                                                               // cada vez que nos salimos de la app
        correctAnswer = prefs.getInt("correctAnswer",0);
        wrongAnswer = prefs.getInt("wrongAnswer",0);
        score = prefs.getInt("score",0);
        questionCounter = prefs.getInt("questionCounter",1)-1; //se le resta 1 ya que por un bug , se estaba repitiendo la ??ltima pregunta



        idInitializers();

        txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer)); //
        txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));
        txtvwScore.setText("Score: " + String.valueOf(score));
        dbInitializer();

        btnLabelColor = rb1.getTextColors();

        finalScoreDialog = new FinalScoreDialog(this);


    }

    private void idInitializers()//m??todo que inicializa y llama a los ids que se van a necesitar en la activity
    {
        txtvwQuestions = findViewById(R.id.game_question);
        txtvwQuestionCount = findViewById(R.id.questions_count);
        txtvwScore = findViewById(R.id.game_score);
        txtvwCorrect = findViewById(R.id.game_CorrectAnsw);
        txtvwWrong = findViewById(R.id.game_wrongAnsw);


        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rdbtn_ans1);
        rb2 = findViewById(R.id.rdbtn_ans2);
        rb3 = findViewById(R.id.rdbtn_ans3);
        rb4 = findViewById(R.id.rdbtn_ans4);


        buttonNext = findViewById(R.id.btn_gameNext);


    }

    private void dbInitializer() {


        DbHelper dbHelper = new DbHelper(this);


        switch (cont) // se realiza en un switch para poder obtener las categor??as del modelado de la clase Questions.
        {
            case 1 :
                questionList = dbHelper.getAllQuestionsWithCategory("General");
                break;
            case 2 :
                questionList = dbHelper.getAllQuestionsWithCategory("Maths");
                break;
            case 3 :
                questionList = dbHelper.getAllQuestionsWithCategory("History");
                break;


        }


        startGame();

    } // incializa la base de datos

    private void startGame() {
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList); //aleatoriza la lista de preguntas
        if (questionCounter == questionTotalCount-1) //cuando se llega a la ??ltima pregunta, aun no est?? funcional esta parte, hay un bug
        {
            buttonNext.setText("Confirm and finish");
        }
        showQuestions();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) // al momento de seleccionar una respuesta correcta, se pondr?? de los colores que son
            // ya que tuve un error en el que al pasar de pregunta, igual se quedaba el color de la opci??n
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


        buttonNext.setOnClickListener(new View.OnClickListener() { //si no se se selecciona ning??na respuesta saldr?? el toast
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
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId()); //   se llama el id del radiogroup creado en el archivo.xml
        int answerNr = radioGroup.indexOfChild(rbSelected) + 1; // la clase indexofchild nos permite obtener el radiobutton seleccionado, se le suma +1 para que siempre inicie con el valor de 1, para que al entrar
                                                                // al m??todo checkSolution, entre de una por el case 1
        checkSolution(answerNr, rbSelected);
    } // m??todo que inicializa los raddiobuttons y al final se llama al m??todo checksolution para mostrar tanto las preguntas, como las respuestas, buenas y malas

    @SuppressLint("SetTextI18n")
    private void checkSolution(int answerNr, RadioButton rbSelected) {
        switch (currentQuestion.getAnswer()) // se va a validar que la pregunte est?? buena o no
        {
            case 1: // en cada case se evaluar?? si las preguntas est??n buenas o no. se le agregan los contadores de respuestas malas y buenas, un delay de 1 segundo al momento de pasar de pregunta
                if (currentQuestion.getAnswer() == answerNr) {

                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer));
                    correctAnswer++;//cuenta el numero de preguntas buenas y se lo agrega , en la l??nea de abajo, al textview
                    txtvwCorrect.setText("Correct: " + String.valueOf(correctAnswer));

                    score = (correctAnswer * 20) - (wrongAnswer * 5) ;
                    txtvwScore.setText("Score: " + String.valueOf(score)); //se le asigna el valor del score al textview


                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showQuestions(); // el m??todo showquestions se llama en cada if y else ya que se necesita que se actulice de nuevo todas las preguntas y se muestren en la activity
                        }
                    },1000);

                } else {
                    changetoIncorrectColor(rbSelected);
                    wrongAnswer++;// cuenta el numero de preguntas malas y se lo agrega , en la l??nea de abajo, al textview
                    txtvwWrong.setText("Wrong: " + String.valueOf(wrongAnswer));
                    score = (correctAnswer * 20) - (wrongAnswer * 5) ;
                    txtvwScore.setText("Score: " + String.valueOf(score));

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

                    score = (correctAnswer * 20) - (wrongAnswer * 5);
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

                    score = (correctAnswer * 20) - (wrongAnswer * 5) ;
                    txtvwScore.setText("Score: " + String.valueOf(score));

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

                    score = (correctAnswer * 20) - (wrongAnswer * 5);
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

                    score = (correctAnswer * 20) - (wrongAnswer * 5) ;
                    txtvwScore.setText("Score: " + String.valueOf(score));

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

                    score = (correctAnswer * 20) - (wrongAnswer * 5);
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

                    score = (correctAnswer * 20) - (wrongAnswer * 5) ;
                    txtvwScore.setText("Score: " + String.valueOf(score));

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
    } // en este m??todo se analiza las respuestas malas o buenas para cada pregunta

    private void changetoIncorrectColor(RadioButton rbSelected) {
        rbSelected.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer));
    } //este m??todo se utiliza cuando se selecciona una pregunta incorrecta

    @SuppressLint("SetTextI18n")
    public void showQuestions() {
        radioGroup.clearCheck(); //limpia lo seleccionado


        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background)); // en estas cuatro l??neas de c??digo se le agrega el drawable, el cual le aplica transparancia a el resto de radio buttons
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background)); // los cuales contienen las opciones de respuesta
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.botton_background));

        if (questionCounter < questionTotalCount) // se muestra la pregunta siempre y cuando no se haya llegado al l??mite de preguntas
        {
            currentQuestion = questionList.get(questionCounter); //obtiene el total de preguntas del arraylist


            txtvwQuestions.setText(currentQuestion.getCategory()+": "+currentQuestion.getQuestion()); // en esta l??nea  y las 4 de abajo, se le asigna los valores de la BD a las opciones que se van a mostrar

            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());


            questionCounter++;
            answer = false; //se necesita que por defecto est?? en false

            buttonNext.setText("Confirm");

            txtvwQuestionCount.setText("Questions : " + questionCounter + "/" + questionTotalCount);

        } else
        //si no, sse devuelve de nuevo a la actividad
        {
            totalSizeofQuestions = questionList.size();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //finalScoreDialog.finalScoreDialog(correctAnswer,wrongAnswer,totalSizeofQuestions);
                    if(cont >=3 )
                    {
                        finalScoreDialog.finalScoreDialog(correctAnswer,wrongAnswer,15); // cuando se llega al final del juego, se lanzara lo escrito en la clase
                        //FinalScoreDialog, la cual muestra un alertDialog con el puntaje total obtenido
                    }
                }
            }, 500);

            if(cont < 3) // se realiza este if para mandarle los datos  de las preguntas buenas, malas y puntaje a la siguiente categor??a
            {
                cont ++ ; //se incrementa la categor??a
               /* intent.putExtra("cont",cont);
                intent.putExtra("correctAnswer",correctAnswer);
                intent.putExtra("wrongAnswer",wrongAnswer);
                intent.putExtra("score",score);
*/
                SharedPreferences.Editor editor = getSharedPreferences("datos", MODE_PRIVATE).edit(); // de la l??inea 450 a la 457 se hace el setting para luego utilizar el shared preferences
                editor.putInt("cont", cont);
                editor.putInt("correctAnswer",correctAnswer);
                editor.putInt("wrongAnswer",wrongAnswer);
                editor.putInt("score",score);
                editor.putInt("questionCounter", 1);
                editor.apply();
                Intent intent = new Intent(this,GameActivity.class );
                startActivity(intent); //pasa a la siguiente categor??a
            }
        }
    }// m??todo que muestra y actualiza las preguntas y opciones de respuesta
    long count1=0;

    @Override
    public void onBackPressed() // m??todo en el cual al darle atr??s , nos saldr?? un aviso diciendo que volvamos a pulsar atr??s para salir de la app
                                //adem??s se prepara el editor para luego recuperar los datos en el on create con el shared preferences
    {


        SharedPreferences.Editor editor = getSharedPreferences("datos", MODE_PRIVATE).edit();
        editor.putInt("cont", cont);
        editor.putInt("correctAnswer",correctAnswer);
        editor.putInt("wrongAnswer",wrongAnswer);
        editor.putInt("score",score);
        editor.putInt("questionCounter", questionCounter);
        editor.apply();





        String backAgain=getString(R.string.onBackPressed);

        ConstraintLayout constraintLayout;
        constraintLayout= findViewById(R.id.ly_game_activity);


        if (count1+2000>System.currentTimeMillis()){
            super.finishAffinity();
        }        else{
            Snackbar.make(constraintLayout,backAgain,  Snackbar.LENGTH_SHORT).show();
            count1=System.currentTimeMillis();
        }
    }
}