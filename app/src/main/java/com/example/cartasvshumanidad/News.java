package com.example.cartasvshumanidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cartasvshumanidad.adapters.NoticiasAdapter;
import com.example.cartasvshumanidad.models.Noticias;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class News extends AppCompatActivity {
    //Declaracion de atributos iniciales
    RecyclerView mRecycler;
    NoticiasAdapter mAdapter;
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //Creamos la instancia al Firestore de la base de daos de Firebase
        mFirestore = FirebaseFirestore.getInstance();
        //Relacionamos el atributo mrecycler con la activity layout done se crea el recycler View con el XML
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        //En esta parte se accede a la tabla de Firebase
        Query query = mFirestore.collection("Noticias").orderBy("fechaPublicacion");
        //En esta parte se lanza la sentencia y se recuperan los datos almacenados en Firebase
        FirestoreRecyclerOptions<Noticias> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Noticias>().setQuery(query, Noticias.class).build();
        mAdapter = new NoticiasAdapter(firestoreRecyclerOptions);
        //Este metodo notifica si se ha producido algun cambio en la base de datos.
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }

    //Metodo que se utiliza cuando se accede al recyclar View, mientras este en funcionamiento este metodo se queda esperando peticiones
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
    //Metodo que se pone en funconamiento cuando se cierra la aplicacion, se cierra la conexion
    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    //Este metodo sirve para ir a la la activity Profile
    public void profile(View view)
    {
        Intent i= new Intent(this,Profile.class);
        startActivity(i);
    }
    //Este metodo sirve para ir a la la activity Profile
    public void lobby(View view)
    {
        Intent i= new Intent(this,Lobby.class);
        startActivity(i);
    }

    //Constructor vacio de la clase News
    public News() {
    }
    //Constructor completo parametrizado de la clase
    public News(RecyclerView mRecycler, NoticiasAdapter mAdapter, FirebaseFirestore mFirestore) {
        this.mRecycler = mRecycler;
        this.mAdapter = mAdapter;
        this.mFirestore = mFirestore;
    }

    //Metodos Getter y Setter de la clase/////////////////////////////////////////////////////////

    public RecyclerView getmRecycler() {
        return mRecycler;
    }

    public void setmRecycler(RecyclerView mRecycler) {
        this.mRecycler = mRecycler;
    }

    public NoticiasAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(NoticiasAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public FirebaseFirestore getmFirestore() {
        return mFirestore;
    }

    public void setmFirestore(FirebaseFirestore mFirestore) {
        this.mFirestore = mFirestore;
    }

    //Este metodo devuelve todos los atributos de la clase en formato de cadena String
    @Override
    public String toString() {
        return "News{" +
                "mRecycler=" + mRecycler +
                ", mAdapter=" + mAdapter +
                ", mFirestore=" + mFirestore +
                '}';
    }
}