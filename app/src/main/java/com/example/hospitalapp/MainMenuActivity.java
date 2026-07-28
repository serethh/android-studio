package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_menu);

        String usuario = getIntent().getStringExtra("usuario");
        String rol = getIntent().getStringExtra("rol");

        TextView tvBienvenida = findViewById(R.id.tvBienvenida);
        tvBienvenida.setText("Hola, " + usuario + " (" + rol + ")");

        Button btnWifi = findViewById(R.id.btnWifi);
        Button btnUbicacion = findViewById(R.id.btnUbicacion);
        Button btnPaciente = findViewById(R.id.btnPaciente);
        Button btnSalida = findViewById(R.id.btnSalida);
        Button btnLista = findViewById(R.id.btnLista);
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnMedico = findViewById(R.id.btnMedico);

        btnMedico.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrarMedicoActivity.class)));

        btnWifi.setOnClickListener(v -> startActivity(new Intent(this, WifiActivity.class)));
        btnUbicacion.setOnClickListener(v -> startActivity(new Intent(this, LocationActivity.class)));
        btnPaciente.setOnClickListener(v -> startActivity(new Intent(this, PacienteConsultaActivity.class)));
        btnSalida.setOnClickListener(v -> startActivity(new Intent(this, RegistrarSalidaActivity.class)));
        btnLista.setOnClickListener(v -> startActivity(new Intent(this, ListaPacientesActivity.class)));
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminBDActivity.class)));
    }
}
