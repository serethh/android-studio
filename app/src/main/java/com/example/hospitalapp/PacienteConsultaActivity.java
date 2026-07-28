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

        // Nombre obligatorio
        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese el nombre del paciente");
            etNombre.requestFocus();
            return;
        }

        // Edad obligatoria
        if (TextUtils.isEmpty(edadStr)) {
            etEdad.setError("Ingrese la edad");
            etEdad.requestFocus();
            return;
        }

        // Diagnóstico obligatorio
        if (TextUtils.isEmpty(diagnostico)) {
            etDiagnostico.setError("Ingrese el diagnóstico");
            etDiagnostico.requestFocus();
            return;
        }

        // Nombre: solo letras y espacios
        if (!nombre.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$")) {
            etNombre.setError("El nombre solo puede contener letras");
            etNombre.requestFocus();
            return;
        }

        // Diagnóstico: no solo números
        if (diagnostico.matches("\\d+")) {
            etDiagnostico.setError("Ingrese un diagnóstico válido");
            etDiagnostico.requestFocus();
            return;
        }

        // Diagnóstico: caracteres permitidos
        if (!diagnostico.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ0-9.,;:()\\- ]+$")) {
            etDiagnostico.setError("El diagnóstico contiene caracteres no permitidos");
            etDiagnostico.requestFocus();
            return;
        }

        int edad;

        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            etEdad.setError("La edad debe ser un número");
            etEdad.requestFocus();
            return;
        }

        // Edad válida
        if (edad <= 0 || edad > 120) {
            etEdad.setError("Ingrese una edad válida");
            etEdad.requestFocus();
            return;
        }

        try {

            long idPacienteGuardado =
                    dbHelper.guardarPaciente(nombre, edad, diagnostico);

            if (idPacienteGuardado > 0) {

                Toast.makeText(this,
                        "Paciente guardado con id " + idPacienteGuardado,
                        Toast.LENGTH_SHORT).show();

                mostrarFragmentoConsulta(idPacienteGuardado);

            } else {

                Toast.makeText(this,
                        "Error al guardar paciente",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Toast.makeText(this,
                    "Ocurrió un error al guardar el paciente.",
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
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