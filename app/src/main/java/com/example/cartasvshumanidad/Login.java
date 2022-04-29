package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView correo;
    TextView password;
    String strcorreo;
    String strpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        correo = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);

    }

    public void registrarme(View view)
    {
        Intent i= new Intent(this,Registro.class);
        startActivity(i);
    }
    public void RecuperarPassword(View view)
    {
        Intent i= new Intent(this,RecuperarPass.class);
        startActivity(i);
    }

    //La dejo aqui hasta que hagamos lo de la autenticación y tal para hacer pruebas
    public void Inicio (View view)
    {
        Intent i= new Intent(this, Inicio.class);
        startActivity(i);
    }

    //Este metodo comprobará si el logueo es correcto y en caso que así sea invoco el método loginuser
    public void acceso(View view) {

        strcorreo = correo.getText().toString();
        strpassword = password.getText().toString();

        if(!strcorreo.isEmpty() && !strpassword.isEmpty()){

            loginuser();
        }
        else{
            Toast.makeText(Login.this, "Completa los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Este método logueará al usuario y en caso de que no ocurra ningun problema podrá acceder a principal jugar
    private void loginuser(){

        mAuth.signInWithEmailAndPassword(strcorreo, strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //En caso de no error invocamos el método inicio

                    Intent intent = new Intent(Login.this, Inicio.class);
                    startActivity(intent);
                    //Finalizar Activity
                    finish();

                }
                //En caso de error lanzo el toast indicando lo sucedido
                else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}