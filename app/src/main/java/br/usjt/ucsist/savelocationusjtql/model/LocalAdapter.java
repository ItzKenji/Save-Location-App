package br.usjt.ucsist.savelocationusjtql.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.usjt.ucsist.savelocationusjtql.R;

public class LocalAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private List<Local> locais;
    private Context context;

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
        holder.DataCadastro.setText(context.getString(R.string.CdataDados, DateHelper.format(l.getDataCadastro())));
        holder.TextViewTitulo.setText(context.getString(R.string.CTitulo, l.getTitulo()));
        holder.TextViewRua.setText(context.getString(R.string.CRua, l.getRua()));
        holder.TextViewBairro.setText(context.getString(R.string.CBairro, l.getBairro()));
        holder.TextViewCidade.setText(context.getString(R.string.CCidade, l.getCidade()));
        holder.TextViewEstado.setText(context.getString(R.string.CEstado, l.getEstado()));
        holder.TextViewLatitude.setText(context.getString(R.string.ClatitudeDados, l.getDadosLatitude()));
        holder.TextViewLongitude.setText(context.getString(R.string.ClongitudeDados, l.getDadosLongitude()));

//        holder.TextViewLongitude.setText(l.getDadosLongitude());
//        holder.TextViewLatitude.setText(l.getDadosLatitude());
//        holder.TextViewEstado.setText(l.getEstado());
//        holder.TextViewCidade.setText(l.getCidade());
//        holder.TextViewBairro.setText(l.getBairro());
//        holder.TextViewRua.setText(l.getRua());
//        holder.TextViewCEP.setText(l.getCep());

    }

    @Override
    public int getItemCount() {
        return locais.size();
    }
}