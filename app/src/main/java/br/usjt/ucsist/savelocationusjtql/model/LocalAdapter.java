package br.usjt.ucsist.savelocationusjtql.model;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.usjt.ucsist.savelocationusjtql.R;

public class LocalAdapter extends RecyclerView.Adapter<LocalViewHolder> {

    private List<Local> locais;
    private Context context;
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("Locais");
    private String name;

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

        holder.setOnLongClickListener(new LocalViewHolder.LongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                showDeleteDialog(name);
            }
        });

    }

    private void dataToDelete(){

        mDocRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, R.string.deleted, 2).show();
                }
                else Toast.makeText(context, R.string.notDeleted, 2).show();
            }
        });
        notifyDataSetChanged();
    }

    private void showDeleteDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    @Override
    public int getItemCount() {
        return locais.size();
    }
}