package br.usjt.ucsist.savelocationusjtql.model;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import br.usjt.ucsist.savelocationusjtql.R;

public class LocalViewHolder extends RecyclerView.ViewHolder {
    TextView DataCadastro;
    TextView TextViewTitulo;
    TextView TextViewRua;
    TextView TextViewBairro;
    TextView TextViewCidade;
    TextView TextViewEstado;
    TextView TextViewLatitude;
    TextView TextViewLongitude;


    LocalViewHolder(View raiz){

        super(raiz);
        this.DataCadastro = raiz.findViewById(R.id.DataCadastro);
        this.TextViewTitulo = raiz.findViewById(R.id.TextViewTitulo);
        this.TextViewRua = raiz.findViewById(R.id.TextViewRua);
        this.TextViewBairro = raiz.findViewById(R.id.TextViewBairro);
        this.TextViewCidade = raiz.findViewById(R.id.TextViewCidade);
        this.TextViewEstado = raiz.findViewById(R.id.TextViewEstado);
        this.TextViewLatitude = raiz.findViewById(R.id.DadosDeLatitude);
        this.TextViewLongitude = raiz.findViewById(R.id.DadosDeLongitude);

         raiz.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mLongClickListener.onItemLongClick(v, getAdapterPosition());
                return false;
            }

        });

    }

    public LocalViewHolder.LongClickListener mLongClickListener;
    public interface LongClickListener{
        void onItemLongClick(View v, int position);
    }

    public void setOnLongClickListener(LocalViewHolder.LongClickListener longClickListener){
        mLongClickListener = longClickListener;
    }

}
