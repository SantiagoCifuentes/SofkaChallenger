package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ExplainingActivity extends AppCompatActivity {

    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaining);

        TextView user = findViewById(R.id.txtvwUser);

        SharedPreferences prefe = this.getSharedPreferences("data", Context.MODE_PRIVATE);

        String showUser = prefe.getString("user", "");//aquí se guarda el usuario digitado en la activity del login
        user.setText(showUser);


        Button button = findViewById(R.id.btn_logout);

        button.setOnClickListener(view -> exitSession());

        Button letsPlay = findViewById(R.id.btn_letsplay);

        letsPlay.setOnClickListener(view -> //estas líneas son para pasar de esta activity a la GameActivity
                //al darle click al boton "Let´s play"
        {
            Intent intent = new Intent(this, GameActivity.class);
           /* intent.putExtra("cont",1);
            intent.putExtra("correctAnswer",0);
            intent.putExtra("wrongAnswer",0);
            intent.putExtra("score",0);*/
            startActivity(intent);
        });

        clearBtn = findViewById(R.id.btn_clear);
        clearBtn.setOnClickListener(view -> //al darle al botón clear se restaurará los valores
        {
            SharedPreferences.Editor editor = getSharedPreferences("datos", MODE_PRIVATE).edit();
            editor.putInt("cont", 1);
            editor.putInt("correctAnswer", 0);
            editor.putInt("wrongAnswer", 0);
            editor.putInt("score", 0);
            editor.putInt("questionCounter", 1);
            editor.apply();

            Toast.makeText(this, "Data cleared", Toast.LENGTH_SHORT).show();
        });


        button.setOnClickListener(view -> exitSession());


    }

    private void exitSession() {

        new MaterialAlertDialogBuilder(this) //al darle click en el botón log out saldrá ese alert dialog
                .setTitle("Do you want to log out?")
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    SharedPreferences prefer = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefer.edit();
                    editor.putBoolean("status", false);
                    editor.apply();

                    startActivity(new Intent(this, MainActivity.class));
                    super.finishAffinity();
                })
                .show();


    } //metodo para cerrar la sesión y volver a la pantall de login

    long count1 = 0;

    @Override
    public void onBackPressed() {
        String backAgain = getString(R.string.onBackPressed);

        ConstraintLayout constraintLayout;
        constraintLayout = findViewById(R.id.ly_game_activity);


        if (count1 + 2000 > System.currentTimeMillis()) {
            super.finishAffinity();
        } else {
            Snackbar.make(constraintLayout, backAgain, Snackbar.LENGTH_SHORT).show();
            count1 = System.currentTimeMillis();
        }
    }


}