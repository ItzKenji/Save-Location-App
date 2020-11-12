package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

import br.usjt.ucsist.savelocationusjtql.R;
import br.usjt.ucsist.savelocationusjtql.model.Local;

public class CadastroDeLocaisActivity extends AppCompatActivity {

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

        card = findViewById(R.id.mensagensRecyclerView);
        mensagens = new ArrayList<>();
        adapter = new ChatAdapter(mensagens, this);
        mensagensRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mensagensRecyclerView.setLayoutManager(linearLayoutManager);
        mensagemEditText = findViewById(R.id.mensagemEditText);

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