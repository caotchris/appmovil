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

import Utilidades.Constantes;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;

public class AgenteTransito extends AppCompatActivity {

    TextView nombre, apellido, identificacion, codigo;

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
//        Consultar();
    }

    //boton atras en actionbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    //Metodo consultas
//    public void Consultar(){
//
//        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, Constantes.DB, null, 1);
//        SQLiteDatabase BaseDeDataBase = admin.getWritableDatabase();
//        Cursor fila = BaseDeDataBase.rawQuery
//                ("select Codigo, Cedula, Nombres, Apellidos from Agente_Transito where Codigo="+ 001, null);
//        if (fila.moveToFirst()){
//
//            codigo.setText(fila.getString(0));
//            identificacion.setText(fila.getString(1));
//            nombre.setText(fila.getString(2));
//            apellido.setText(fila.getString(3));
//
//            BaseDeDataBase.close();
//        }else{
//            Toast.makeText(this, "No hay información", Toast.LENGTH_SHORT).show();
//            BaseDeDataBase.close();
//        }
//    }

}
