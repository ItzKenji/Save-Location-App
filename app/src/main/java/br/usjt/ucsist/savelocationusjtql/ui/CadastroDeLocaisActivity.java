package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TextView DadosDeLongitude;
    private TextView DadosDeLatitude;
    private Button adicionarLocais;

    public static final String CEP_KEY = "cep";
    public static final String RUA_KEY = "rua";
    public static final String NUMERO_KEY = "numero";
    public static final String BAIRRO_KEY = "bairro";
    public static final String CIDADE_KEY = "cidade";
    public static final String ESTADO_KEY = "estado";
    private static final String TAG = "MyActivity";

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("Locais");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_locais);

    }

    public void completarCadastro( View view) {
        EditText cepView = (EditText) findViewById(R.id.editTextCEP);
        EditText ruaView = (EditText) findViewById(R.id.editTextRua);
        EditText numeroView = (EditText) findViewById(R.id.editTextNumero);
        EditText bairroView = (EditText) findViewById(R.id.editTextBairro);
        EditText cidadeView = (EditText) findViewById(R.id.editTextCidade);
        EditText estadoView = (EditText) findViewById(R.id.editTextEstado);

        String cepText = cepView.getText().toString();
        String ruaText = ruaView.getText().toString();
        String numeroText = numeroView.getText().toString();
        String bairroText = bairroView.getText().toString();
        String cidadeText = cidadeView.getText().toString();
        String estadoText = estadoView.getText().toString();

        if (cepText.isEmpty() || ruaText.isEmpty() || numeroText.isEmpty() || bairroText.isEmpty() || cidadeText.isEmpty() || estadoText.isEmpty()) {
            return;
        }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(CEP_KEY, cepText);
        dataToSave.put(RUA_KEY, ruaText);
        dataToSave.put(NUMERO_KEY, numeroText);
        dataToSave.put(BAIRRO_KEY, bairroText);
        dataToSave.put(CIDADE_KEY, cidadeText);
        dataToSave.put(ESTADO_KEY, estadoText);
        mDocRef.collection("users")
                .add(dataToSave)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



//    public boolean validarCampos(){
//        boolean valido = true;
//        if(editTextCEP.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "CEP inválido", Toast.LENGTH_SHORT).show();
//        }
//        if(editTextRua.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "Rua inválido", Toast.LENGTH_SHORT).show();
//        }
//        if(editTextNumero.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
//        }
//        if(editTextBairro.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "Bairro inválido", Toast.LENGTH_SHORT).show();
//        }
//        if(editTextCidade.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "Cidade inválido", Toast.LENGTH_SHORT).show();
//        }
//        if(editTextEstado.getText().toString().trim().length() == 0){
//            valido = false;
//            Toast.makeText(this, "Estado inválido", Toast.LENGTH_SHORT).show();
//        }
//        return  valido;
//    }

}