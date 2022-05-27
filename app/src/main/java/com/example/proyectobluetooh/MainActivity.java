package com.example.proyectobluetooh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Bundle datos;
    private Button btnEncenderTodoLuces,btnApagarTodoLuces,btnSitio1,btnSitio2,btnSitio3,btnSitio4,btnSitio5,listar;
    MaterialButton  cobrar;
    private EditText placaVehiculo;
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    //Identificador único de servicio- SPP UUID
    private static final UUID BITMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //String para la dirección MAC
    private static String address = null;
    String url;
    private RequestQueue requestQueue;
    private boolean presionado = false;
    String placaIngresada ="";
    String nombreConductor="";
    String lugarParqueadero="";
    String placaIngresadaBusqueda="";
    String datosObtenidosValorpordia="";
    String ip ="192.168.1.10";
    private String urlDriveraddActivo="http://"+ip+":4000/clientes";
    private String urlbuscarPlaca="http://"+ip+":4000/clientesPlaca";

    int contadorClick=0;
    int contadorClick2=0;
    int contadorClick3=0;
    int contadorClick4=0;
    int contadorClick5=0;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        datos=getIntent().getExtras();
        datosObtenidosValorpordia=datos.getString("VALOR_POR_DIA");




        bluetoothIn = new Handler(){
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg){
                if (msg.what == handlerState){

                }
            }
        };
        hideSystemUI();
        btnEncenderTodoLuces = findViewById(R.id.btnEncenderTodoLuces);
        btnApagarTodoLuces = findViewById(R.id.btnApagarTodoLuces);
        btnSitio1 = findViewById(R.id.btnSitio1);
        btnSitio2 = findViewById(R.id.btnSitio2);
        btnSitio3 = findViewById(R.id.btnSitio3);
        btnSitio4 = findViewById(R.id.btnSitio4);
        btnSitio5 = findViewById(R.id.btnSitio5);
        cobrar = findViewById(R.id.cobrar);
        placaVehiculo= (EditText) findViewById(R.id.placaIngresada);

        //imageViewPersona = findViewById(R.id.imageViewPersona);


        btAdapter =BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        Toast.makeText(getBaseContext(), "El Dato Enviando es  : "+datosObtenidosValorpordia, Toast.LENGTH_LONG).show();

        btnEncenderTodoLuces.setOnClickListener(v -> {

            Toast.makeText(getBaseContext(), "Enviando 1", Toast.LENGTH_LONG).show();
            MyConexionBT.write("x");


        });

        btnApagarTodoLuces.setOnClickListener(v -> {
            MyConexionBT.write("y");
        });

        btnSitio1.setOnClickListener(v -> {
            //MyConexionBT.write("1");

            contadorClick++;
            if (contadorClick == 1){
                    onCreateDialogCobrar();
                cambiarColorBotonesParkeadero(btnSitio1,R.color.ParqueaderoOcupado);
                btnSitio1.setText("OCUPADO");
            }else if (contadorClick == 2){
                buscarPlaca();
                Toast.makeText(MainActivity.this,"-> "+contadorClick,Toast.LENGTH_SHORT).show();
                cambiarColorBotonesParkeadero(btnSitio1,R.color.white);
                contadorClick =0;
                MyConexionBT.write("a");
                btnSitio1.setText("Sitio 1");
            }

        });

//

        btnSitio2.setOnClickListener(v -> {
            //MyConexionBT.write("1");

            contadorClick2++;
            if (contadorClick2 == 1){
                onCreateDialogCobrar();
                cambiarColorBotonesParkeadero(btnSitio2,R.color.ParqueaderoOcupado);
                btnSitio2.setText("OCUPADO");
            }else if (contadorClick2 == 2){
                buscarPlaca();
                Toast.makeText(MainActivity.this,"-> "+contadorClick2,Toast.LENGTH_SHORT).show();
                cambiarColorBotonesParkeadero(btnSitio2,R.color.white);
                contadorClick2 =0;
                MyConexionBT.write("b");
                btnSitio2.setText("Sitio 2");
            }
        });

        btnSitio3.setOnClickListener(v -> {
            //MyConexionBT.write("1");

            contadorClick3++;
            if (contadorClick3 == 1){
                onCreateDialogCobrar();
                cambiarColorBotonesParkeadero(btnSitio3,R.color.ParqueaderoOcupado);
                btnSitio3.setText("OCUPADO");
            }else if (contadorClick3 == 2){
                buscarPlaca();
                Toast.makeText(MainActivity.this,"-> "+contadorClick3,Toast.LENGTH_SHORT).show();
                cambiarColorBotonesParkeadero(btnSitio3,R.color.white);
                contadorClick3 =0;
                MyConexionBT.write("c");
                btnSitio3.setText("Sitio 3");
            }
        });

        btnSitio4.setOnClickListener(v -> {
            //MyConexionBT.write("1");

            contadorClick4++;
            if (contadorClick4 == 1){
                onCreateDialogCobrar();
                cambiarColorBotonesParkeadero(btnSitio4,R.color.ParqueaderoOcupado);
                btnSitio4.setText("OCUPADO");
            }else if (contadorClick4 == 2){
                buscarPlaca();
                Toast.makeText(MainActivity.this,"-> "+contadorClick4,Toast.LENGTH_SHORT).show();
                cambiarColorBotonesParkeadero(btnSitio4,R.color.white);
                contadorClick4 =0;
                MyConexionBT.write("d");
                btnSitio4.setText("Sitio 4");
            }
        });

        btnSitio5.setOnClickListener(v -> {
            //MyConexionBT.write("1");

            contadorClick5++;
            if (contadorClick5 == 1){
                onCreateDialogCobrar();
                cambiarColorBotonesParkeadero(btnSitio5,R.color.ParqueaderoOcupado);
                btnSitio5.setText("OCUPADO");
            }else if (contadorClick5 == 2){
                buscarPlaca();
                Toast.makeText(MainActivity.this,"-> "+contadorClick5,Toast.LENGTH_SHORT).show();
                cambiarColorBotonesParkeadero(btnSitio5,R.color.white);
                contadorClick5 =0;
                MyConexionBT.write("e");
                btnSitio5.setText("Sitio 5");
            }
        });

        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConexionBT.write("z");
               // new PagoActivity().show(getSupportFragmentManager(), "PagoActivity");

            }
        });



    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device)throws IOException{
        //Crea una conexión de salida segura para el dispositivo usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BITMODULEUUID);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        address = intent.getStringExtra(DispositivosVinculados.EXTRA_DEVICE_ADDRESS);
        //Setea la dirección MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        }catch (IOException e){
            Toast.makeText(getBaseContext(), "La creación del Socket falló.", Toast.LENGTH_SHORT).show();
        }
        //Establece la conexión con el socket bluetooth.
        try {
            btSocket.connect();
        }catch (IOException e){
            try {
              btSocket.close();
            }catch (IOException e2){

            }
        }
        MyConexionBT = new ConnectedThread(btSocket);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            //Cuando se sale de la aplicación esta parte permite que no se deje abierto el socket
            btSocket.close();
        }catch (IOException e){

        }
    }
    //Comprueba que el dispositivo Bluetooth
    //Está disponible y solicita que se active si está desactivado
    private void VerificarEstadoBT(){
        if (btAdapter == null){
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth.", Toast.LENGTH_LONG).show();
        }else {
            if (btAdapter.isEnabled()){

            }else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            }
        }
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
                Toast.makeText(getBaseContext(), "La Conexión Falló.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    // Metodo para ocultar la barra de inicio y hacer el programa full screm
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }



    /*public  void traerSolicitudes(){
        StringRequest request=new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int idClientes=jsonObject.getInt("idClientes");
                            String placa=jsonObject.getString("placa").toString();
                            String nombre=jsonObject.getString("nombre").toString();
                            String lugar=jsonObject.getString("lugar").toString();
                            String horaIngreso=jsonObject.getString("horaIngreso").toString();
                            //Toast.makeText(Inicio.this,"-> "+startDireccion,Toast.LENGTH_SHORT).show();
                            // Toast.makeText(Inicio.this,"-> "+jsonObject.getString("FinalDirection").toString(),Toast.LENGTH_SHORT).show();

                            //int idRequest,Double latitud,Double longitud,String nameUser,String referencia
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e("Error Volelly",error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(this).add(request);
    }*/

    // Metodo para crear un ventana nueva y asignar los datos del Cliente
    public void onCreateDialogCobrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle(R.string.tituloAletaIngresarVehiculo);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //BreakIterator dialog_signin;
        View dialogView= inflater.inflate(R.layout.dialog_signin,null);
        builder.setView(dialogView);
         EditText placaVehiculoIngresada = (EditText) dialogView.findViewById(R.id.placaIngresada);
         EditText nombreUsuarioET = (EditText) dialogView.findViewById(R.id.nombreUsuarioET);
         EditText lugarParqueaderoET = (EditText) dialogView.findViewById(R.id.lugarParqueadero);

        // >>>> here


        //builder.setView(inflater.inflate(R.layout.dialog_signin, null));


                // Add action buttons
                builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(MainActivity.this,"-> "+placaVehiculoIngresada.getText().toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this,">-> "+nombreUsuarioET.getText().toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this,">>-> "+lugarParqueaderoET.getText().toString(),Toast.LENGTH_SHORT).show();
                        String parqueaderoSeleccionado;
                        parqueaderoSeleccionado=lugarParqueaderoET.getText().toString();
                        placaIngresada=placaVehiculoIngresada.getText().toString();
                        nombreConductor=nombreUsuarioET.getText().toString();
                        lugarParqueadero=parqueaderoSeleccionado;

                        encender_Apagar_Parqueaderos(parqueaderoSeleccionado);
                        enviarDatosPakingActivos();

                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog= builder.create();
           dialog.show();

    }
    public void ventanaTotal_A_Pagar(String cantidad) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle(R.string.tituloAlertaTotalPagar);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //BreakIterator dialog_signin;
        View dialogView= inflater.inflate(R.layout.dialog_signincantidadcobrar,null);
        builder.setView(dialogView);
        TextView totalCobrar = (TextView) dialogView.findViewById(R.id.totalCobrar);
        totalCobrar.setText(cantidad);


        // >>>> here


        //builder.setView(inflater.inflate(R.layout.dialog_signin, null));


        // Add action buttons
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this,"-> "+totalCobrar.getText().toString(),Toast.LENGTH_SHORT).show();


            }

        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog= builder.create();
        dialog.show();
    }

    // Metodo para crear un ventana nueva y buscar una Placa unica
    public void buscarPlaca() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("BUSCAR PLACA");
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //BreakIterator dialog_signin;
        View dialogView= inflater.inflate(R.layout.dialog_signinconsultarplaca,null);
        builder.setView(dialogView);
        EditText placaBuscardatos = (EditText) dialogView.findViewById(R.id.placaBuscarDatos);


        // >>>> here


        //builder.setView(inflater.inflate(R.layout.dialog_signin, null));


        // Add action buttons
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

               // Toast.makeText(MainActivity.this,"-> "+placaVehiculoIngresada.getText().toString(),Toast.LENGTH_SHORT).show();

                placaIngresadaBusqueda=placaBuscardatos.getText().toString();
                enviarDatosConsultaPlaca();

            }

        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog= builder.create();
        dialog.show();

    }


    // Metodo para enviar los datos al servidor de Node.js y guardarlos en la BD
    public void enviarDatosPakingActivos(){
        //Toast.makeText(Actividades.this,"Heyy >>"+placa+" : "+name,Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this,"Heyy >>",Toast.LENGTH_SHORT).show();


        StringRequest postRequest = new StringRequest(Request.Method.POST, urlDriveraddActivo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ////////////
                ///////////  Respusta del servidor///////////
                ////////////
                Toast.makeText(MainActivity.this,"[]"+response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error Volelly",error.getMessage());
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("placa",placaIngresada);
                params.put("nombre",nombreConductor);
                params.put("lugar",lugarParqueadero);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(postRequest);

    }

    public void enviarDatosConsultaPlaca(){
        //Toast.makeText(Actividades.this,"Heyy >>"+placa+" : "+name,Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,"Heyy >>",Toast.LENGTH_SHORT).show();


        StringRequest postRequest = new StringRequest(Request.Method.POST, urlbuscarPlaca, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ////////////
                ///////////  Respusta del servidor///////////
                ////////////
                Toast.makeText(MainActivity.this,"[]"+response.toString(),Toast.LENGTH_SHORT).show();
                ventanaTotal_A_Pagar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error Volelly",error.getMessage());
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("placa",placaIngresadaBusqueda);
                params.put("valorCobrar",datosObtenidosValorpordia);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(postRequest);

    }

   public void encender_Apagar_Parqueaderos(String lugarParqueadero){
       MyConexionBT.write(lugarParqueadero);
   }
   public void cambiarColorBotonesParkeadero(Button boton,int color){
        boton.setBackgroundColor(ContextCompat.getColor(getBaseContext(),color));
   }

}