package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;
import br.usjt.ucsist.savelocationusjtql.model.LocalAdapter;

public class CadastroDeLocaisActivity extends AppCompatActivity {

    private RecyclerView cardsLocaisRecyclerView;
    private LocalAdapter adapter;
    private List<Local> locais;
    private CollectionReference locaisReference;

    private Button voltarHome;
    private EditText editTextCEP;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;

    private Local localCorrente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_locais);
        cardsLocaisRecyclerView = findViewById(R.id.cardsLocaisRecycleView); locais =
                new ArrayList<>();
        adapter = new LocalAdapter(locais, this);
        cardsLocaisRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        cardsLocaisRecyclerView.setLayoutManager(linearLayoutManager);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextEstado = findViewById(R.id.editTextEstado);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextCEP = findViewById(R.id.editTextCEP);
        editTextBairro = findViewById(R.id.editTextBairro);
    }


    public void confirmarCadastro(View view){
        if(validarCampos()){
            if(localCorrente == null){
                localCorrente = new Local();
            }
            localCorrente.setBairro(editTextBairro.getText().toString());
            localCorrente.setCep(editTextCEP.getText().toString());
            localCorrente.setCidade(editTextCidade.getText().toString());
            localCorrente.setEstado(editTextEstado.getText().toString());
            localCorrente.setNumero(editTextNumero.getText().toString());
            localCorrente.setRua(editTextRua.getText().toString());
            Toast.makeText(this, "Local salvo", Toast.LENGTH_SHORT).show();
            locaisReference.add(localCorrente);
        }
    }

    public boolean validarCampos(){
        boolean valido = true;
        if(editTextCEP.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "CEP inválido", Toast.LENGTH_SHORT).show();
        }
        if(editTextRua.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Rua inválido", Toast.LENGTH_SHORT).show();
        }
        if(editTextNumero.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
        }
        if(editTextBairro.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Bairro inválido", Toast.LENGTH_SHORT).show();
        }
        if(editTextCidade.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Cidade inválido", Toast.LENGTH_SHORT).show();
        }
        if(editTextEstado.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Estado inválido", Toast.LENGTH_SHORT).show();
        }
        return  valido;
    }

    private void setupFirebase (){
        locaisReference = FirebaseFirestore.getInstance().collection("mensagens");
        getRemoteMsgs();
    }
    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();
    }

    private void getRemoteMsgs (){
        locaisReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                locais.clear();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                    Local incomingMsg = doc.toObject(Local.class);
                    locais.add(incomingMsg);
                }
                Collections.sort(locais);
                adapter.notifyDataSetChanged();
            }
        });
    }

}