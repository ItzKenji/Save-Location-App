package br.usjt.ucsist.savelocationusjtql.ui;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.usjt.ucsist.savelocationusjtql.R;


public class MainActivity extends AppCompatActivity {

    private Button adicionarLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adicionarLocais = (Button) findViewById(R.id.buttonAdicionarLocais);
        adicionarLocais.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this, CadastroDeLocaisActivity.class);
               startActivity(intent);

            }

        });

    }



}