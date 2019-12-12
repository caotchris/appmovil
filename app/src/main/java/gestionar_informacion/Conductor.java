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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ucot.R;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import Utilidades.Constantes;
import Utilidades.Utilidades;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import gestionar_sincronizacion.Obtener_Info;

public class Conductor extends AppCompatActivity implements View.OnClickListener {

    public static ImageButton emisionlicencia, caducidadlicencia;
    public static EditText licenciaemision, licenciacaducidad, licenciacategoria, nombres, apellidos, identificacion;
    private static int ano, mes, dia;
    private static Spinner spinnerc;
    private String opcionesTipo [] =new String[0];
    String selectedString;
    private static final String PREF_NAME = "datos";
    private FloatingActionButton conductor;

    private RequestQueue request;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarconductor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Botones para fecha
        emisionlicencia = (ImageButton) findViewById(R.id.emisioni);
        caducidadlicencia = (ImageButton) findViewById(R.id.caducidadi);
        //Boton de consulta
        conductor = (FloatingActionButton) findViewById(R.id.Conductor);
        //Edittext
        nombres = (EditText) findViewById(R.id.Nombre);
        apellidos = (EditText) findViewById(R.id.Apellido);
        identificacion = (EditText) findViewById(R.id.Identificacion);
        licenciaemision = (EditText) findViewById(R.id.FechaEmisionLicencia);
        licenciacaducidad = (EditText) findViewById(R.id.FechaCaducidadLicencia);
        licenciacategoria = (EditText) findViewById(R.id.CategoriaLicencia);
        licenciacategoria.setEnabled(false);
        licenciaemision.setEnabled(false);
        licenciacaducidad.setEnabled(false);
        emisionlicencia.setOnClickListener(this);
        caducidadlicencia.setOnClickListener(this);
        conductor.setOnClickListener(this);
        //Spinner
        spinnerc = (Spinner) findViewById(R.id.TipoLicencia);
        spiner();
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
        nombres.setText(prefe.getString("nombre", ""));
        apellidos.setText(prefe.getString("apellido", ""));
        identificacion.setText(prefe.getString("identificacion", ""));
        licenciacategoria.setText(prefe.getString("lcategoria", ""));
        spinnerc.setSelection(prefe.getInt("spinner", spinnerc.getSelectedItemPosition()));
        licenciaemision.setText(prefe.getString("lemision", ""));
        licenciacaducidad.setText(prefe.getString("lcaducidad", ""));
    }

    private boolean  validar() {
        boolean band = true;
        if (identificacion.getText ().toString ().isEmpty ()) {
            identificacion.setError ("Campo requerido");
            band = false;
        }
        if (nombres.getText ().toString ().isEmpty ()) {
            nombres.setError ("Campo requerido");
            band = false;
        }
        if (apellidos.getText ().toString ().isEmpty ()) {
            apellidos.setError ("Campo requerido");
            band = false;
        }
        if (licenciaemision.getText ().toString ().isEmpty ()) {
            licenciaemision.setError ("Campo requerido");
            band = false;
        }

        if (licenciacaducidad.getText ().toString ().isEmpty ()) {
            licenciacaducidad.setError ("Campo requerido");
            band = false;
        }

        return band;
    }

    //guarda estado activity
    public void guardaestado() {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("nombre", nombres.getText().toString());
        editor.putString("apellido", apellidos.getText().toString());
        editor.putString("identificacion", identificacion.getText().toString());
        editor.putString("lcategoria", licenciacategoria.getText().toString());
        editor.putInt("spinner", spinnerc.getSelectedItemPosition());
        //spinner texto
        selectedString = opcionesTipo[spinnerc.getSelectedItemPosition()];  //Guarda texto del spinner
        editor.putString("textospinner", selectedString);                  //Guarda texto del spinner
        editor.putString("lemision", licenciaemision.getText().toString());
        editor.putString("lcaducidad", licenciacaducidad.getText().toString());
        editor.apply();
        guardaDB ();
        finish();
    }

    public void guardaDB() {
        String nombre = nombres.getText().toString();
        String apellido = apellidos.getText().toString();
        String id = identificacion.getText().toString();
        String lcategoria = licenciacategoria.getText().toString();
        String tipol  = opcionesTipo[spinnerc.getSelectedItemPosition()];  //Guarda texto del spinner
        String lemision = licenciaemision.getText().toString();
        String lcaducidad = licenciacaducidad.getText().toString();

        Modelos.Conductor conductor = new Modelos.Conductor (id, nombre, apellido, tipol, lcategoria, lemision, lcaducidad);
        Constantes.conductor = conductor;
        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper (this, Constantes.DB, null, 1);
        helper.crearConductor (conductor);
        finish();
    }

    //controla boton atras
    @Override
    public void onBackPressed()
    {
        guardaestado();
    }


    //Spinner
    public void spiner()
    {
        opcionesTipo = new String[]{
                "A",
                "B",
                "F",
                "A1",
                "C",
                "C1",
                "D",
                "D1",
                "E",
                "E1",
                "G",
        };

        ArrayAdapter<String> arrayAdapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesTipo);
        spinnerc.setAdapter(arrayAdapterTipo);

        //Listener a spinner
        spinnerc.getSelectedItemPosition();
        spinnerc.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                        String seleccion = spn.getItemAtPosition(posicion).toString();
                        if(seleccion.equals("A") || seleccion.equals("B") || seleccion.equals("F")){
                            String resultado = "No profesional";
                            licenciacategoria.setText(resultado);
                        } else if (seleccion.equals("A1") || seleccion.equals("C") || seleccion.equals("C1") || seleccion.equals("D") || seleccion.equals("D1") || seleccion.equals("E") || seleccion.equals("E1") || seleccion.equals("G")){
                            String resultado1 = "Profesional";
                            licenciacategoria.setText(resultado1);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
    }

    //Mostrar menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainc, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Calendario
    @Override
    public void onClick(View view) {
        if (view == emisionlicencia){
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    licenciaemision.setText(day+"/"+(month+1)+"/"+year);
                }
            }, dia, mes, ano);
            datePickerDialog.show();
        }
        if (view == caducidadlicencia) {
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    licenciacaducidad.setText(day+"/"+(month+1)+"/"+year);
                }
            }, dia, mes, ano);
            datePickerDialog.show();
        }
        if (view == conductor){
            if (!identificacion.getText ().toString ().isEmpty ())
                obtenerConductor ();
            else
                identificacion.setError ("Campo requerido");
        }
    }

    private void obtenerConductor() {
        dialog.show ();
        final String[] msg = new String[1];
        new Thread (() -> {
            @SuppressLint("LogNotTimber") JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.GET,
                    Constantes.URL_GET_CONDUCTOR+identificacion.getText ().toString (), null, response -> {
                try {
                    Log.e ("ConductorResponse", response.toString ());
                    String status = response.getString ("status");
                    if (status.equals ("ok")) {
                        Constantes.conductor = Utilidades.obtenerConductor(response.getJSONObject ("conductor"));
                        Modelos.Conductor conductor = Constantes.conductor;
                        if (conductor != null) {
                            nombres.setText (conductor.getNombres());
                            apellidos.setText (conductor.getApellidos());
                            identificacion.setText (conductor.getCedula ());
                            licenciaemision.setText (conductor.getFecha_emision_licencia ());
                            licenciacaducidad.setText (conductor.getFecha_caducidad_licencia ());
                            licenciacategoria.setText (conductor.getCategoria_licencia ());
                            guardaDB ();
                            guardaestado ();
                            msg[0] = "Conductor encontrado";
                        } else {
                            msg[0] = "Conductor no encontrado, vuelva a intentarlo";
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }, error -> {
                Log.e ("CondutorError", error.toString ());
            });
            request.add (objectRequest);
            runOnUiThread (() -> {
                dialog.dismiss ();
            });
        }).start ();
    }

}
