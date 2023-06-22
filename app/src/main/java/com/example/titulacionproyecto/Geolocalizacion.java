package com.example.titulacionproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Geolocalizacion extends AppCompatActivity implements OnMapReadyCallback {
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalizacion);

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
}