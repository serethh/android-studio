package com.example.hospitalapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrarSalidaActivity extends AppCompatActivity {
    private Spinner spinnerPaciente;
    private TextView tvHoraEntrada;
    private TextView tvHoraSalida;
    private DatabaseHelper dbHelper;
    private List<DatabaseHelper.ConsultaPendiente> listaConsultas;
    private EditText etObservaciones;
    private String horaSalida = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_salida);

        dbHelper = new DatabaseHelper(this);

        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        tvHoraEntrada = findViewById(R.id.tvHoraEntrada);
        tvHoraSalida = findViewById(R.id.tvHoraSalida);
        etObservaciones = findViewById(R.id.etObservaciones);

        Button btnHora = findViewById(R.id.btnHora);
        Button btnRegistrar = findViewById(R.id.btnRegistrarSalida);

        cargarPacientes();

        spinnerPaciente.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent,
                                               android.view.View view,
                                               int position,
                                               long id) {

                        DatabaseHelper.ConsultaPendiente consulta =
                                listaConsultas.get(position);

                        tvHoraEntrada.setText(consulta.horaEntrada);
                    }

                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {
                    }
                });

        btnHora.setOnClickListener(v -> seleccionarHora());

        btnRegistrar.setOnClickListener(v -> registrarSalida());
    }

    private void cargarPacientes() {

        listaConsultas = dbHelper.obtenerConsultasPendientes();

        ArrayAdapter<DatabaseHelper.ConsultaPendiente> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.spinner_item,
                        listaConsultas);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerPaciente.setAdapter(adapter);

        if (!listaConsultas.isEmpty()) {
            tvHoraEntrada.setText(listaConsultas.get(0).horaEntrada);
        }
    }

    private void seleccionarHora() {

        Calendar c = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hour, minute) -> {

                    horaSalida = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            hour,
                            minute);

                    tvHoraSalida.setText("Hora de salida: " + horaSalida);

                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true);

        dialog.show();
    }

    private void registrarSalida() {

        try {

            if (listaConsultas == null || listaConsultas.isEmpty()) {
                Toast.makeText(this,
                        "No hay pacientes pendientes",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (spinnerPaciente.getSelectedItem() == null) {
                Toast.makeText(this,
                        "Seleccione un paciente",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper.ConsultaPendiente consulta =
                    (DatabaseHelper.ConsultaPendiente) spinnerPaciente.getSelectedItem();

            if (horaSalida.isEmpty()) {
                Toast.makeText(this,
                        "Debe seleccionar la hora de salida.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {

                SimpleDateFormat formato =
                        new SimpleDateFormat("HH:mm", Locale.getDefault());

                Date entrada = formato.parse(consulta.horaEntrada);
                Date salida = formato.parse(horaSalida);

                if (salida.before(entrada)) {

                    Toast.makeText(this,
                            "La hora de salida no puede ser menor que la hora de entrada.",
                            Toast.LENGTH_LONG).show();

                    return;
                }

            } catch (Exception e) {

                Toast.makeText(this,
                        "Error al validar la hora.",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            String observaciones =
                    etObservaciones.getText().toString().trim();

            if (observaciones.isEmpty()) {
                etObservaciones.setError("Ingrese las observaciones");
                etObservaciones.requestFocus();
                return;
            }

            if (observaciones.matches("\\d+")) {
                etObservaciones.setError("Las observaciones no pueden contener solo números");
                etObservaciones.requestFocus();
                return;
            }

            if (!observaciones.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ0-9.,;:()\\- ]+$")) {
                etObservaciones.setError("Las observaciones contienen caracteres no permitidos");
                etObservaciones.requestFocus();
                return;
            }

            boolean ok = dbHelper.registrarSalida(
                    consulta.idConsulta,
                    horaSalida,
                    observaciones);

            if (ok) {
                Toast.makeText(this,
                        "Salida registrada correctamente",
                        Toast.LENGTH_SHORT).show();

                finish();

            } else {
                Toast.makeText(this,
                        "Error al registrar la salida",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this,
                    "Ocurrió un error al registrar la salida.",
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
}