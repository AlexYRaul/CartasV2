package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearPartida extends AppCompatActivity {
    //Atributos iniciales de la clase
    EditText editText;
    Button button;
    String playerName;
    //Atributos que se utilizaran para hacer referencia a la base de datos de Firebase
    FirebaseDatabase database;
    DatabaseReference playerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        //Relacionamos los atributos con sus respecivos editText y Button que estan en el Layout
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        //Instanciamos la base de datos de Firebase
        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");

        if(!playerName.equals("")){
            playerRef = database.getReference("players/" + playerName);
            addEventListener();
            playerRef.setValue("");
        }

        //Cada vez que el usuario pase por este metodo se creara un registro en la base de datos.
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

    //Este metedo es un evento, sirve para recoger el nombre del usuario para la partida
    private void addEventListener (){
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!playerName.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();
                    //Una vez recogidos los datos de arriba se pasa a la activity Arena.
                    startActivity(new Intent(getApplicationContext(), com.example.cartasvshumanidad.Arena.class));
                    finish();
                }
            }
            //En caso de que no se cumpla el metodo anterior se mostara un mensaje de error mediante un Toast
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setText("LOG IN");
                button.setEnabled(true);
                Toast.makeText(CrearPartida.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}