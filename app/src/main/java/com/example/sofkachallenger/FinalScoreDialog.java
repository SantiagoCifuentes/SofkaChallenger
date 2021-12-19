package com.example.sofkachallenger;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreDialog
{
    private Context mContext;
    private Dialog finalScoreDialog;
    TextView txtvwFinalScore;




    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog(int correctAns,int wrongAns, int totalSize)
    {
        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.final_score);

        final Button btn_finalScore = (Button) finalScoreDialog.findViewById(R.id.btn_finalScore);

        finalScoreValidations( correctAns, wrongAns, totalSize);

        btn_finalScore.setOnClickListener(view -> //al darle click al botón "ok", al finalizar la ronda, se cerrará el alert dialog y se llevará de nuevo a la explaining Activity
        {
            finalScoreDialog.dismiss();

            Intent intent = new Intent(mContext,ExplainingActivity.class);
            mContext.startActivity(intent);


        });

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);//se pone falso ya que no se desea cancelar al apretar por ejemplo, el botón de atrás
        finalScoreDialog.setCanceledOnTouchOutside(false);//esto indica que el cuadro de diálogo no se cerrará si se clickea en otra parte

    }

    private void finalScoreValidations(int correctAns, int wrongAns, int totalSize)

    {
        int tempScore;
        txtvwFinalScore = finalScoreDialog.findViewById(R.id.txtvwScore); // se le asigna los datos al textview creado en el layout final_score

        if (correctAns == totalSize) //cuando se finaliza el juego
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

        else if (wrongAns == totalSize) // cuando no se completa  el juego  o las preguntas son incorrectas
        {
            tempScore = 0;
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

        else  if (correctAns>wrongAns) // cuando hay más respuestas correctas que incorrectas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else  if (wrongAns>correctAns) // cuando hay más respuestas incorrectas que correctas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else  if (correctAns==wrongAns) // cuando hay igualdad en respuestsa correctas e incorrectas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }


    }
}
