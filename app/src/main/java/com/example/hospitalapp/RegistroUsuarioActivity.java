package com.example.hospitalapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword, etConfirmar;
    private MaterialButton btnCrearCuenta;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        dbHelper = new DatabaseHelper(this);

        etUsuario = findViewById(R.id.etNuevoUsuario);
        etPassword = findViewById(R.id.etNuevaPassword);
        etConfirmar = findViewById(R.id.etConfirmarPassword);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmar = etConfirmar.getText().toString().trim();

        if (TextUtils.isEmpty(usuario) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmar)) {

            Toast.makeText(this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmar)) {
            Toast.makeText(this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.existeUsuario(usuario)) {
            Toast.makeText(this,
                    "Ese usuario ya existe",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = dbHelper.registrarUsuario(usuario, password);

        if (resultado != -1) {
            Toast.makeText(this,
                    "Cuenta creada correctamente",
                    Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this,
                    "No se pudo crear la cuenta",
                    Toast.LENGTH_SHORT).show();
        }
    }
}