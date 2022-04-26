package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarPass extends AppCompatActivity {
    //Atributos iniciales de la clase
    private EditText email;
    private Button recuperar;
    private ProgressDialog progressDialog;
    private String correo;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        email = findViewById(R.id.txtRecuperarEmail);
        recuperar = findViewById(R.id.btnConfirmar);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //Llamada al metodo de recuperar contraseña.
        getRecuperarPassword();
    }

    /**
     * Este metodo sirve para recuperar la contraseña del usuario
     */
    private void getRecuperarPassword() {
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = email.getText().toString().trim();
                if(!correo.isEmpty()){
                    progressDialog.setMessage("Espere un momento...");
                    //Si el usuario pincha cualquier lugar de la pantalla este metodo evita que la operacion sea cancelada.
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    //Se llama al metodo enviar correo.
                    enviarCorreo();
                } else{
                    //Si la tarea no se ha finalizado con exito se mostrará el siguiente mensaje.
                    Toast.makeText(getApplicationContext(),"No se ha podido enviar el correo correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Este metodo sirve para enviar un correo al usuario
     */
    private void enviarCorreo(){
        //Este metodo indica que el idioma en el que se manda el correo es el Español.
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Mostramos al usuario el mensaje que aparece en el metodo
                    Toast.makeText(getApplicationContext(), "Revisa tu correo para restaurar la contraseña", Toast.LENGTH_SHORT).show();
                    //Enviamos al usuario a la activity login para que se vuelva a loggear con la nueva contraseña
                    Intent enviarPrincipal = new Intent(RecuperarPass.this,Login.class);
                    startActivity(enviarPrincipal); //Lanzamos el intente
                    finish();
                } else {
                    //Si  la tarea no se ha realizado con exito mostrará el siguiente mensaje al usuario.
                    Toast.makeText(getApplicationContext(), "No se ha podido enviar el correo correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    Es metodo sirve para el boton cancelar
    devuelve al usuario a la Activity de Loggin
     */
    public void Cancelar (View view)
    {
        Intent i= new Intent(this,Login.class);
        startActivity(i);
    }

    /**
     * Constructor vacio de la clase RecuperarPass
     */
    public RecuperarPass() {
    }

    /**
     * Constructor completo parametrizado de la clase
     */
    public RecuperarPass(EditText email, Button recuperar, ProgressDialog progressDialog, String correo, FirebaseAuth auth) {
        this.email = email;
        this.recuperar = recuperar;
        this.progressDialog = progressDialog;
        this.correo = correo;
        this.auth = auth;
    }
    /*
      Metodos Getter y Setter de la clase
     */
    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public Button getRecuperar() {
        return recuperar;
    }

    public void setRecuperar(Button recuperar) {
        this.recuperar = recuperar;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * Este metodo muestra todos los atributos de la clase en forma de cadena String
     * @return devuelve los atributos de la clase en formato de cadena de texto
     */
    @Override
    public String toString() {
        return "RecuperarPass{" +
                "email=" + email +
                ", recuperar=" + recuperar +
                ", progressDialog=" + progressDialog +
                ", correo='" + correo + '\'' +
                ", auth=" + auth +
                '}';
    }
}