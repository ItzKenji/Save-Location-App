package br.usjt.ucsist.savelocationusjtql.model;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

import br.usjt.ucsist.savelocationusjtql.R;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {

    private List<Local> locais;
    private Context context;
    private String name;
    private static ClickListener clickListener;

    public LocalAdapter(List<Local> locais, Context context){
        this.locais = locais;
        this.context = context;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_local, parent, false);
        return new LocalViewHolder(v);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        Local l = locais.get(position);
        //holder.DataCadastro.setText(context.getString(R.string.CdataDados, DateHelper.format(l.getDataCadastro())));
        holder.TextViewTitulo.setText(context.getString(R.string.CTitulo, l.getTitulo()));
        holder.TextViewRua.setText(context.getString(R.string.CRua, l.getRua()));
        holder.TextViewBairro.setText(context.getString(R.string.CBairro, l.getBairro()));
        holder.TextViewCidade.setText(context.getString(R.string.CCidade, l.getCidade()));
        holder.TextViewEstado.setText(context.getString(R.string.CEstado, l.getEstado()));
        holder.TextViewLatitude.setText(context.getString(R.string.ClatitudeDados, l.getDadosLatitude()));
        holder.TextViewLongitude.setText(context.getString(R.string.ClongitudeDados, l.getDadosLongitude()));

    }

    @Override
    public int getItemCount() {
        return locais.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LocalAdapter.clickListener = clickListener;
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView DataCadastro;
        TextView TextViewTitulo;
        TextView TextViewRua;
        TextView TextViewBairro;
        TextView TextViewCidade;
        TextView TextViewEstado;
        TextView TextViewLatitude;
        TextView TextViewLongitude;


        public LocalViewHolder(View raiz){
            super(raiz);
            this.DataCadastro = raiz.findViewById(R.id.DataCadastro);
            this.TextViewTitulo = raiz.findViewById(R.id.TextViewTitulo);
            this.TextViewRua = raiz.findViewById(R.id.TextViewRua);
            this.TextViewBairro = raiz.findViewById(R.id.TextViewBairro);
            this.TextViewCidade = raiz.findViewById(R.id.TextViewCidade);
            this.TextViewEstado = raiz.findViewById(R.id.TextViewEstado);
            this.TextViewLatitude = raiz.findViewById(R.id.DadosDeLatitude);
            this.TextViewLongitude = raiz.findViewById(R.id.DadosDeLongitude);
            raiz.setOnClickListener(this);
            raiz.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
}