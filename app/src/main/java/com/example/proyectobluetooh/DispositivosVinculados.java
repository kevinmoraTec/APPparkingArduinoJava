package com.example.proyectobluetooh;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;
import java.util.Set;

public class DispositivosVinculados extends AppCompatActivity {

    //Depuración de LOGCAT
    private static final String TAG = "DispositivosVinculados";
    //Declaración de ListView
    ListView IdList;
    //String que se enviará a actividad principal, mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    //Declaración de campos
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_vinculados);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VerificarEstadoBT();
        //Inicializa la array que contendra la lista de los dispositivos bluetooth vinculados
        mPairedDevicesArrayAdapter = new ArrayAdapter(this, R.layout.dispositivos_encontrados);
        //Presenta los dispositivos vinculados en el listview
        IdList = findViewById(R.id.IdLista);
        IdList.setAdapter(mPairedDevicesArrayAdapter);
        IdList.setOnItemClickListener(mDeviceClickListener);
        //Obtiene el adaptador local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        //Adiciona un dispositivo emparejado al array
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }

    //Configura un (on-click) para la lista
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Obtener la dirección MAC del dispositivo que son los últimos 17 carácteres en la vista
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);
            finishAffinity();
//            Toast.makeText(getBaseContext(), "Dirección" + address, Toast.LENGTH_SHORT).show();

            //Realiza un intent para iniciar la siguiente actividad
            //Mientras toma un EXTRA_DEVICE_ADDRESS que es la dirección MAC
            Intent intent = new Intent(DispositivosVinculados.this, Actividades.class);
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            System.out.println(address);
            startActivity(intent);
        }
    };

    private void VerificarEstadoBT() {
        //Comprueba que el dispositivo tiene Bluetooth y que está encendido
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null)
        {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else{
            if (mBtAdapter.isEnabled()){
                Log.d(TAG, "Bluetooth Activado...");
            }else{
                //Solicita al usuario que active Bluetooth
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            }
        }
    }
}