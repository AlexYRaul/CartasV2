package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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

import java.lang.reflect.Array;

public class Juego extends AppCompatActivity {

    //Inidico el tiempo con el que quiero que inicie el contador
    int tiempo = 30000;
    TextView tvTiempo;

    String playerName = "";
    String roomName = "";
    String role = "";
    String message;
    FirebaseDatabase database;
    DatabaseReference messageRef;

    String strCarta1;
    String strCarta2;
    String strCarta3;
    String strCarta4;
    String strCarta5;
    String strCartaNegra;

    boolean recarganegra=true;
    boolean envio=false;
    boolean respuestas=false;

    String respuesta="";

    TextView carta1;
    TextView carta2;
    TextView carta3;
    TextView carta4;
    TextView carta5;
    TextView cartaNegra;

    //Estas variables contendrán un valor aleatorio y segun ellos se les asignará un valor a sus repectivas cartas
    int id_carta1;
    int id_carta2;
    int id_carta3;
    int id_carta4;
    int id_carta5;
    int id_cartaNegra;

    //Creo 2 array en los que se almacenaran las cartas blancas y negras
    String cartasBlancas [] = new String[21];
    String cartasNegras [] = new String[21];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Sincronizo el textview del tiempo con la variable que contiene el valor y lanzo el metodo de cuenta atras
        tvTiempo = findViewById(R.id.tvTiempo);
        IniciarCuentaAtras();

        //Mando ejecutarse los metodos que rellenan los array con las cartas
        if(recarganegra==true){
            rellenarArrayNegras();
            rellenarArrayBlancas();
        }

        //Sincronizo las cartas con las variables Textview
        cartaNegra = findViewById(R.id.tvFrase);
        cartaNegra.setText(strCartaNegra);
        cartaNegra.setTextColor(Color.parseColor("#222121"));
        carta1 = findViewById(R.id.tvCarta1);
        carta1.setText(strCarta1);
        carta2 = findViewById(R.id.tvCarta2);
        carta2.setText(strCarta2);
        carta3 = findViewById(R.id.tvCarta3);
        carta3.setText(strCarta3);
        carta4 = findViewById(R.id.tvCarta4);
        carta4.setText(strCarta4);
        carta5 = findViewById(R.id.tvCarta5);
        carta5.setText(strCarta5);

        //Crea la sala y otorga un rol a cada usuario
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
            } else {
                role = "guest";
            }

        }
        //Cada carta blanca tiene un Onclick que hace resaltar dicha carta, resetea el resto y pasa al otro jugador tu carta
        carta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carta1.setTextColor(Color.parseColor("#FFFFFF"));
                carta2.setTextColor(Color.parseColor("#000000"));
                carta3.setTextColor(Color.parseColor("#000000"));
                carta4.setTextColor(Color.parseColor("#000000"));
                carta5.setTextColor(Color.parseColor("#000000"));
                carta1.setEnabled(false);
                message = role +": "+ strCarta1;
                messageRef.setValue(message);
                envio=true;
                RespuestasDadas();
            }
        });
        carta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carta2.setTextColor(Color.parseColor("#FFFFFF"));
                carta1.setTextColor(Color.parseColor("#000000"));
                carta3.setTextColor(Color.parseColor("#000000"));
                carta4.setTextColor(Color.parseColor("#000000"));
                carta5.setTextColor(Color.parseColor("#000000"));
                carta2.setEnabled(false);
                message = role +": "+ strCarta2;
                messageRef.setValue(message);
                envio=true;
                RespuestasDadas();
            }
        });
        carta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carta3.setTextColor(Color.parseColor("#FFFFFF"));
                carta2.setTextColor(Color.parseColor("#000000"));
                carta1.setTextColor(Color.parseColor("#000000"));
                carta4.setTextColor(Color.parseColor("#000000"));
                carta5.setTextColor(Color.parseColor("#000000"));
                carta3.setEnabled(false);
                message = role +": "+ strCarta3;
                messageRef.setValue(message);
                envio=true;
                RespuestasDadas();
            }
        });
        carta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carta4.setTextColor(Color.parseColor("#FFFFFF"));
                carta2.setTextColor(Color.parseColor("#000000"));
                carta3.setTextColor(Color.parseColor("#000000"));
                carta1.setTextColor(Color.parseColor("#000000"));
                carta5.setTextColor(Color.parseColor("#000000"));
                carta4.setEnabled(false);
                message = role +": "+ strCarta4;
                messageRef.setValue(message);
                envio=true;
                RespuestasDadas();
            }
        });
        carta5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carta5.setTextColor(Color.parseColor("#FFFFFF"));
                carta2.setTextColor(Color.parseColor("#000000"));
                carta3.setTextColor(Color.parseColor("#000000"));
                carta4.setTextColor(Color.parseColor("#000000"));
                carta1.setTextColor(Color.parseColor("#000000"));
                carta5.setEnabled(false);
                message = role +": "+ strCarta5;
                messageRef.setValue(message);
                envio=true;
                RespuestasDadas();
            }
        });

        //Mustra que usuario se ha conectado
        messageRef = database.getReference("rooms/" + roomName + "/message");
        if(role.equals("host")){
            message = role+": ¡Jugador 1 se ha conectado! ";
        }
        if(role.equals("guest")){
            message = role+": ¡Jugador 2 se ha conectado! ";
        }
        messageRef.setValue(message);

        //Si eres el usuario host crea un onclick sobre la carta negra y pasa dicha carta al jugador 2 para que tengan la misma
        if(role.equals("host")){
            cartaNegra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartaNegra.setEnabled(false);
                    message = "negra: "+ strCartaNegra;
                    messageRef.setValue(message);
                    cartaNegra.setTextColor(Color.parseColor("#FFFFFF"));
                }
            });
        }

        addRoomEventListener();

    }

    //Captura la respuesta del otro usuario y lo amcena en la variable respuesta
    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (role.equals("host")) {
                    if (snapshot.getValue(String.class).contains("guest:")) {
                        carta1.setEnabled(true);
                        carta2.setEnabled(true);
                        carta3.setEnabled(true);
                        carta4.setEnabled(true);
                        carta5.setEnabled(true);
                        respuesta = snapshot.getValue(String.class).replace("guest:", "");

                        respuestas=true;

                    }
                } else {
                    if (snapshot.getValue(String.class).contains("host:")) {
                        carta1.setEnabled(true);
                        carta2.setEnabled(true);
                        carta3.setEnabled(true);
                        carta4.setEnabled(true);
                        carta5.setEnabled(true);
                        respuesta = snapshot.getValue(String.class).replace("host:", "");

                        respuestas=true;

                    }
                    if(snapshot.getValue(String.class).contains("negra:")){
                        strCartaNegra = snapshot.getValue(String.class).replace("negra:", "");
                        cartaNegra.setText(strCartaNegra);
                        cartaNegra.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageRef.setValue(message);
            }
        });
    }

    //Comprueba que ambos jugadores hayan respondido
    public void RespuestasDadas(){
        if(envio==true && respuestas==true){
            Intent intent = new Intent(Juego.this, ElegirGanador.class);
            startActivity(intent);
        }
    }

    //lleno el array "rellenarArrayBlancas" con los textos de algunas de las cartas, genero un numero aleatorío que será el que elija que carta tocará
    public void rellenarArrayBlancas(){
        cartasBlancas[0] = "Barack Obama";
        cartasBlancas[1] = "Txeroki";
        cartasBlancas[2] = "Esperanza Aguirre";
        cartasBlancas[3] = "Correrse en una piscina llena de lagrimas de niño";
        cartasBlancas[4] = "Un sombrero muy guay";
        cartasBlancas[5] = "Auschwitz";
        cartasBlancas[6] = "Tirarse un pedo e irse";
        cartasBlancas[7] = "Un pelo púbico desviado";
        cartasBlancas[8] = "Pelotas";
        cartasBlancas[9] = "Rito de apareamiento";
        cartasBlancas[10] = "Olor a viejos";
        cartasBlancas[11] = "Un terrorista gracioso";
        cartasBlancas[12] = "Fuego amigo";
        cartasBlancas[13] = "Frigo Pie";
        cartasBlancas[14] = "Keanu Reeves";
        cartasBlancas[15] = "Carne de vaca loca";
        cartasBlancas[16] = "Sean Connery";
        cartasBlancas[17] = "Sean Penn";
        cartasBlancas[18] = "El huracán Katrina";
        cartasBlancas[19] = "Cristopher Walken";
        cartasBlancas[20] = "Barack Obama";

        id_carta1 = (int) (Math.random()*20+0);
        id_carta2 = (int) (Math.random()*20+0);
        id_carta3 = (int) (Math.random()*20+0);
        id_carta4 = (int) (Math.random()*20+0);
        id_carta5 = (int) (Math.random()*20+0);

        strCarta1=cartasBlancas[id_carta1];
        strCarta2=cartasBlancas[id_carta2];
        strCarta3=cartasBlancas[id_carta3];
        strCarta4=cartasBlancas[id_carta4];
        strCarta5=cartasBlancas[id_carta5];

    }

    //lleno el array "rellenarArrayNegras" con los textos de algunas de las cartas, genero un numero aleatorío que será el que elija que carta tocará
    public void rellenarArrayNegras(){
        cartasNegras[0] = "Bebo para olvidar _______.";
        cartasNegras[1] = "_______: Bueno hasta la ultima gota.";
        cartasNegras[2] = "¿Que es ese sonido?";
        cartasNegras[3] = "¿A que huele?";
        cartasNegras[4] = "Durante el sexo, me gusta pensar en _______";
        cartasNegras[5] = "La medicina alternativa ahora usa las propiedades curativas de _______";
        cartasNegras[6] = "Mientras flipaba con ácido _______ se convirtó en _______";
        cartasNegras[7] = "Por culpa de un érror de marketing El corte ingles ha dejado de vender _______";
        cartasNegras[8] = "¿Por qué estoy pegajoso?";
        cartasNegras[9] = "Hoy el tio de Bricomania contruirá _______";
        cartasNegras[10] = "¿Que me traje de Marruecos?";
        cartasNegras[11] = "¿Qué ayuda a Obama a relajarse?";
        cartasNegras[12] = "_______ ¡Chocala!";
        cartasNegras[13] = "_______ It´s a trap!";
        cartasNegras[14] = "¿Qué le gusta Aznar?";
        cartasNegras[15] = "_______ ¡A que no puedes comer solo uno!";
        cartasNegras[16] = "La guerra sirve para _______";
        cartasNegras[17] = "¿Que es lo que no te gustaría encontrar en un plato de comida china?";
        cartasNegras[18] = "¿Cual es el próximo juguete del HappyMeal?";
        cartasNegras[19] = "Este año, en vez de carbón, papa noel traerá  _______ a los niños malos.";
        cartasNegras[20] = "Bebo para olvidar _______.";

        id_cartaNegra = (int) (Math.random()*20+0);
        strCartaNegra = cartasNegras[id_cartaNegra];
    }


    public void SalirPartida (View view){
        messageRef.child("rooms").child(roomName).removeValue();
        Intent i= new Intent(this,Arena.class);
        startActivity(i);
    }

    //Metodo que crea una cuenta atras desde que se entra en la activity
    private void IniciarCuentaAtras () {
        new CountDownTimer(tiempo, 1000) {

            @Override
            public void onTick(long l) {
                long segundosPendientes = l / 1000;
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