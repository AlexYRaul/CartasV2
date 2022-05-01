package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    public void news (View view)
    {
        Intent i= new Intent(this,News.class);
        startActivity(i);
    }
    public void profile(View view)
    {
        Intent i= new Intent(this,Profile.class);
        startActivity(i);
    }
}