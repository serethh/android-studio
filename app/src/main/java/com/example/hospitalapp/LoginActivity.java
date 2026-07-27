package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity 1: Login. Valida usuario/password contra la tabla Usuarios
 * y navega al menú principal si son correctos.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvCrearCuenta = findViewById(R.id.tvCrearCuenta);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentarLogin();
            }
        });
        tvCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroUsuarioActivity.class);
            startActivity(intent);
        });
    }

    private void intentarLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        String rol = dbHelper.validarUsuario(usuario, password);
        if (rol != null) {
            Toast.makeText(this, "Bienvenido (" + rol + ")", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("rol", rol);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
