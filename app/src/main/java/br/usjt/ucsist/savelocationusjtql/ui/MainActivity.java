package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import 	android.util.Log;
import android.widget.Toast;

import br.usjt.ucsist.savelocationusjtql.model.LocalAdapter;
import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;
import io.perfmark.Tag;


public class MainActivity extends AppCompatActivity {

    private RecyclerView cardsLocaisRecyclerView;
    public static LocalAdapter adapter;
    public static List<Local> locais;
    private FirebaseUser fireUser;
    private CollectionReference mMsgsReference;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("Locais");
    private Button adicionarLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardsLocaisRecyclerView = findViewById(R.id.cardsLocaisRecycleView);
        locais = new ArrayList<>();
        adapter = new LocalAdapter(locais, this);

        adapter.setOnItemClickListener(new LocalAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("RESULTADO", "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("RESULTADO", "onItemLongClick pos = " + position);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        cardsLocaisRecyclerView.setLayoutManager(linearLayoutManager);
        cardsLocaisRecyclerView.setAdapter(adapter);


        adicionarLocais = (Button) findViewById(R.id.buttonAdicionarLocais);
        adicionarLocais.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroDeLocaisActivity.class);
                startActivity(intent);
            }

        });

    }


    private void setupFirebase (){
        fireUser= FirebaseAuth.getInstance().getCurrentUser();
        mMsgsReference =FirebaseFirestore.getInstance ().collection("NewLocais");
        getRemoteMsgs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();
    }

    private void getRemoteMsgs (){
        mMsgsReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( @Nullable
                                        QuerySnapshot queryDocumentSnapshots,
                                          @Nullable
                                                 FirebaseFirestoreException e) {
                        locais.clear();
                        for(DocumentSnapshot doc:queryDocumentSnapshots.getDocuments()){
                            Local local = doc.toObject(Local.class);
                            locais .add(local);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    private void dataToDelete(){

        mDocRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MainActivity.this, R.string.notDeleted, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.delete_dialog);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataToDelete();
            }
        });
        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}