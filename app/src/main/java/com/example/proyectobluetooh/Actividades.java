package com.example.proyectobluetooh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Actividades extends AppCompatActivity {

    ImageView parquaderoactivity, iconoInformacion;
    TextView placaVehiculoET,nombreUsuarioET;


    //String que se enviará a actividad principal, mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        parquaderoactivity = findViewById(R.id.parquaderoactivity);
        iconoInformacion = findViewById(R.id.iconoInformacion);
        placaVehiculoET = findViewById(R.id.placaVehiculoET);
        nombreUsuarioET = findViewById(R.id.nombreUsuarioET);


        Bundle extras = getIntent().getExtras();
        String address = extras.getString(EXTRA_DEVICE_ADDRESS);



//        Toast.makeText(getBaseContext(), "Dirección" + address, Toast.LENGTH_SHORT).show();

        parquaderoactivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String placaIngresada= placaVehiculoET.getText().toString();
                String nombreConductor= nombreUsuarioET.getText().toString();
                if(placaIngresada.isEmpty() || nombreConductor.isEmpty()){
                    Toast.makeText(getBaseContext(), "Ingrese Datos" + "Nombre - Placa", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getBaseContext(), "Abrio ACTIVITY" + address, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Actividades.this, MainActivity.class);
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                    startActivity(intent);
                }

            }
        });

//        onff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Actividades.this, ApagadoEncendido.class);
//                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
//                startActivity(intent);
//            }
//        });

    }
}