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

        if (TextUtils.isEmpty(usuario)) {
            etUsuario.setError("Ingrese un usuario");
            etUsuario.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Ingrese una contraseña");
            etPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmar)) {
            etConfirmar.setError("Confirme la contraseña");
            etConfirmar.requestFocus();
            return;
        }

        if (usuario.contains(" ")) {
            etUsuario.setError("El usuario no debe contener espacios");
            etUsuario.requestFocus();
            return;
        }

        if (!usuario.matches("^[a-zA-Z0-9_]+$")) {
            etUsuario.setError("Solo se permiten letras, números y guion bajo (_)");
            etUsuario.requestFocus();
            return;
        }

        if (usuario.length() < 4) {
            etUsuario.setError("El usuario debe tener al menos 4 caracteres");
            etUsuario.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmar)) {
            etConfirmar.setError("Las contraseñas no coinciden");
            etConfirmar.requestFocus();
            return;
        }

        try {

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

        } catch (Exception e) {

            Toast.makeText(this,
                    "Ocurrió un error al crear la cuenta.",
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }
}