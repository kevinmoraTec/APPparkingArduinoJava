package com.example.proyectobluetooh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Actividades extends AppCompatActivity {
    private String[] placasVehiculos;

    ImageView parquaderoactivity, iconoInformacion;

    private RequestQueue requestQueue;
    String ip ="192.168.190.237";

    String address;
    private String urlDriveraddActivo="http://"+ip+":4000/clientes";
    //http://"+"ip+"+":4000/clientes";


    //String que se enviará a actividad principal, mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private Request<? extends Object> stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        parquaderoactivity = findViewById(R.id.parquaderoactivity);
        iconoInformacion = findViewById(R.id.iconoInformacion);





        Bundle extras = getIntent().getExtras();
         address = extras.getString(EXTRA_DEVICE_ADDRESS);




//        Toast.makeText(getBaseContext(), "Dirección" + address, Toast.LENGTH_SHORT).show();

        parquaderoactivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                   //onCreateDialogPlacas();

                    Intent intent = new Intent(Actividades.this, MainActivity.class);
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

                    startActivity(intent);

//                    Toast.makeText(getBaseContext(), "Abrio ACTIVITY" + address, Toast.LENGTH_SHORT).show();
                   //


            }
        });


    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

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







//    public void onCreateDialogPlacas() {
//        TimePickerDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.placas)
//                .setItems(R.array.arrayPlacas, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(Actividades.this,"Selecionaste :"+placasVehiculos[which],Toast.LENGTH_SHORT).show();
//
//
//
//                    }
//                });
//        Dialog dialog= builder.create();
//        dialog.show();
//    }

}