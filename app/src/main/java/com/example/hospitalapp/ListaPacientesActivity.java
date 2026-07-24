package com.example.hospitalapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Activity 5: Lista de Pacientes. Realiza un JOIN entre Pacientes,
 * Consultas y Medicos para mostrar cada paciente junto con el doctor
 * que lo atendió.
 */
public class ListaPacientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        RecyclerView recyclerView = findViewById(R.id.recyclerPacientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<DatabaseHelper.PacienteConDoctor> lista = dbHelper.obtenerPacientesConMedico();

        PacienteAdapter adapter = new PacienteAdapter(lista);
        recyclerView.setAdapter(adapter);
    }
}
