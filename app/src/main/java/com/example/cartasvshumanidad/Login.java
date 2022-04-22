package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registrarme(View view)
    {
        Intent i= new Intent(this,Registro.class);
        startActivity(i);
    }
    public void RecuperarPassword(View view)
    {
        Intent i= new Intent(this,RecuperarPass.class);
        startActivity(i);
    }

    //La dejo aqui hasta que hagamos lo de la autenticación y tal para hacer pruebas
    public void Inicio (View view)
    {
        Intent i= new Intent(this,Inicio.class);
        startActivity(i);
    }
}