package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;
import br.usjt.ucsist.savelocationusjtql.model.LocalAdapter;

public class CadastroDeLocaisActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private TextView textViewLongitude;
    private TextView textViewLatitude;

    private static final String TAG = "MyActivity";

    FirebaseFirestore  firebaseFirestore = FirebaseFirestore.getInstance();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int GPS_REQUEST_PERMISSION_CODE = 1001;

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener
            );
        }
        else {
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GPS_REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                }
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 10, locationListener
                );
            } else {
                Toast.makeText(this, getString(R.string.gps_permission_denied),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_locais);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);
        textViewLatitude = findViewById(R.id.textViewLatitude);
        textViewLongitude = findViewById(R.id.textViewLongitude);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                textViewLatitude.setText(String.format("%f", latitude));
                textViewLongitude.setText(String.format("%f", longitude));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle
                    extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    public void completarCadastro( View view) {
        if(validarCampos()) {

            String tituloText = editTextTitulo.getText().toString();
            String ruaText = editTextRua.getText().toString();
            String numeroText = editTextNumero.getText().toString();
            String bairroText = editTextBairro.getText().toString();
            String cidadeText = editTextCidade.getText().toString();
            String estadoText = editTextEstado.getText().toString();
            String latitudeText = textViewLatitude.getText().toString();
            String longitudeText = textViewLongitude.getText().toString();

            String data  = dateFormat.format(new Date());

            Local localSave = new Local();


            localSave.setTitulo(tituloText);
            localSave.setRua(ruaText);
            localSave.setNumero(numeroText);
            localSave.setBairro(bairroText);
            localSave.setCidade(cidadeText);
            localSave.setEstado(estadoText);
            localSave.setDadosLatitude(latitudeText);
            localSave.setDadosLongitude(longitudeText);
            localSave.setDataCadastro(data);
            firebaseFirestore.collection("NewLocais")
                    .add(localSave)
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
            finish();
        }
    }

    public void voltarHome (View view){

        Intent intent = new Intent(CadastroDeLocaisActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public boolean validarCampos(){
        boolean valido = true;
        if(editTextTitulo.getText().toString().trim().length() == 0){
            valido = false;
            Toast.makeText(this, "Título inválido", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

}