package com.example.hospitalapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Fragment insertado dentro de PacienteConsultaActivity. Contiene la
 * información referente a la consulta (doctor, diagnóstico de la
 * consulta) y la captura de la hora de salida del paciente.
 */
public class ConsultaFragment extends Fragment {

    private static final String ARG_ID_PACIENTE = "id_paciente";

    private long idPaciente;
    private DatabaseHelper dbHelper;
    private Spinner spinnerMedico;
    private EditText etDiagnosticoConsulta;
    private TextView tvHoraSalida;
    private List<DatabaseHelper.Medico> listaMedicos;

    private String horaSalidaSeleccionada = "";

    public static ConsultaFragment newInstance(long idPaciente) {
        ConsultaFragment fragment = new ConsultaFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID_PACIENTE, idPaciente);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPaciente = getArguments().getLong(ARG_ID_PACIENTE);
        }
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consulta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerMedico = view.findViewById(R.id.spinnerMedico);
        etDiagnosticoConsulta = view.findViewById(R.id.etDiagnosticoConsulta);
        tvHoraSalida = view.findViewById(R.id.tvHoraSalida);
        Button btnSeleccionarHoraSalida = view.findViewById(R.id.btnSeleccionarHoraSalida);
        Button btnGuardarConsulta = view.findViewById(R.id.btnGuardarConsulta);

        listaMedicos = dbHelper.obtenerMedicos();
        ArrayAdapter<DatabaseHelper.Medico> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, listaMedicos);
        spinnerMedico.setAdapter(adapter);

        btnSeleccionarHoraSalida.setOnClickListener(v -> seleccionarHoraSalida());
        btnGuardarConsulta.setOnClickListener(v -> guardarConsulta());
    }

    private void seleccionarHoraSalida() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            horaSalidaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            tvHoraSalida.setText("Hora de salida: " + horaSalidaSeleccionada);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    private void guardarConsulta() {
        if (listaMedicos == null || listaMedicos.isEmpty()) {
            Toast.makeText(requireContext(), "No hay médicos registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        String diagnosticoConsulta = etDiagnosticoConsulta.getText().toString().trim();
        if (TextUtils.isEmpty(diagnosticoConsulta)) {
            Toast.makeText(requireContext(), "Ingresa el diagnóstico de la consulta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(horaSalidaSeleccionada)) {
            Toast.makeText(requireContext(), "Selecciona la hora de salida", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper.Medico medicoSeleccionado = (DatabaseHelper.Medico) spinnerMedico.getSelectedItem();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String fechaHoraAtencion = sdf.format(new Date());

        long idConsulta = dbHelper.guardarConsulta(idPaciente, medicoSeleccionado.id,
                fechaHoraAtencion, horaSalidaSeleccionada, diagnosticoConsulta);

        if (idConsulta > 0) {
            Toast.makeText(requireContext(), "Consulta registrada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Error al registrar la consulta", Toast.LENGTH_SHORT).show();
        }
    }
}
