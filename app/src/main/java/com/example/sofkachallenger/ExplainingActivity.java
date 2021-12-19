package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
                startActivity(new Intent(this,GameActivity.class));
        });




    }
}