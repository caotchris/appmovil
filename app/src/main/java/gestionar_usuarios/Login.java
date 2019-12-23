package gestionar_usuarios;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import timber.log.Timber;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Intent intent;
    EditText usuario, contrasena;
    String usuario1, contrasena1;
    Agente_Transito agente;

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

    @SuppressLint("LogNotTimber")
    private void obtenerAgente() {
        dialog.show ();
        new Thread (() -> {
            JSONObject object = new JSONObject();
            try {
                object.put("cedula",usuario.getText ().toString ());
                object.put("clave",contrasena.getText ().toString ());
                JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST, Constantes.URL_GET_AGENTE, object, response -> {
                    Log.e ("LoginResponse", response.toString ());
                    try {
                        String status = response.getString ("status");
                        if (status.equals ("ok")) {
                            JSONObject obj = response.getJSONArray ("agente").getJSONObject (0);
                            Log.e ("Object", obj.toString ());
                            if (obj != null)
                                Constantes.agente = Utilidades.obtenerAgente (obj);
                            else {
                                runOnUiThread (() -> {
                                    dialog.dismiss ();
                                    Toast.makeText (this, "Agente no encontrado, intentelo de nuevo", Toast.LENGTH_SHORT).show ();
                                });
                            }
                            obtenerNumInfraccion (Constantes.agente.getCodigo_agente ());
                            agente = Constantes.agente;
                            ingreso ();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }
                }, error -> {
                    Log.e ("LoginError", error.toString ());
                    runOnUiThread (() -> {
                        dialog.dismiss ();
                        Toast.makeText (this, "Error de conexion", Toast.LENGTH_SHORT).show ();
                    });
                });
                request.add (objectRequest);
            } catch (JSONException e) {
                e.printStackTrace ();
            }
        }).start ();
    }

    private void obtenerNumInfraccion(int codigo) {
        JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST, Constantes.URL_GET_NUM_INFRA+codigo, null, response -> {
            Timber.tag ("NumInfResponse").e (response.toString ());
            try {
                String status = response.getString ("status");
                if (status.equals ("ok")) {
                    Constantes.agente = Utilidades.obtenerAgente (response.getJSONArray ("agente").getJSONObject (0));
                    agente = Constantes.agente;
                    ingreso ();
                }
            } catch (JSONException e) {
                e.printStackTrace ();
            }
        }, error -> {
            Timber.tag ("NumInfError").e (error.toString ());
            runOnUiThread (() -> {
                dialog.dismiss ();
                Toast.makeText (this, "Error de conexion", Toast.LENGTH_SHORT).show ();
            });
        });
        request.add (objectRequest);
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
        dialog.dismiss ();
        Toast.makeText (this, "Bienvenido "+agente.getNombre (), Toast.LENGTH_SHORT).show ();
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
                obtenerAgente ();
            }
        }

    }


}
