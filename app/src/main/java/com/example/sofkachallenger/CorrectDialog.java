package com.example.sofkachallenger;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CorrectDialog
{
    private Context mContext;

    private Dialog correctDialog;

    private GameActivity migameActivity;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void correctDialog(int score, final GameActivity gameActivity)
    {

        migameActivity = gameActivity;

        correctDialog = new Dialog(mContext);
        correctDialog.setContentView(R.layout.final_score);

        Button btCorrectDilaog = (Button) correctDialog.findViewById(R.id.btn_finalScore);

        score(score);


        btCorrectDilaog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctDialog.dismiss(); //cierra el alert dialog
                migameActivity.showQuestions();
            }
        });

        correctDialog.show();
        correctDialog.setCancelable(false); // se pone falso ya que no se desea cancelar al apretar por ejemplo, el botón de atrás
        correctDialog.setCanceledOnTouchOutside(false); // esto indica que el cuadro de diálogo no se cerrará si se clickea en otra parte

        correctDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void score(int score) {

        TextView textViewScore = (TextView) correctDialog.findViewById(R.id.txtvwScore);
        textViewScore.setText("Score: " + String.valueOf(score));
    }

}
