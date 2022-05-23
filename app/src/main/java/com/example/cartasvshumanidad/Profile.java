package com.example.cartasvshumanidad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;

    ImageView profile;
    Bundle datos;
    String correo;
    String password;
    TextView tvCorreo;

    //Creo esta variable para comprobrar con ella si el usuario ha dado permisos para acceder a la galeria
    private static final int REQUEST_PERMISSION_CODE = 100;

    //Creo esta variable para comprobrar con ella si el usuario ha seleccionado una imagen de la galeria
    private static final int REQUEST_IMAGE_GALLERY =101;

    private static final int File = 1;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView mButtonSignOut = (TextView) findViewById(R.id.tvCerrarSesion);
        mAuth = FirebaseAuth.getInstance();

        datos=getIntent().getExtras();
        correo=datos.getString("correo");
        password=datos.getString("pass");

        TextView submit = findViewById(R.id.tvBorrarCuenta);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteuser(correo, password);
            }
        });

        //Cierro la sesión del usuario
        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAuth.signOut();
               startActivity(new Intent(Profile.this, Login.class));
               finish();
            }
        });



        tvCorreo = findViewById(R.id.tvCorreo);
        tvCorreo.setText(correo);

        //Sincronizo la variable con la imagen del usuario en profile
        profile = findViewById(R.id.imgProfile);

        //Solicito permiso al usuario para acceder al almacenamiento interno del dispositivo y llamo al método openGallery
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        openGallery();
                    }else{
                        ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                    }
                }else{
                    openGallery();
                }
            }
        });
    }

    //Recogo la imagen de la galeria y la coloco en profile
    @Override
    public void onActivityResult (int requestcode, int resultCode, @Nullable Intent data){
        if(requestcode == REQUEST_IMAGE_GALLERY){
            if(resultCode == Activity.RESULT_OK && data != null){
                Uri photo = data.getData();
                profile.setImageURI(photo);
            }else{
                Toast.makeText(this, "No se recogió ninguna foto de la galeria",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestcode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult (int requestcode, @NonNull String[] permissions, @NonNull int [] grantResults){
        if (requestcode == REQUEST_PERMISSION_CODE){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else{
                Toast.makeText(this, "Necesitas habilitar los permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestcode, permissions, grantResults);
    }

    //Abro la galeria comprobando que tengo permisos
    private void openGallery()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }



    //Acceder a la Activity News
    public void news (View view)
    {
        Intent i= new Intent(this,News.class);
        startActivity(i);
    }

    //Acceder a la Activity Lobby
    public void Lobby(View view)
    {
        Intent i= new Intent(this,Lobby.class);
        startActivity(i);
    }

    public void borrarPerfil(View view)
    {
        Intent i= new Intent(this,BorrarPerfilUsuario.class);
        startActivity(i);
    }



    private void deleteuser(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "User account deleted.");
                                                startActivity(new Intent(Profile.this, Login.class));
                                                Toast.makeText(Profile.this, "Deleted User Successfully,", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }

}