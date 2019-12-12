package gestionar_infracciones;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ucot.R;
import com.github.clans.fab.FloatingActionButton;
import com.sunmi.printerhelper.activity.FunctionActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import Modelos.Agente_Transito;
import Modelos.Articulos_Coip;
import Modelos.Infraccion_Transito;
import Utilidades.Constantes;
import Utilidades.Utilidades;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import gestionar_evidencias.Audio;
import gestionar_evidencias.Foto;
import gestionar_evidencias.Video;
import gestionar_informacion.Conductor;
import gestionar_informacion.Vehiculo;

public class Infraccion extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton conductores, vehiculos, articulos;
    com.google.android.material.floatingactionbutton.FloatingActionButton guardar;
    Intent intent;
    TextView nombre, cedula, placa, articulo, inciso, numeral;

    private static final String PREF_NAME = "datos";
    String nombecompleto;
    ImageView audio, foto, video, horainf;
    EditText hora;
    int horas, minutos,infraccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infraccion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarinfraccion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Textview
        nombre = (TextView) findViewById(R.id.nombreinf);
        cedula = (TextView) findViewById(R.id.cedulainf);
        placa = (TextView) findViewById(R.id.placainf);
        articulo = (TextView) findViewById(R.id.articuloinf);
        inciso = (TextView) findViewById(R.id.incisoinf);
        numeral = (TextView) findViewById(R.id.numeralinf);
        hora = (EditText) findViewById(R.id.HoraInfraccion);
        hora.setEnabled(false);
        //Botones flotantes
        guardar = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.GuardarInfraccion);
        conductores = (FloatingActionButton) findViewById(R.id.Conductor);
        vehiculos = (FloatingActionButton) findViewById(R.id.Vehiculo);
        articulos = (FloatingActionButton) findViewById(R.id.Articuloss);
        audio = (ImageView) findViewById(R.id.AudioI);
        foto = (ImageView) findViewById(R.id.FotoI);
        video = (ImageView) findViewById(R.id.VideoI);
        horainf = (ImageView) findViewById(R.id.EstablecerHoraInfraccion);
        conductores.setOnClickListener(this);
        vehiculos.setOnClickListener(this);
        articulos.setOnClickListener(this);
        guardar.setOnClickListener(this);
        audio.setOnClickListener(this);
        foto.setOnClickListener(this);
        video.setOnClickListener(this);
        horainf.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarestado();
    }

    //boton atras en actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Llamar clase condcutor
    public void MetodoConductor (){
        intent = new Intent(Infraccion.this, Conductor.class);
        startActivity(intent);
    }

    //Llamar clase vehiculo
    public void MetodoVehiculo (){
        intent = new Intent(Infraccion.this, Vehiculo.class);
        startActivity(intent);
    }

    //Llamar clase articulo
    public void MetodoArticulo (){
        intent = new Intent(Infraccion.this, Articulos.class);
        startActivity(intent);
    }

    //llamar a video
    public void MetodoGrabarVideo(){
        intent = new Intent(Infraccion.this, Video.class);
        startActivity(intent);
    }

    //llamar a foto
    public void MetodoGrabarFoto(){
        intent = new Intent(Infraccion.this, Foto.class);
        startActivity(intent);
    }

    //llamar a audio
    public void MetodoGrabarAudio(){
        intent = new Intent(Infraccion.this, Audio.class);
        startActivity(intent);
    }

    //guardar
    public void GuardarInf(){
        intent = new Intent(Infraccion.this, FunctionActivity.class);
        startActivity(intent);
    }

    //llamar a audio
    public void MetodoHora(){
        final Calendar c = Calendar.getInstance();
        horas = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                // i = hora, i1 = minutos
                Formatter obj = new Formatter();
                String ceros = String.valueOf(obj.format("%02d", i1));
                hora.setText(i+":"+ceros);
            }
        },horas, minutos, false);
        timePickerDialog.show();
    }

    //Escucha botones
    @Override
    public void onClick(View view) {
        if (view == conductores){
            MetodoConductor();
        }
        if (view == vehiculos){
            MetodoVehiculo();
        }
        if (view == articulos){
            MetodoArticulo();
        }
        if (view == guardar){
            GuardarInf();
            guardarNumInfraccion ();
            guardarDB ();
        }
        if (view == audio){
            MetodoGrabarAudio();
        }
        if (view == foto){
            MetodoGrabarFoto();
        }
        if (view == video){
            MetodoGrabarVideo();
        }
        if (view == horainf){
            MetodoHora();
        }
    }

    //Limpiar estado
    public void limpiar (){
        getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }


    private void guardarNumInfraccion() {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt ("numInf", infraccion+1);
    }

    private void guardarDetencion(int num) {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString ("infraccion", String.valueOf (num));
        editor.putString ("hora", hora.getText ().toString ());
        editor.apply();
    }

    private int obtenerNumInfraccion() {
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefe.getInt ("numInf", 0);
    }

    //presentar estado activity
    public void mostrarestado (){
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        placa.setText(prefe.getString("placa", ""));
        nombecompleto = prefe.getString("nombre", "") + " " +prefe.getString("apellido", "");
        nombre.setText(nombecompleto);
        cedula.setText(prefe.getString("identificacion", ""));
        articulo.setText(prefe.getString("articulo", ""));
        inciso.setText(prefe.getString("inciso", ""));
        numeral.setText(prefe.getString("numeral", ""));
    }

    private void guardarDB() {
        int numrero_infraccion = Integer.parseInt (String.valueOf (Constantes.agente.getCodigo_agente ()).concat (String.valueOf (obtenerNumInfraccion ())));
        String descripcion = " ";
        String ubicacion = Constantes.ubicacion;
        int intento = 1;
        double latitud = Constantes.lat;
        double longitud = Constantes.lng;
        String estado = "En resolucion";
        String fecha_infraccion = Utilidades.obtenerFechaActual();
        String hora_infraccion = hora.getText ().toString ();
        guardarDetencion (numrero_infraccion);
        String hora_registro = Utilidades.obtenerHoraActual ();
        Agente_Transito agente_transito = Constantes.agente;
        Modelos.Conductor conductor = Constantes.conductor;
        Modelos.Vehiculo vehiculo = Constantes.vehiculo;
        Articulos_Coip articulos = Constantes.articulo;
        Infraccion_Transito infraccion = new Infraccion_Transito (numrero_infraccion, descripcion,
                ubicacion, intento, latitud, longitud, estado, fecha_infraccion, hora_infraccion, hora_registro);
        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper (this, Constantes.DB, null, 1);
        infraccion.setAgente_transito (agente_transito.getCodigo_agente ());
        infraccion.setConductor (conductor.getCedula ());
        infraccion.setVehiculo (vehiculo.getPlaca ());
        infraccion.setArticulos (articulos.getArticulo ());
        helper.crearInfraccionTransito (infraccion);
    }

    //controla boton atras
    @Override
    public void onBackPressed() {
        limpiar();
        super.onBackPressed();
    }
}
