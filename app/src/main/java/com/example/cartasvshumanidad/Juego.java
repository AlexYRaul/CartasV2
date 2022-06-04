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

import java.lang.reflect.Array;

public class Juego extends AppCompatActivity {

    int tiempo = 30000;
    TextView tvTiempo;

    Button button;
    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";
    FirebaseDatabase database;
    DatabaseReference messageRef;

    String strCarta1;
    String strCarta2;
    String strCarta3;
    String strCarta4;
    String strCarta5;
    String strCartaNegra;

    TextView carta1;
    TextView carta2;
    TextView carta3;
    TextView carta4;
    TextView carta5;
    TextView cartaNegra;

    int id_carta1;
    int id_carta2;
    int id_carta3;
    int id_carta4;
    int id_carta5;
    int id_cartaNegra;

    String cartasBlancas [] = new String[21];
    String cartasNegras [] = new String[21];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvTiempo = findViewById(R.id.tvTiempo);
        IniciarCuentaAtras();

        button = findViewById(R.id.button);
        button.setEnabled(false);

        rellenarArrayNegras();
        rellenarArrayBlancas();

        cartaNegra = findViewById(R.id.tvFrase);
        carta1 = findViewById(R.id.tvCarta1);
        carta2 = findViewById(R.id.tvCarta2);
        carta3 = findViewById(R.id.tvCarta3);
        carta4 = findViewById(R.id.tvCarta4);
        carta5 = findViewById(R.id.tvCarta5);


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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                message = role + ":Poked!";
                messageRef.setValue(message);
            }
        });
        messageRef = database.getReference("rooms/" + roomName + "/message");
        message = role + ":Poked!";
        messageRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (role.equals("host")) {
                    if (snapshot.getValue(String.class).contains("guest:")) {
                        button.setEnabled(true);
                        Toast.makeText(Juego.this, "" +
                                snapshot.getValue(String.class).replace("guest:", ""), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (snapshot.getValue(String.class).contains("host:")) {
                        button.setEnabled(true);
                        Toast.makeText(Juego.this, "" +
                                snapshot.getValue(String.class).replace("host:", ""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageRef.setValue(message);
            }
        });

    }

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

        carta1.setText(strCarta1);
        carta2.setText(strCarta2);
        carta3.setText(strCarta3);
        carta4.setText(strCarta4);
        carta5.setText(strCarta5);
    }

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
        cartaNegra.setText(strCartaNegra);
    }


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