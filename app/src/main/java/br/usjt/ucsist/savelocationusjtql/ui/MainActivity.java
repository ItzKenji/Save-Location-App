package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import 	android.util.Log;

import br.usjt.ucsist.savelocationusjtql.model.LocalAdapter;
import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;
import io.perfmark.Tag;


public class MainActivity extends AppCompatActivity {

    public static final String CEP_KEY = "cep";
    public static final String RUA_KEY = "rua";
    public static final String NUMERO_KEY = "numero";
    public static final String BAIRRO_KEY = "bairro";
    public static final String CIDADE_KEY = "cidade";
    public static final String ESTADO_KEY = "estado";
    private static final String TAG = "MyActivity";
    private RecyclerView cardsLocaisRecyclerView;
    private LocalAdapter adapter;
    private List<Local> locais;
    private CollectionReference locaisReference;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("Locais");

    private Button voltarHome;
    private EditText editTextCEP;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private Button adicionarLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardsLocaisRecyclerView = findViewById(R.id.cardsLocaisRecycleView);
        locais = new ArrayList<>();
        adapter = new LocalAdapter(locais, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        cardsLocaisRecyclerView.setLayoutManager(linearLayoutManager);
        cardsLocaisRecyclerView.setAdapter(adapter);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextEstado = findViewById(R.id.editTextEstado);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextCEP = findViewById(R.id.editTextCEP);
        editTextBairro = findViewById(R.id.editTextBairro);
        textViewLatitude = findViewById(R.id.DadosDeLatitude);
        textViewLongitude = findViewById(R.id.DadosDeLongitude);

        adicionarLocais = (Button) findViewById(R.id.buttonAdicionarLocais);
        adicionarLocais.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroDeLocaisActivity.class);
                startActivity(intent);
            }

        });

    }

}