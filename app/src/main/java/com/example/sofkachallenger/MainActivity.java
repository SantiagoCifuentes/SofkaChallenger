package com.example.sofkachallenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public EditText user,password;
    public Button login;
    public CheckBox remembUser;
    public String inputUser,inputPassword;
    public SharedPreferences prefe;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefe=getSharedPreferences("data",MODE_PRIVATE);
        editor= prefe.edit();



        user=findViewById(R.id.edt_username);
        password=findViewById(R.id.edt_password);
        remembUser=findViewById(R.id.login_check);
        login=findViewById(R.id.btn_login);

        login.setOnClickListener(view -> validator());


    }

    public void validator() // se realiza unas pequeñas validaciones
    {
        inputUser=user.getText().toString();
        inputPassword=password.getText().toString();

        if (inputUser.isEmpty())
        {
            user.setError("Usuario obligatorio");
        }

        else if(inputUser.length()>35)
        {
            user.setError("No se puede exceder 35 carácteres");
        }

        else if(inputPassword.isEmpty())
        {
            Toast.makeText(this, "Contraseña obligatoria", Toast.LENGTH_SHORT).show();
        }

        else if(inputPassword.length()>20)
        {
            Toast.makeText(this, "No se puede exceder 20 caracteres", Toast.LENGTH_SHORT).show();
        }



        else
        {


            prefe=getSharedPreferences("data",MODE_PRIVATE);
            editor= prefe.edit();

            editor.putString("user",this.getInputUser()); //me guarda los datos del usuario que se pone en user
            startActivity(new Intent(this,ExplainingActivity.class));


            if (remembUser.isChecked())
            {
                editor.putBoolean("status",true);
                editor.apply();
            }
        }


    }

    private String getInputUser()
    {
        return  inputUser;
    }

}