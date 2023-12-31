package com.example.titulacionproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Pagar_Servicio extends AppCompatActivity {

    Spinner spnEstrellas;
    Button btnAcabar;
    String [] opc1 = {"★", "★★", "★★★", "★★★★", "★★★★★"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_servicio);
        //relaciona el spinner y el boton
        spnEstrellas = findViewById(R.id.spnEstrellas);
        btnAcabar = findViewById(R.id.btnAcabar);

        //colocar los valores del spinner usando un arreglo
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opc1);
        spnEstrellas.setAdapter(adapter);


        btnAcabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Muchas gracias por su preferencia", Toast.LENGTH_SHORT).show();
                Intent admin = new Intent(Pagar_Servicio.this, Main3Activity.class);
                startActivity(admin);
            }
        });

    }
}