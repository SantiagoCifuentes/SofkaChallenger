package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ExplainingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explaining);

        TextView user= findViewById(R.id.txtvwUser);

        SharedPreferences prefe= this.getSharedPreferences("data", Context.MODE_PRIVATE);

        String showUser=prefe.getString("user","");//aquí se guarda el usuario digitado en la activity del login
        user.setText(showUser);


        Button letsPlay= findViewById(R.id.btn_letsplay);

        letsPlay.setOnClickListener(view -> //estas líneas son para pasar de esta activity a la GameActivity
                //al darle click al boton "Let´s play"
        {
            Intent intent = new Intent(this,GameActivity.class);
            intent.putExtra("cont",1);
            intent.putExtra("correctAnswer",0);
            intent.putExtra("wrongAnswer",0);
            intent.putExtra("score",0);
            startActivity(intent);
        });




    }

    long count1=0;

    @Override
    public void onBackPressed()
    {
        String backAgain=getString(R.string.onBackPressed);

        ConstraintLayout constraintLayout;
        constraintLayout= findViewById(R.id.ly_explaining);


        if (count1+2000>System.currentTimeMillis()){
            super.finishAffinity();
        }        else{
            Snackbar.make(constraintLayout,backAgain,  Snackbar.LENGTH_SHORT).show();
            count1=System.currentTimeMillis();
        }
    }
}