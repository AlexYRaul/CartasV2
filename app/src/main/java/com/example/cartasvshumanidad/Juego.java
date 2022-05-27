package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Juego extends AppCompatActivity {

    int tiempo = 30000;
    TextView tvTiempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvTiempo = findViewById(R.id.tvTiempo);
        IniciarCuentaAtras();
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