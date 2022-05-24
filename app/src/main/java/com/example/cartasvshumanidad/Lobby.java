package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Lobby extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tvPregunta;
    private Button aceptar;
    private Button cancel;
    private String correo;
    private String pass;
    Bundle datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        datos=getIntent().getExtras();
        correo=datos.getString("correo");
        pass=datos.getString("pass");
    }


    public void news (View view)
    {
        Intent i= new Intent(this,News.class);
        i.putExtra("correo", correo);
        i.putExtra("pass", pass);
        startActivity(i);
    }
    public void profile(View view)
    {
        Intent i= new Intent(this,Profile.class);
        i.putExtra("correo", correo);
        i.putExtra("pass", pass);
        startActivity(i);
    }

    public void CrearPartida(View view)
    {
        createNewContactDialog();
    }

    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        this.tvPregunta=(TextView) contactPopupView.findViewById(R.id.tvPregunta);
        this.aceptar=(Button) contactPopupView.findViewById(R.id.btnPopUpCrear);
        this.cancel=(Button) contactPopupView.findViewById(R.id.btnPopUpCancelar);

        this.dialogBuilder.setView(contactPopupView);
        this.dialog = dialogBuilder.create();
        dialog.show();

        this.aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(Lobby.this, Juego.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        this.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                dialog.dismiss();
            }
        });
    }

}