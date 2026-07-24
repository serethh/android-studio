package com.example.hospitalapp;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity 6: Visor de tablas de la BD. Un Spinner permite elegir
 * cualquiera de las 4 tablas y una TableLayout muestra dinámicamente
 * todas sus filas y columnas.
 */
public class AdminBDActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TableLayout tablaContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bd);

        dbHelper = new DatabaseHelper(this);
        Spinner spinnerTablas = findViewById(R.id.spinnerTablas);
        tablaContenedor = findViewById(R.id.tablaContenedor);

        String[] tablas = dbHelper.obtenerNombresTablas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tablas);
        spinnerTablas.setAdapter(adapter);

        spinnerTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarTabla(tablas[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // no-op
            }
        });

        if (tablas.length > 0) {
            mostrarTabla(tablas[0]);
        }
    }

    private void mostrarTabla(String nombreTabla) {
        tablaContenedor.removeAllViews();
        Cursor cursor = dbHelper.obtenerTabla(nombreTabla);
        if (cursor == null) return;

        // Fila de encabezado con los nombres de columna
        TableRow filaEncabezado = new TableRow(this);
        for (String columnName : cursor.getColumnNames()) {
            filaEncabezado.addView(crearCelda(columnName, true));
        }
        tablaContenedor.addView(filaEncabezado);

        // Filas con los datos de cada registro
        while (cursor.moveToNext()) {
            TableRow fila = new TableRow(this);
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String valor = cursor.getString(i);
                fila.addView(crearCelda(valor == null ? "" : valor, false));
            }
            tablaContenedor.addView(fila);
        }
        cursor.close();
    }

    private TextView crearCelda(String texto, boolean esEncabezado) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setPadding(24, 16, 24, 16);
        tv.setGravity(Gravity.START);
        if (esEncabezado) {
            tv.setTypeface(null, Typeface.BOLD);
            tv.setBackgroundColor(0xFF1565C0);
            tv.setTextColor(0xFFFFFFFF);
        } else {
            tv.setBackgroundColor(0xFFF2F2F2);
            tv.setTextColor(0xFF000000);
        }
        return tv;
    }
}
