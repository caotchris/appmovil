package gestionar_informacion;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ucot.R;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONException;

import java.util.Calendar;

import Utilidades.Constantes;
import Utilidades.Utilidades;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import gestionar_sincronizacion.Obtener_Info;

public class Vehiculo extends AppCompatActivity implements View.OnClickListener {

    ImageButton emisionmatricula, caducidadmatricula;
    EditText matriculaemision, matriculacaducidad, placa, marca, tipo, color;
    private int ano, mes, dia;
    private static final String PREF_NAME = "datos";
    private FloatingActionButton vehiculo;

    private RequestQueue request;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarvehiculo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Botones calendario
        emisionmatricula = (ImageButton) findViewById(R.id.emisionv);
        caducidadmatricula = (ImageButton) findViewById(R.id.caducidadv);
        //Boton consulta
        vehiculo = (FloatingActionButton) findViewById(R.id.Vehiculo);
        //Editext
        placa = (EditText) findViewById(R.id.Placa);
        marca = (EditText) findViewById(R.id.Marca);
        tipo = (EditText) findViewById(R.id.Tipo);
        color = (EditText) findViewById(R.id.Color);
        matriculaemision = (EditText) findViewById(R.id.FechaEmisionMatricula);
        matriculacaducidad = (EditText) findViewById(R.id.FechaCaducidadMatricula);
        matriculaemision.setEnabled(false);
        matriculacaducidad.setEnabled(false);
        emisionmatricula.setOnClickListener(this);
        caducidadmatricula.setOnClickListener(this);
        vehiculo.setOnClickListener(this);
        mostrarestado();

        request = Volley.newRequestQueue (this);
        dialog = new ProgressDialog (this);
        dialog.setCancelable (false);
        dialog.setTitle (R.string.titulo_dialogo2);
        dialog.setMessage ("Espere por favor...");
    }

    //presentar estado activity
    public void mostrarestado (){
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        placa.setText(prefe.getString("placa", ""));
        marca.setText(prefe.getString("marca", ""));
        tipo.setText(prefe.getString("tipo", ""));
        color.setText(prefe.getString("color", ""));
        matriculaemision.setText(prefe.getString("memision", ""));
        matriculacaducidad.setText(prefe.getString("mcaducidad", ""));
    }

    //guarda estado activity
    public void guardaestado() {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("placa", placa.getText().toString());
        editor.putString("marca", marca.getText().toString());
        editor.putString("tipo", tipo.getText().toString());
        editor.putString("color", color.getText().toString());
        editor.putString("memision", matriculaemision.getText().toString());
        editor.putString("mcaducidad", matriculacaducidad.getText().toString());
        editor.apply();
//        guardaDB ();
        finish();
    }

//    public void guardaDB() {
//        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper (this, Constantes.DB, null, 1);
//        String placaV = placa.getText().toString();
//        String marcaV = marca.getText().toString();
//        String tipoV = tipo.getText().toString();
//        String colorV = color.getText().toString();
//        String emision = matriculaemision.getText().toString();
//        String caducidad = matriculacaducidad.getText().toString();
//
//        Modelos.Vehiculo  vehiculo = new Modelos.Vehiculo (placaV, tipoV, marcaV, colorV, emision, caducidad);
//        Constantes.vehiculo = vehiculo;
//        helper.crearVehiculo (vehiculo);
//    }

    //Mostrar menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainc, menu);
        return true;
    }

    //controla boton atras
    @Override
    public void onBackPressed()
    {
        guardaestado();
    }

    //Boton atras actionbar
    @Override
    public boolean onSupportNavigateUp() {
        if (validar ())
            onBackPressed();
        return true;
    }

    //Escucha botones flotantes
    @Override
    public void onClick(View view) {
        if (view == emisionmatricula){
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    matriculaemision.setText(day+"/"+(month+1)+"/"+year);
                }
            }, dia, mes, ano);
            datePickerDialog.show();
        }
        if (view == caducidadmatricula){
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    matriculacaducidad.setText(day+"/"+(month+1)+"/"+year);
                }
            }, dia, mes, ano);
            datePickerDialog.show();
        }
        if (view == vehiculo){
            if (!placa.getText ().toString ().isEmpty ())
                obtenerVehiculo();
            else
                placa.setError ("Campo requerido");
        }
    }

    private boolean validar() {
        boolean band = true;
        if (placa.getText ().toString ().isEmpty ()) {
            placa.setError ("Campo requerido");
            band = false;
        }
        if (marca.getText ().toString ().isEmpty ()) {
            marca.setError ("Campo requerido");
            band = false;
        }
        if (tipo.getText ().toString ().isEmpty ()) {
            tipo.setError ("Campo requerido");
            band = false;
        }
        if (color.getText ().toString ().isEmpty ()) {
            color.setError ("Campo requerido");
            band = false;
        }
        if (matriculaemision.getText ().toString ().isEmpty ()) {
            matriculaemision.setError ("Campo requerido");
            band = false;
        }
        if (matriculacaducidad.getText ().toString ().isEmpty ()) {
            matriculacaducidad.setError ("Campo requerido");
            band = false;
        }
        return band;
    }

    private void obtenerVehiculo() {
        dialog.show();
        final String[] msg = new String[1];
        new Thread (() -> {
            @SuppressLint("LogNotTimber") JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.GET,
                    Constantes.URL_GET_VEHICULO + placa.getText ().toString (), null, response -> {
                Log.e ("VehiculoResponse", response.toString ());
                Constantes.vehiculo = Utilidades.obtenerVehiculo (response);
                Modelos.Vehiculo vehiculo = Constantes.vehiculo;
                if (vehiculo != null){
                    placa.setText(Constantes.vehiculo.getPlaca ());
                    marca.setText(Constantes.vehiculo.getMarca ());
                    tipo.setText(Constantes.vehiculo.getTipo ());
                    color.setText(Constantes.vehiculo.getColor ());
                    matriculaemision.setText(Constantes.vehiculo.getFecha_matricula ());
                    matriculacaducidad.setText(Constantes.vehiculo.getFecha_caducidad_matricula ());
//                    guardaDB ();
                    guardaestado ();
                    msg[0] = "Vehiculo encontrado";
                } else {
                    msg[0] = "Vehiculo no encontrado, vuelva a intentarlo";
                }
            }, error -> Log.e ("VehiculoError", error.toString ()));
            request.add (objectRequest);
            runOnUiThread (() -> dialog.dismiss ());
        }).start ();
    }
}