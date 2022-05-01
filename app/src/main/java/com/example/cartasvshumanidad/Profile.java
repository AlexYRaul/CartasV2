package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void news (View view)
    {
        Intent i= new Intent(this,News.class);
        startActivity(i);
    }
    public void Lobby(View view)
    {
        Intent i= new Intent(this,Lobby.class);
        startActivity(i);
    }

    public void CerrarSesion (View view)
    {
        Intent i= new Intent(this,Login.class);
        startActivity(i);
    }
}