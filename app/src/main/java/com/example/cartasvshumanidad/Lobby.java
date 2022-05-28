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

    private Button crear;


    private ListView listView;
    List<String> roomsList;
    String playerName="pruebarda1";
    String roomName="Pruebaca1";
    FirebaseDatabase dataBase;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        datos=getIntent().getExtras();
        correo=datos.getString("correo");
        pass=datos.getString("pass");

        crear=(Button) findViewById(R.id.fbtnCrear);

        dataBase = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        roomName=playerName;

        listView=findViewById(R.id.lvListaPartidas);
        roomsList=new ArrayList<>();


        listView.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomName=roomsList.get(position);
                roomRef=dataBase.getReference("rooms/"+roomName+"/player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });
        addRoomsEventListener();
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
                Toast.makeText(Lobby.this, "Creando partida..", Toast.LENGTH_SHORT).show();
                crear.setEnabled(false);
                roomName = playerName;
                roomRef=dataBase.getReference("rooms/"+roomName+"/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
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


    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Lobby.this, "Uniendose a una partida..", Toast.LENGTH_SHORT).show();
                crear.setEnabled(true);
                Intent i= new Intent(getApplicationContext(),Juego.class);
                i.putExtra("roomName", roomName);
                startActivity(i);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Lobby.this, "Uniendose a una partida..", Toast.LENGTH_SHORT).show();
                crear.setEnabled(true);
                Toast.makeText(Lobby.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomsEventListener(){
        roomsRef= dataBase.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : rooms){
                    roomsList.add(snapshot.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Lobby.this, android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }

}