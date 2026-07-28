package com.example.hospitalapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarMedicoActivity extends AppCompatActivity {

    private EditText etNombre, etEspecialidad, etCedula;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_medico);

        dbHelper = new DatabaseHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etEspecialidad = findViewById(R.id.etEspecialidad);
        etCedula = findViewById(R.id.etCedula);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarMedico());
    }

    private void registrarMedico() {

        String nombre = etNombre.getText().toString().trim();
        String especialidad = etEspecialidad.getText().toString().trim();
        String cedula = etCedula.getText().toString().trim();

        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese el nombre del médico");
            etNombre.requestFocus();
            return;
        }

        if (especialidad.isEmpty()) {
            etEspecialidad.setError("Ingrese la especialidad");
            etEspecialidad.requestFocus();
            return;
        }

        if (cedula.isEmpty()) {
            etCedula.setError("Ingrese la cédula profesional");
            etCedula.requestFocus();
            return;
        }

        if (!nombre.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$")) {
            etNombre.setError("El nombre solo puede contener letras");
            etNombre.requestFocus();
            return;
        }

        if (!especialidad.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$")) {
            etEspecialidad.setError("La especialidad solo puede contener letras");
            etEspecialidad.requestFocus();
            return;
        }

        if (!cedula.matches("\\d+")) {
            etCedula.setError("La cédula solo debe contener números");
            etCedula.requestFocus();
            return;
        }

        if (nombre.length() < 3) {
            etNombre.setError("Ingrese un nombre válido");
            etNombre.requestFocus();
            return;
        }

        if (especialidad.length() < 4) {
            etEspecialidad.setError("Ingrese una especialidad válida");
            etEspecialidad.requestFocus();
            return;
        }

        if (cedula.length() < 7 || cedula.length() > 10) {
            etCedula.setError("La cédula debe tener entre 7 y 10 dígitos");
            etCedula.requestFocus();
            return;
        }

        try {

            long id = dbHelper.guardarMedico(
                    nombre,
                    especialidad,
                    cedula);

            if (id != -1) {

                Toast.makeText(this,
                        "Médico registrado correctamente",
                        Toast.LENGTH_SHORT).show();

                finish();

            } else {

                Toast.makeText(this,
                        "Error al registrar el médico",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Toast.makeText(this,
                    "Ocurrió un error al registrar el médico.",
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
}