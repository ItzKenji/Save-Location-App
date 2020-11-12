package br.usjt.ucsist.savelocationusjtql.model;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.usjt.ucsist.savelocationusjtql.R;

public class LocalViewHolder extends RecyclerView.ViewHolder {
    TextView DataCadastro;
    TextView TextViewCEP;
    TextView TextViewRua;
    TextView TextViewBairro;
    TextView TextViewCidade;
    TextView TextViewEstado;
    TextView DadosDeLatitude;
    TextView DadosDeLongitude;

    LocalViewHolder(View raiz){
        super(raiz);
        DataCadastro = raiz.findViewById(R.id.DataCadastro);
        TextViewCEP = raiz.findViewById(R.id.TextViewCEP);
        TextViewRua = raiz.findViewById(R.id.TextViewRua);
        TextViewBairro = raiz.findViewById(R.id.TextViewBairro);
        TextViewCidade = raiz.findViewById(R.id.TextViewCidade);
        TextViewEstado = raiz.findViewById(R.id.TextViewEstado);
        DadosDeLatitude = raiz.findViewById(R.id.DadosDeLatitude);
        DadosDeLongitude = raiz.findViewById(R.id.DadosDeLongitude);
    }
}
