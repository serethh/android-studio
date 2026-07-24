package com.example.hospitalapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Activity 2: Detalles de Wi-Fi. Solicita el permiso de ubicación en
 * tiempo de ejecución (requerido por Android para leer el SSID) y
 * muestra SSID, BSSID, velocidad de enlace y dirección IP.
 */
public class WifiActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private TextView tvSsid, tvBssid, tvVelocidad, tvIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        tvSsid = findViewById(R.id.tvSsid);
        tvBssid = findViewById(R.id.tvBssid);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        tvIp = findViewById(R.id.tvIp);

        verificarPermisoYMostrar();
    }

    private void verificarPermisoYMostrar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mostrarDetallesWifi();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarDetallesWifi();
            } else {
                tvSsid.setText("SSID: permiso denegado");
            }
        }
    }

    private void mostrarDetallesWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifiManager == null) return;

        WifiInfo info = wifiManager.getConnectionInfo();
        if (info != null) {
            String ssid = info.getSSID();
            if (ssid != null) {
                ssid = ssid.replace("\"", "");
            }
            tvSsid.setText("SSID: " + ssid);
            tvBssid.setText("BSSID: " + info.getBSSID());
            tvVelocidad.setText("Velocidad de enlace: " + info.getLinkSpeed() + " Mbps");
            String ip = Formatter.formatIpAddress(info.getIpAddress());
            tvIp.setText("Dirección IP: " + ip);
        } else {
            tvSsid.setText("No hay conexión Wi-Fi activa");
        }
    }
}
