package gestionar_usuarios;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ucot.R;

import Modelos.Agente_Transito;
import Utilidades.Constantes;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;

public class AgenteTransito extends AppCompatActivity {

    TextView nombre, apellido, identificacion, codigo;
    Agente_Transito agente = Constantes.agente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentetransito);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarperfil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Textview
        nombre = (TextView) findViewById(R.id.NombresP);
        apellido = (TextView) findViewById(R.id.ApellidosP);
        identificacion = (TextView) findViewById(R.id.IdentificacionP);
        codigo = (TextView) findViewById(R.id.CodigoP);
        nombre.setText(agente.getNombre());
        apellido.setText(agente.getApellidos());
        identificacion.setText(agente.getCedula());
        codigo.setText(String.valueOf(agente.getCodigo_agente()));
    }

    //boton atras en actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
