package gestionar_evidencias;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ucot.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

import Utilidades.Constantes;

public class Audio extends AppCompatActivity{
    private static final int REQUEST_RECORD_AUDIO = 0;
    static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    private  String AUDIO_FILE_PATH = Environment.getExternalStorageDirectory().getPath()+ "/"+timeStamp+".mp3";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        }

        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        recordAudio();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                Constantes.audio = AUDIO_FILE_PATH;
                Toast.makeText(this, "Guardado con exito", Toast.LENGTH_LONG).show();
                onBackPressed();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio no guardado", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }

    public void recordAudio() {
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }
}




//
//
//
//    private Button startbtn, stopbtn, playbtn, stopplay;
//    private MediaRecorder mRecorder;
//    private MediaPlayer mPlayer;
//    private static final String LOG_TAG = "AudioRecording";
//    private static String mFileName = null;
//
//    @SuppressLint("LogNotTimber")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_audio);
//
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(Audio.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission_group.MICROPHONE}, 1000);
//        }
//        //tiempo de multimedia
//        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        startbtn = findViewById(R.id.btnRecord);
//        stopbtn = findViewById(R.id.btnStop);
//        playbtn = findViewById(R.id.btnPlay);
//        stopplay = findViewById(R.id.btnStopPlay);
//        stopbtn.setEnabled(false);
//        playbtn.setEnabled(false);
//        stopplay.setEnabled(false);
//        startbtn.setOnClickListener(this);
//        stopbtn.setOnClickListener(this);
//        playbtn.setOnClickListener(this);
//        stopplay.setOnClickListener(this);
//        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//        mFileName += "/AudioRecording" + timeStamp + ".mp3";
//    }
//
//    public void Grabar(){
//        stopbtn.setEnabled(true);
//        startbtn.setEnabled(false);
//        playbtn.setEnabled(false);
//        stopplay.setEnabled(false);
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mRecorder.setOutputFile(mFileName);
//        try {
//            mRecorder.prepare();
//            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
//
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
//        }
//        mRecorder.start();
//        Toast.makeText(getApplicationContext(), "Grabación iniciada", Toast.LENGTH_LONG).show();
//    }
//
//    public void Detener (){
//        stopbtn.setEnabled(false);
//        startbtn.setEnabled(true);
//        playbtn.setEnabled(true);
//        stopplay.setEnabled(true);
//        mRecorder.stop();
//        mRecorder.release();
//        mRecorder = null;
//        Toast.makeText(getApplicationContext(), "Grabación detenida", Toast.LENGTH_LONG).show();
//    }
//
//    public void Reproducir (){
//        stopbtn.setEnabled(false);
//        startbtn.setEnabled(true);
//        playbtn.setEnabled(false);
//        stopplay.setEnabled(true);
//        mPlayer = new MediaPlayer();
//        try {
//            mPlayer.setDataSource(mFileName);
//            mPlayer.prepare();
//            mPlayer.start();
//            Toast.makeText(getApplicationContext(), "Grabación comenzó a reproducirse", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
//        }
//    }
//
//    public void DetenerReproducir(){
//        mPlayer.release();
//        mPlayer = null;
//        stopbtn.setEnabled(false);
//        startbtn.setEnabled(true);
//        playbtn.setEnabled(true);
//        stopplay.setEnabled(false);
//        Toast.makeText(getApplicationContext(), "Reproducción de audio detenido\n", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if ( v == startbtn){
//            Grabar();
//        }
//        if ( v == stopbtn){
//            Detener();
//        }
//        if ( v == playbtn){
//            Reproducir();
//        }
//        if ( v == stopplay){
//            DetenerReproducir();
//        }
//    }