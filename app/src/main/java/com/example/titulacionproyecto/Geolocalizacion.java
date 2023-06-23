package com.example.titulacionproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.Random;

public class Geolocalizacion extends AppCompatActivity implements OnMapReadyCallback {
    private double latitude;
    private double longitude;
    public double costo, tiempo;
    public int co,ti;
    TextView txtCosto, txtTiempo;
    Button btnPagar;
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalizacion);
        txtCosto = findViewById(R.id.txtCosto);
        txtTiempo = findViewById(R.id.txtTiempo);
        btnPagar = findViewById(R.id.btnPagar);

        btnPagar.setEnabled(false);

        Random ran = new Random();
        ran.setSeed(System.currentTimeMillis());
        co = ran.nextInt(300);

        costo = co * 2.3;

        txtCosto.setText(String.valueOf(df.format(costo)));

        ran.setSeed(System.currentTimeMillis());
        ti = ran.nextInt(300000);
        final MiContador timer = new MiContador(ti,1000);
        timer.start();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin = new Intent(Geolocalizacion.this, Pagar_Servicio.class);
                startActivity(admin);
            }
        });

        // Obtener los valores de latitud y longitud pasados como extras
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Inicializar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Crear un objeto LatLng con la ubicación recibida
        LatLng location = new LatLng(latitude, longitude);

        // Agregar un marcador en la ubicación
        googleMap.addMarker(new MarkerOptions().position(location).title("Mi ubicación"));

        // Mover la cámara al lugar especificado y ajustar el zoom
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
    }


    public class MiContador extends CountDownTimer {

        public MiContador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //Lo que quieras hacer al finalizar
            btnPagar.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Sal a recibir a nuestros colaboradores y no olvides pagar sus sservicios :)", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //texto a mostrar en cuenta regresiva en un textview
            txtTiempo.setText((millisUntilFinished/1000+""));

        }
    }

}