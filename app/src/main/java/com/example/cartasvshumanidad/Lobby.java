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

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tvPregunta;
    private Button aceptar;
    private Button cancel;
    private String correo;
    private String pass;
    Bundle datos;

    private ListView listView;
    List<String> roomsList;
    String playerName;
    String roomName="";
    FirebaseDatabase dataBase;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        datos = getIntent().getExtras();
        correo = datos.getString("correo");
        playerName = datos.getString("nick");
        pass = datos.getString("pass");

        dataBase = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        roomName = playerName;


}

    public void news (View view)
    {
        Intent i= new Intent(this,News.class);
        i.putExtra("correo", correo);
        i.putExtra("nick", playerName);
        i.putExtra("pass", pass);
        startActivity(i);
    }
    public void profile(View view)
    {
        Intent i= new Intent(this,Profile.class);
        i.putExtra("correo", correo);
        i.putExtra("nick", playerName);
        i.putExtra("pass", pass);
        startActivity(i);
    }


    public void jugar (View view)
    {
        Intent i= new Intent(this,CrearPartida.class);
        startActivity(i);
    }

}