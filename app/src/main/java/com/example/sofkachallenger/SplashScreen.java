package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try{
            SharedPreferences prefe = getSharedPreferences("data",MODE_PRIVATE);
            status = prefe.getBoolean("status",false); //se guardan los datos del usuario al darle recordar credenciales
            Thread.sleep(2000); // el tiempo que durará el splash screen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (status)startActivity(new Intent(this,ExplainingActivity.class));
        else
            startActivity(new Intent(this,MainActivity.class));

        finish();

    }
}