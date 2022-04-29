package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void goProfile(View view)
    {
        Intent i= new Intent(this,Profile.class);
        startActivity(i);
    }

    public void goNews(View view)
    {
        Intent i= new Intent(this,News.class);
        startActivity(i);
    }

    public void goLobby(View view)
    {
        Intent i= new Intent(this,Lobby.class);
        startActivity(i);
    }
}