package gestionar_accidentes;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ucot.R;

import java.util.Calendar;
import java.util.Formatter;

import Modelos.Accidente_Transito;
import Modelos.Agente_Transito;
import Modelos.Articulos_Coip;
import Modelos.Conductor;
import Modelos.Vehiculo;
import Utilidades.Constantes;
import Utilidades.Utilidades;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import gestionar_evidencias.Audio;
import gestionar_evidencias.Foto;
import gestionar_evidencias.Video;

public class Accidente extends AppCompatActivity implements View.OnClickListener{
    com.google.android.material.floatingactionbutton.FloatingActionButton guardar;

    private final String PREF_NAME = "datos";
    EditText descripcion, horaacc, horaat;
    ImageButton audio, video, foto;
    Spinner tipoaccidente;
    String opcionesTipoAccidente [] =new String[0];
    Intent intent;
    ImageView horaacidente, horaatencion;
    int horas, minutos, infraccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accidente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarinfraccion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Edittext
        descripcion = (EditText) findViewById(R.id.DescripcionAccidente);
        horaacc = (EditText) findViewById(R.id.HoraAccidente);
        horaat = (EditText) findViewById(R.id.HoraAtencion);
        horaacc.setEnabled(false);
        // Spinner
        tipoaccidente = (Spinner) findViewById(R.id.TipoAccidente);
        //Boton Evidencias
        audio = findViewById(R.id.AudioA);
        foto = findViewById(R.id.FotoA);
        video = findViewById(R.id.VideoA);
        horaacidente = (ImageView) findViewById(R.id.EstablecerHoraAccidente);
        horaatencion = (ImageView) findViewById(R.id.EstablecerHoraAccidenteAtencion);
        audio.setOnClickListener(this);
        foto.setOnClickListener(this);
        video.setOnClickListener(this);
        horaacidente.setOnClickListener(this);
        horaatencion.setOnClickListener(this); //Hora atencion es igual a hora de registro
        //Boton para guardar
        guardar = findViewById(R.id.GuardarAccidente);
        guardar.setOnClickListener(this);
        spiner();
        horaactual1();
    }

    //Spinner
    public void spiner() {
        opcionesTipoAccidente = new String[]{
                "Choque",
                "Atropellamiento",
                "Volcamiento",
                "Caida del ocupante",
                "Incendio",
                "Otros",
        };

        ArrayAdapter<String> arrayAdapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesTipoAccidente);
        tipoaccidente.setAdapter(arrayAdapterTipo);
    }

    //boton atras en actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //llamar a video
    public void MetodoGrabarVideo(){
        intent = new Intent(Accidente.this, Video.class);
        startActivity(intent);
    }

    //llamar a foto
    public void MetodoGrabarFoto(){
        intent = new Intent(Accidente.this, Foto.class);
        startActivity(intent);
    }

    //llamar a audio
    public void MetodoGrabarAudio(){
        intent = new Intent(Accidente.this, Audio.class);
        startActivity(intent);
    }

    //Establce Hora
    public void MetodoHoraAc(){
        final Calendar c = Calendar.getInstance();
        horas = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                // i = hora, i1 = minutos
                Formatter obj = new Formatter();
                String ceros = String.valueOf(obj.format("%02d", i1));
                horaacc.setText(i+":"+ceros);
            }
        },horas, minutos, false);
        timePickerDialog.show();
    }

    //Establce Hora
    public void MetodoHoraAt(){
        final Calendar c = Calendar.getInstance();
        horas = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                // i = hora, i1 = minutos
                Formatter obj = new Formatter();
                String ceros = String.valueOf(obj.format("%02d", i1));
                horaacc.setText(i+":"+ceros);
            }
        },horas, minutos, false);
        timePickerDialog.show();
    }

    public void horaactual1(){
        String mydate = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
        horaacc.setText(mydate);
        horaat.setText(mydate);
    }

    private void guardarNumAccidente() {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt ("num", infraccion+1);
    }

    private void guardarDetencion(int num) {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt ("infraccion", num);
        editor.putString ("hora", horaacc.getText ().toString ());
        editor.apply();
    }

    private int obtenerNumAccidente() {
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefe.getInt ("num", 0);
    }

    @Override
    public void onClick(View view) {
        if (view == guardar){
            validar();
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
        if (view == horaacidente){
            MetodoHoraAc();
        }
        if (view == horaatencion){
            MetodoHoraAt();
        }
    }

    //Validar los campos
    private boolean validar() {
        boolean band = true;
        if (descripcion.getText ().toString ().isEmpty ()){
            descripcion.setError ("Campo requerido");
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_LONG).show();
            band = false;
        }
        else{
            guardarAccidenteDB();
            guardarNumAccidente ();
        }
        return band;
    }

    private void guardarAccidenteDB() {
        int numero = Constantes.numaccidente;
        String tipo = tipoaccidente.getSelectedItem ().toString ();
        String descripcion = this.descripcion.getText ().toString ();
        String ubicacion = Constantes.ubicacion;
        int intento = 55;
        double latitud = Constantes.lat;
        double longitud = Constantes.lng;
        String estado = "Reportado";
        String fecha_accidente = Utilidades.obtenerFechaActual ();
        String fecha_registro = Utilidades.obtenerFechaActual ();   //Fecha de atencion
        String hora_accident = this.horaacc.getText ().toString ();
        //guardarDetencion (numero);
        String hora_registro = Utilidades.obtenerHoraActual ();   // Hora de atencion
        Agente_Transito agente_transito = Constantes.agente;
//        Conductor conductor = Constantes.conductor;
//        Vehiculo vehiculo = Constantes.vehiculo;
//        Articulos_Coip articulos = Constantes.articulo;
        Accidente_Transito accidente = new Accidente_Transito (numero, tipo, descripcion, ubicacion,
                intento, latitud, longitud, estado, fecha_accidente, fecha_registro, hora_accident, hora_registro);
        accidente.setAgente_transito (agente_transito.getCodigo_agente ());
//        accidente.setArticulos (articulos);
//        accidente.setConductor (conductor);
//        accidente.setVehiculo (vehiculo);
//        Constantes.accidente = accidente;
        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper (this, Constantes.DB, null, 1);
        helper.crearAccidenteTransito(accidente);
        Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_LONG).show();
        Constantes.numaccidente= numero+1;
        onBackPressed();
    }
}