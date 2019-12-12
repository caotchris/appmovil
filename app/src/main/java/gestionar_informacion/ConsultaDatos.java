package gestionar_informacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ucot.R;
import com.github.clans.fab.FloatingActionButton;

public class ConsultaDatos extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton conductorcon, vehiculocon;
    Intent intent;
    private static final String PREF_NAME = "datos";
    String nombecompletocon;
    TextView nombrecom, cedulacon, placacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultadatos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarconsulta);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Textview
        nombrecom = (TextView) findViewById(R.id.nombrecompletocon);
        cedulacon = (TextView) findViewById(R.id.identificacioncon);
        placacon = (TextView) findViewById(R.id.placacon);
        //Botones flotantes
        conductorcon = (FloatingActionButton) findViewById(R.id.ConductorCon);
        vehiculocon = (FloatingActionButton) findViewById(R.id.VehiculoCon);
        conductorcon.setOnClickListener(ConsultaDatos.this);
        vehiculocon.setOnClickListener(ConsultaDatos.this);
    }

    //Metodos
    //Llamar clase condcutor
    public void MetodoConductorcon (){
        intent = new Intent(ConsultaDatos.this, Conductor.class);
        startActivity(intent);
    }

    //Llamar clase vehiculo
    public void MetodoVehiculocon (){
        intent = new Intent(ConsultaDatos.this, Vehiculo.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarestadocon();
    }

    //presentar estado activity
    public void mostrarestadocon (){
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        placacon.setText(prefe.getString("placa", ""));
        nombecompletocon = prefe.getString("nombre", "") + " " +prefe.getString("apellido", "");
        nombrecom.setText(nombecompletocon);
        cedulacon.setText(prefe.getString("identificacion", ""));
    }

    //controla boton atras
    @Override
    public void onBackPressed() {
        limpiar();
        super.onBackPressed();
    }

    //Limpiar estado
    public void limpiar (){
        getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().commit();

    }

    //Escucha botones flotantes
    @Override
    public void onClick(View view) {
        if (view == conductorcon){
            MetodoConductorcon();
        }
        if (view == vehiculocon){
            MetodoVehiculocon();
        }
    }

    //boton atras en actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
