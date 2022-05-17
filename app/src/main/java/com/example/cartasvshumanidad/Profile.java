package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView mButtonSignOut = (TextView) findViewById(R.id.tvCerrarSesion);
        mAuth = FirebaseAuth.getInstance();

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAuth.signOut();
               startActivity(new Intent(Profile.this, Login.class));
               finish();
            }
        });
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




}