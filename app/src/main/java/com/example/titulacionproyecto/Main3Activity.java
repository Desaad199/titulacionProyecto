package com.example.titulacionproyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main3Activity extends AppCompatActivity {
    private Button btn;
    private Button locationButton;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 123;

    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView textView = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Aleo/Aleo-Regular.ttf");
        textView.setTypeface(typeface);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_name = user.getDisplayName();

        locationButton = findViewById(R.id.locationButton);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        TextView textViewName = findViewById(R.id.textView);
        textViewName.setText("Bienvenido " + user_name);
    }

    public void botonUbicacion(View v) {
        requestLocation();
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permisos de ubicación si no están concedidos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        // Obtener la última ubicación conocida
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            // Utiliza la latitud y longitud obtenidas como desees
            Toast.makeText(this, "Latitud: " + latitude + ", Longitud: " + longitude, Toast.LENGTH_SHORT).show();
            // Redirigir a la pantalla de mapa
            Intent intent = new Intent(Main3Activity.this, Geolocalizacion.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
        }
    }

    public void cerrarSesion(View v) {
        // Revocar los permisos de Google
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).revokeAccess();
        // Desconectar al usuario actualmente autenticado
        FirebaseAuth.getInstance().signOut();

        // Volver a la pantalla de inicio de sesión
        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpiar todas las actividades anteriores
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }
}