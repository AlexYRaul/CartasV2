package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lobby extends AppCompatActivity {

    private String correo;
    private String pass;
    Bundle datos;

    EditText editText;
    Button button;
    String playerName;

    FirebaseDatabase database;
    DatabaseReference playerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        datos = getIntent().getExtras();
        correo = datos.getString("correo");
        pass = datos.getString("pass");

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");

        //Compruebo que el nombre del usuario no está vacio y creo en la base de datos el player con dicho nombre
        if(!playerName.equals("")){
            playerRef = database.getReference("players/" + playerName);
            addEventListener();
            playerRef.setValue("");
        }

        //Doy al editText el valor del nombre del usuario, compruebo que no esta vacio y hago referencia en la base de datos al usuario con dicho nombre
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                playerName = editText.getText().toString();
                editText.setText("");
                if (!playerName.equals("")){
                    button.setText("LOGGING IN");
                    button.setEnabled(false);
                    playerRef = database.getReference("players/" + playerName);
                    addEventListener();
                    playerRef.setValue("");
                }
            }
        });
    }

    //Recuerdo eñ nombre de usuario y me logueo con el
    private void addEventListener (){
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!playerName.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), com.example.cartasvshumanidad.Arena.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setText("LOG IN");
                button.setEnabled(true);
                Toast.makeText(Lobby.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Cambio de activity a News pasandole los valores del usuario
    public void news (View view) {
        Intent i= new Intent(this,News.class);
        i.putExtra("correo", correo);
        i.putExtra("nick", playerName);
        i.putExtra("pass", pass);
        startActivity(i);
    }
    //Cambio de activity a Profile pasandole los valores del usuario
    public void profile(View view) {
        Intent i= new Intent(this,Profile.class);
        i.putExtra("correo", correo);
        i.putExtra("nick", playerName);
        i.putExtra("pass", pass);
        startActivity(i);
    }

}