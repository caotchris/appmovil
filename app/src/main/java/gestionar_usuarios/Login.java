package gestionar_usuarios;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ucot.MainActivity;
import com.example.ucot.R;

import org.json.JSONException;
import org.json.JSONObject;

import Modelos.Agente_Transito;
import Utilidades.Constantes;
import Utilidades.Utilidades;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import timber.log.Timber;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Intent intent;
    EditText usuario, contrasena;
    String usuario1, contrasena1;
    int scodigo;
    String scedula;
    String snombres;
    String sapellidos;
    String sclave;
    Agente_Transito agente;
    // Constantes cuando el usuario se encuentra registrado
    public static int codigost, cedulast;

    private RequestQueue request;
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Edittext
        usuario = findViewById(R.id.Usuario);
        contrasena = findViewById(R.id.Contrasena);

        //PETICION DE PERMISOS
        requestPermissions ();

        //Boton entrar
        login = findViewById(R.id.Login);
        login.setOnClickListener(this);

        request = Volley.newRequestQueue (this);
        dialog = new ProgressDialog (this);
        dialog.setTitle ("validando datos");
        dialog.setMessage ("Espere por favor...");
        dialog.setCancelable (false);
    }

    //Cuando informacion del agente se encuentra localmente
    public static Agente_Transito obtenerAgt(int cod, String ced, String clav, String nombr, String apellid){
        int codigoe = cod;
        String cedulae = ced;
        String clavee = clav;
        String nombree = nombr;
        String apellidose = apellid;

        return new Agente_Transito (codigoe, cedulae, clavee, nombree, apellidose);
    }

    //COnsulta BD en tabla de agente de transito
    public void consultaDb(){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        if(!usuario1.isEmpty() && !contrasena1.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select codigo, cedula, nombres, apellidos, clave from agenteTransito where cedula =" + Integer.parseInt(usuario1), null);
            if(fila.moveToFirst()){

                if (Integer.parseInt(usuario1) == fila.getInt(1) && contrasena1.equals(fila.getString(4))){
                    Constantes.agente = obtenerAgt(fila.getInt(0), String.valueOf(fila.getInt(1)), fila.getString(4), fila.getString(2), fila.getString(3));
                    ingreso();
                    BaseDeDatabase.close();
                }
                else {
                    Toast.makeText(this,"Verifique sus credenciales", Toast.LENGTH_SHORT).show();
                    BaseDeDatabase.close();
                }

            } else {
                Toast.makeText(this,"Sincronizando usuario", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
               obtenerAgente(); //Sincroiza al agente de tránsito
            }

        } else {
            Toast.makeText(this, "LLene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarDb (){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if(scodigo>0 && !scedula.isEmpty() && !snombres.isEmpty() && !snombres.isEmpty() && !sapellidos.isEmpty() && !sclave.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", scodigo);
            registro.put("cedula", Integer.parseInt(scedula));
            registro.put("nombres",snombres);
            registro.put("apellidos", sapellidos);
            registro.put("clave", sclave);
            BaseDeDatos.insert("agenteTransito", null, registro);
            BaseDeDatos.close();
            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
            ingreso();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


//Consulta en la BD servidor
    private void obtenerAgente() {
        dialog.show ();
        final String[] msg = new String[1];
        new Thread (() -> {
            @SuppressLint("LogNotTimber") JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.GET,
                    Constantes.URL_GET_AGENTE+usuario1, null, response -> {

                try {
                    Log.e ("AgenteResponse", response.toString ());
                    String status = response.getString ("status");
                    if (status.equals ("ok")) {
                        Constantes.agente = Utilidades.obtenerAgente(response.getJSONObject ("agente"));
                        agente = Constantes.agente;
                        if (agente != null) {
                            scodigo = agente.getCodigo_agente();
                            scedula = agente.getCedula();
                            snombres = agente.getNombre();
                            sapellidos = agente.getApellidos();
                            sclave = agente.getClave();
                            guardarDb();
                            msg[0] = "Conductor encontrado";
                        } else {
                            msg[0] = "Conductor no encontrado, vuelva a intentarlo";
                            Toast.makeText (this, "Conductor no encontrado, vuelva a intentarlo", Toast.LENGTH_SHORT).show ();
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


    private boolean validar() {
        boolean band = true;
        if (usuario.getText ().toString ().isEmpty ()) {
            usuario.setError ("Campo requerido");
            band = false;
        }
        if (contrasena.getText ().toString ().isEmpty ()) {
            contrasena.setError ("Campo requerido");
            band = true;
        }
        return band;
    }

    //Metodo para acceder al mainprincipal
    public void ingreso(){
        usuario.setText("");
        contrasena.setText("");
        dialog.dismiss ();
        Toast.makeText (this, "Bienvenido ", Toast.LENGTH_SHORT).show ();
        intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void requestPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Explicamos porque necesitamos el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Aca continuamos el procesos deseado a hacer

            } else {
                ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CALENDAR}, 1);
            }
        }
    }

    //Escucha el boton
    @Override
    public void onClick(View view) {
        if(view == login){
            if (validar ()) {
                usuario1 = usuario.getText ().toString ();
                contrasena1 = contrasena.getText ().toString ();
                consultaDb();
            }
        }

    }


}
