package com.example.hospitalapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity 4: Registrar Paciente. Guarda un nuevo paciente en SQLite
 * y, una vez guardado, muestra dentro de la misma pantalla el
 * ConsultaFragment para capturar la consulta asociada a ese paciente.
 */
public class PacienteConsultaActivity extends AppCompatActivity {

    private EditText etNombre, etEdad, etDiagnostico;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_consulta);

        dbHelper = new DatabaseHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etDiagnostico = findViewById(R.id.etDiagnostico);
        Button btnGuardarPaciente = findViewById(R.id.btnGuardarPaciente);

        btnGuardarPaciente.setOnClickListener(v -> guardarPaciente());
    }

    private void guardarPaciente() {
        String nombre = etNombre.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();
        String diagnostico = etDiagnostico.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(edadStr)) {
            Toast.makeText(this, "Nombre y edad son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "La edad debe ser un número", Toast.LENGTH_SHORT).show();
            return;
        }

        long idPacienteGuardado = dbHelper.guardarPaciente(nombre, edad, diagnostico);

        if (idPacienteGuardado > 0) {
            Toast.makeText(this, "Paciente guardado con id " + idPacienteGuardado, Toast.LENGTH_SHORT).show();
            mostrarFragmentoConsulta(idPacienteGuardado);
        } else {
            Toast.makeText(this, "Error al guardar paciente", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarFragmentoConsulta(long idPaciente) {
        ConsultaFragment fragment = ConsultaFragment.newInstance(idPaciente);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
