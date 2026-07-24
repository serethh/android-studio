package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Menú principal mostrado tras un login exitoso. Da acceso a las
 * demás pantallas de la aplicación.
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String usuario = getIntent().getStringExtra("usuario");
        String rol = getIntent().getStringExtra("rol");

        TextView tvBienvenida = findViewById(R.id.tvBienvenida);
        tvBienvenida.setText("Hola, " + usuario + " (" + rol + ")");

        Button btnWifi = findViewById(R.id.btnWifi);
        Button btnUbicacion = findViewById(R.id.btnUbicacion);
        Button btnPaciente = findViewById(R.id.btnPaciente);
        Button btnLista = findViewById(R.id.btnLista);
        Button btnAdmin = findViewById(R.id.btnAdmin);

        btnWifi.setOnClickListener(v -> startActivity(new Intent(this, WifiActivity.class)));
        btnUbicacion.setOnClickListener(v -> startActivity(new Intent(this, LocationActivity.class)));
        btnPaciente.setOnClickListener(v -> startActivity(new Intent(this, PacienteConsultaActivity.class)));
        btnLista.setOnClickListener(v -> startActivity(new Intent(this, ListaPacientesActivity.class)));
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminBDActivity.class)));
    }
}
