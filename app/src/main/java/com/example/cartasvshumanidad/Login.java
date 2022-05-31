package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    TextView tvTextoNombre;
    Button btnNombre;
    EditText txtNombre;
    String PlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        correo = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);

    }


    public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popul_nick, null);
        this.tvTextoNombre=(TextView) contactPopupView.findViewById(R.id.tvEligeNick);
        this.btnNombre=(Button) contactPopupView.findViewById(R.id.btnNombre);
        this.txtNombre=(EditText) contactPopupView.findViewById(R.id.txtNombre);

        this.dialogBuilder.setView(contactPopupView);
        this.dialog = dialogBuilder.create();
        dialog.show();

        this.btnNombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlayerName = txtNombre.getText().toString();

                Intent intent = new Intent(Login.this, News.class);
                intent.putExtra("correo", strcorreo);
                intent.putExtra("nick", PlayerName);
                intent.putExtra("pass", strpassword);
                startActivity(intent);
                //Finalizar Activity
                finish();

                dialog.dismiss();
            }
        });
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
    public void pruebas (View view)
    {
        Intent i= new Intent(this, News.class);
        i.putExtra("correo", strcorreo);
        i.putExtra("pass", strpassword);
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

                    createNewContactDialog();

                }
                //En caso de error lanzo el toast indicando lo sucedido
                else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Impido que el usuario pueda volver a la anterior Activity
    @Override
    public void onBackPressed() {

    }
}