package com.example.hospitalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** Adapter del RecyclerView usado en ListaPacientesActivity. */
public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.ViewHolder> {

    private final List<DatabaseHelper.PacienteConDoctor> lista;

    public PacienteAdapter(List<DatabaseHelper.PacienteConDoctor> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paciente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseHelper.PacienteConDoctor item = lista.get(position);
        holder.tvNombrePaciente.setText(item.nombrePaciente + " (" + item.edad + " años)");
        holder.tvDoctor.setText("Atendido por: " + item.nombreDoctor);
        holder.tvDiagnostico.setText("Diagnóstico: " + item.diagnosticoConsulta);
        holder.tvFecha.setText("Fecha: " + item.fechaAtencion);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePaciente, tvDoctor, tvDiagnostico, tvFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            tvDoctor = itemView.findViewById(R.id.tvDoctor);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnostico);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
