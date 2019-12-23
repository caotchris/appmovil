package com.sunmi.printerhelper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.ucot.MainActivity;
import com.example.ucot.R;
import com.sunmi.printerhelper.utils.AidlUtil;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.ESCUtil;

import java.io.IOException;

import Modelos.Agente_Transito;
import Utilidades.Constantes;
import Utilidades.Utilidades;

public class TextActivity extends BaseActivity {
    private EditText mEditText;
    private LinearLayout mLayout, mLinearLayout;
    private Button salir;
    private int record;
    private boolean isBold, isUnderLine;
    private static final String PREF_NAME = "datos";


    private String[] mStrings = new String[]{"CP437", "CP850", "CP860", "CP863", "CP865", "CP857",
            "CP737", "CP928", "Windows-1252", "CP866", "CP852", "CP858", "CP874", "Windows-775",
            "CP855", "CP862", "CP864", "GB18030", "BIG5", "KSC5601", "utf-8"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        record = 17;
        isBold = false;
        isUnderLine = false;
        salir = (Button) findViewById(R.id.Salir);
        mEditText = (EditText) findViewById(R.id.text_text);
        mEditText.setEnabled(false);
        mLinearLayout = (LinearLayout) findViewById(R.id.text_all);
        mLayout = (LinearLayout) findViewById(R.id.text_set);
        //Da contenido a la impresion
        mostrarestado();
        //Boton salir
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });

        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mLinearLayout.getWindowVisibleDisplayFrame(r);
                if(r.bottom < 800){
                    mLayout.setVisibility(View.GONE);
                }else{
                    mLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        AidlUtil.getInstance().initPrinter();
    }

    public void onClick(View view) {
        String content = mEditText.getText().toString();

        float size = 24;
        if (baseApp.isAidl()) {
            AidlUtil.getInstance().printText(content, size, isBold, isUnderLine);
        } else {
            printByBluTooth(content);
        }
    }

    private void printByBluTooth(String content) {
        try {
            if (isBold) {
                BluetoothUtil.sendData(ESCUtil.boldOn());
            } else {
                BluetoothUtil.sendData(ESCUtil.boldOff());
            }

            if (isUnderLine) {
                BluetoothUtil.sendData(ESCUtil.underlineWithOneDotWidthOn());
            } else {
                BluetoothUtil.sendData(ESCUtil.underlineOff());
            }

            if (record < 17) {
                BluetoothUtil.sendData(ESCUtil.singleByte());
                BluetoothUtil.sendData(ESCUtil.setCodeSystemSingle(codeParse(record)));
            } else {
                BluetoothUtil.sendData(ESCUtil.singleByteOff());
                BluetoothUtil.sendData(ESCUtil.setCodeSystem(codeParse(record)));
            }

            BluetoothUtil.sendData(content.getBytes(mStrings[record]));
            BluetoothUtil.sendData(ESCUtil.nextLine(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte codeParse(int value) {
        byte res = 0x00;
        switch (value) {
            case 0:
                res = 0x00;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                res = (byte) (value + 1);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                res = (byte) (value + 8);
                break;
            case 12:
                res = 21;
                break;
            case 13:
                res = 33;
                break;
            case 14:
                res = 34;
                break;
            case 15:
                res = 36;
                break;
            case 16:
                res = 37;
                break;
            case 17:
            case 18:
            case 19:
                res = (byte) (value - 17);
                break;
            case 20:
                res = (byte) 0xff;
                break;
        }
        return (byte) res;
    }

    /**
     * 自定义的seekbar dialog
     *
     * @param context
     * @param title
     * @param min
     * @param max
     * @param set
     */
    private void showSeekBarDialog(Context context, String title, final int min, final int max, final TextView set) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_seekbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tv_title = (TextView) view.findViewById(R.id.sb_title);
        TextView tv_start = (TextView) view.findViewById(R.id.sb_start);
        TextView tv_end = (TextView) view.findViewById(R.id.sb_end);
        final TextView tv_result = (TextView) view.findViewById(R.id.sb_result);
        TextView tv_ok = (TextView) view.findViewById(R.id.sb_ok);
        TextView tv_cancel = (TextView) view.findViewById(R.id.sb_cancel);
        SeekBar sb = (SeekBar) view.findViewById(R.id.sb_seekbar);
        tv_title.setText(title);
        tv_start.setText(min + "");
        tv_end.setText(max + "");
        tv_result.setText(set.getText());
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.setText(tv_result.getText());
                dialog.cancel();
            }
        });
        sb.setMax(max - min);
        sb.setProgress(Integer.parseInt(set.getText().toString()) - min);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int rs = min + progress;
                tv_result.setText(rs + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.show();
    }

    //presentar estado activity
    public void mostrarestado (){
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Agente_Transito agente = Constantes.agente;
        mEditText.setText("UNIDAD DE CONTROL OPERATIVO DE TRANSPORTE TERRESTRE, TRÁNSITO Y SEGURIDAD VIAL - LOJA\n"+
                "BOLETA DE CITACIÓN DE INFRACCIONES DE TRÁNSITO\n"+
                "_______________________________"+"\n"+
                "Número de citación: \n"+prefe.getString("infraccion", "")+"\n"+
                "Fecha: " + Utilidades.obtenerFechaActual() +"\n"+
                "Hora de detención: \n"+ prefe.getString("horainfraccion", "") +"\n"+
                "Hora de detención: \n"+ prefe.getString("horadetencion", "") +"\n"+
                "_______________________________"+"\n"+
                "DATOS DEL CONDUCTOR"+"\n"+
                "Identificación: "+prefe.getString("identificacion", "")+"\n"+
                "Nombres: "+prefe.getString("nombre", "")+"\n"+
                "Apellidos: "+ prefe.getString("apellido", "")+"\n"+
                "Tipo de Licencia: "+ prefe.getString("TLicencia", "") +"\n"+
                "Categoría: "+ prefe.getString("lcategoria", "") +"\n"+
                "_______________________________"+"\n"+
                "CARACTERÍSTICAS DEL VEHÍCULO"+"\n"+
                "Placa: "+prefe.getString("placa", "")+"\n"+
                "Marca: "+prefe.getString("marca", "")+"\n"+
                "Tipo: "+prefe.getString("tipo", "")+"\n"+
                "Color: "+prefe.getString("color", "")+"\n"+
                "_______________________________"+"\n"+
                "CÓDIGO ORGÁNICO INTEGRAL PENAL"+"\n"+
                "Artículo: "+prefe.getString("articulo", "")+"\n"+
                "Inciso: "+prefe.getString("inciso", "")+"\n"+
                "Numeral: "+prefe.getString("numeral", "")+"\n"+
                "_______________________________"+"\n"+
                "LOCALIZACIÓN"+"\n"+
                "Provincia: Loja"+"\n"+
                "Cantón: Loja"+"\n"+
                "Ubicación: "+ Constantes.ubicacion +"\n"+
                "Latitud: "+ Constantes.lat +"\n"+
                "Longitud: "+ Constantes.lng +"\n"+
                "_______________________________"+"\n"+
                "DESCRIPCIÓN/OBSERVACIÓN"+"\n"+
                "_______________________________"+"\n"+
                "AGENTE DE TRÁNSITO"+"\n"+
                "Nombres: "+agente.getNombre ()+"\n"+
                "Apellidos: "+agente.getApellidos ()+"\n"+
                "Identificación: "+agente.getCedula ()+"\n"+
                "Código: "+agente.getCodigo_agente ()+"\n"+"\n"+"\n"+
                "Firma: _______________________"+"\n"+
                "_____________________________"+"\n"+
                "El pago  de la multa se efectuará dentro de los 10 días hábiles posteriores a la fecha de notificación, en caso de mora se cancelará una multa adicional (2%) sobre el valor principal, por cada mes o fracción del mismo de mora hasta un máximo equivalente al (100%) de la multa. El infractor tiene 3 días para impugnar esta contravención ante el Juez de tránsito competente."+"\n"
        );
    }

    //Limpiar estado
    public void limpiar (){
        getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Override
    public void onBackPressed() {
        limpiar();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
