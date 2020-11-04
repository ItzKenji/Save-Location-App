package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;
import br.usjt.ucsist.savelocationusjtql.model.LocalViewModel;

public class CadastroDeLocaisActivity extends AppCompatActivity {

    private Button voltarHome;
    private EditText editTextCEP;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;

    private LocalViewModel localViewModel;
    private Local localCorrente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_locais);

        editTextCEP = findViewById(R.id.editTextCEP);
        editTextRua = findViewById(R.id.editTextRua);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEstado = findViewById(R.id.editTextEstado);

        localViewModel = new ViewModelProvider(this).get(LocalViewModel.class);
        localViewModel.getLocal().observe(this, new Observer<Local>() {
            @Override
            public void onChanged(@Nullable final Local local) {
                updateView(local);
            }
        });

        voltarHome = (Button) findViewById(R.id.buttonVoltar);
        voltarHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(CadastroDeLocaisActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

    }

    private void updateView(Local local){
        if(local != null && local.getId() > 0){
            localCorrente = local;
            editTextEstado.setText(local.getEstado());
            editTextCidade.setText(local.getCidade());
            editTextBairro.setText(local.getBairro());
            editTextNumero.setText(local.getNumero());
            editTextRua.setText(local.getRua());
            editTextCEP.setText(local.getCep());
        }
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
            localViewModel.insert(localCorrente);
            Toast.makeText(this, "Local salvo", Toast.LENGTH_SHORT).show();
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
}