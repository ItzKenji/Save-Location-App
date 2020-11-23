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
    private EditText editTextTitulo;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;
    private ImageView fotoLocal;
    private TextView linkFotoLocal;

    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private Button adicionarLocais;

    private static final String TAG = "MyActivity";

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("Locais");

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

        editTextTitulo = (EditText) findViewById(R.id.editTextTitulo);
        editTextRua = (EditText) findViewById(R.id.editTextRua);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
        fotoLocal = (ImageView) findViewById(R.id.fotoLocal);
        linkFotoLocal = (TextView) findViewById(R.id.linkFotoLocal);

        linkFotoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

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

    private void tirarFoto() {
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoLocal.setImageBitmap(imageBitmap);
            //contatoCorrente.setImagem(ImageUtil.encode(imageBitmap));
            //Log.d("IMAGEMBITMAPENCODED-->",contatoCorrente.getImagem());
        }

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

            if (tituloText.isEmpty() || ruaText.isEmpty() || numeroText.isEmpty() || bairroText.isEmpty() || cidadeText.isEmpty() || estadoText.isEmpty()) {
                return;
            }
            Map<String, Object> dataToSave = new HashMap<String, Object>();
            dataToSave.put("titulo", tituloText);
            dataToSave.put("rua", ruaText);
            dataToSave.put("numero", numeroText);
            dataToSave.put("bairro", bairroText);
            dataToSave.put("cidade", cidadeText);
            dataToSave.put("estado", estadoText);
            dataToSave.put("dadosLatitude", latitudeText);
            dataToSave.put("dadosLongitude", longitudeText);
            mDocRef.collection("Sla")
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
            Intent intent = new Intent(CadastroDeLocaisActivity.this, MainActivity.class);
            startActivity(intent);
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