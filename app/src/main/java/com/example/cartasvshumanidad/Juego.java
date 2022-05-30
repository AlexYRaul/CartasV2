package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Juego extends AppCompatActivity {

    int tiempo = 30000;
    TextView tvTiempo;
    Button button;
    String playerName ="";
    String roomName = "";
    String role  = "";
    String message = "";
    FirebaseDatabase dataBase;
    DatabaseReference messageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvTiempo = findViewById(R.id.tvTiempo);
        IniciarCuentaAtras();

        button = findViewById(R.id.btnPrueba);
        button.setEnabled(false);
        dataBase = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)){
                role = "host";
            } else {
                role ="guest";
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                message = role + ":Poked!";
                messageRef.setValue(message);
            }
        });

        messageRef = dataBase.getReference("rooms/" + roomName + "/message");
        message = role + "Poked!";
        messageRef.setValue(message);
        addRoomEventListener();

    }
        private void addRoomEventListener(){
            messageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(role.equals("host")){
                        if (snapshot.getValue(String.class).contains("guest")){
                            button.setEnabled(true);
                            Toast.makeText(Juego.this, "" +
                                    snapshot.getValue(String.class).replace("guest", ""), Toast.LENGTH_SHORT).show();
                        } else {
                            if (snapshot.getValue(String.class).contains("host")){
                                button.setEnabled(true);
                                Toast.makeText(Juego.this, "" +
                                        snapshot.getValue(String.class).replace("host", ""), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    messageRef.setValue(message);
                }
            });
        }

    private void IniciarCuentaAtras(){
        new CountDownTimer(tiempo, 1000){

            @Override
            public void onTick(long l) {
                long segundosPendientes=l/1000;
                tvTiempo.setText(segundosPendientes + "s");
            }

            @Override
            public void onFinish() {
                tvTiempo.setText("FIN");
                tiempo = 30000;
            }
        }.start();
    }
}