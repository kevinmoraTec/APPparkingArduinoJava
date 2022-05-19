package com.example.proyectobluetooh;

import static android.graphics.Color.WHITE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class PagoActivity extends DialogFragment {

    MaterialButton pago, cancel;
    TextView cargardatos;
    Handler bluetoothIn;
    ConnectedThread MyConexionBT;
    final int handlerState = 0;
    private static final UUID BITMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = null;
    public PagoActivity() {
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();



    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device)throws IOException{
        //Crea una conexión de salida segura para el dispositivo usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BITMODULEUUID);
    }

    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */

    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_pago, null);

        pago = (MaterialButton) v.findViewById(R.id.cobrar_button);
        cancel = (MaterialButton) v.findViewById(R.id.cancelar_button);
        cargardatos = v.findViewById(R.id.cargardatos);

        cargardatos.setText("SI ESTA ACTIVO ESTE CAMPO");

        builder.setView(v);

        pago.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MyConexionBT.write("1");
                                        Toast.makeText(getContext(), "El dispositivo no soporta Bluetooth.", Toast.LENGTH_LONG).show();
                                    }
                                }
        );

        cancel.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          MyConexionBT.write("1");
                                          dismiss();
                                      }
                                  }

        );

        return builder.create();
    }

    //Crea la clase que permite el evento conexión
    private class ConnectedThread extends Thread{
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket){
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }catch (IOException e){}
            inputStream = tmpIn;
            outputStream = tmpOut;
        }
        public  void run()
        {
            byte[] byte_in = new byte[1];
            //Se mantiene en modo escucha para determinar el ingreso de datos
            while (true){
                try {
                    inputStream.read(byte_in);
                    char ch = (char)byte_in[0];
                    bluetoothIn.obtainMessage(handlerState,ch).sendToTarget();
                }catch (IOException e){
                    break;
                }
            }
        }
        public void write(String input)
        {
            try {
                outputStream.write(input.getBytes());
            }catch (IOException e)
            {
                //Si no es posible enviar datos se cierra la conexión
                Toast.makeText(getContext(), "La Conexión Falló.", Toast.LENGTH_LONG).show();
                dismiss();
            }
        }
    }

}