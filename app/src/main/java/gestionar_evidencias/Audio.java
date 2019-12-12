package gestionar_evidencias;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ucot.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audio extends AppCompatActivity {

    private Button startbtn, stopbtn, playbtn, stopplay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;

    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        //tiempo de multimedia
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        startbtn = findViewById(R.id.btnRecord);
        stopbtn = findViewById(R.id.btnStop);
        playbtn = findViewById(R.id.btnPlay);
        stopplay = findViewById(R.id.btnStopPlay);
        stopbtn.setEnabled(false);
        playbtn.setEnabled(false);
        stopplay.setEnabled(false);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording"+timeStamp+".mp3";

        startbtn.setOnClickListener(v -> {
                stopbtn.setEnabled(true);
                startbtn.setEnabled(false);
                playbtn.setEnabled(false);
                stopplay.setEnabled(false);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(mFileName);
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                mRecorder.start();
                Toast.makeText(getApplicationContext(), "Grabación iniciada", Toast.LENGTH_LONG).show();
            });
        stopbtn.setOnClickListener(v -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(true);
            stopplay.setEnabled(true);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(getApplicationContext(), "Grabación detenida", Toast.LENGTH_LONG).show();
        });
        playbtn.setOnClickListener(v -> {
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(false);
            stopplay.setEnabled(true);
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
                Toast.makeText(getApplicationContext(), "Grabación comenzó a reproducirse", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        });
        stopplay.setOnClickListener(v -> {
            mPlayer.release();
            mPlayer = null;
            stopbtn.setEnabled(false);
            startbtn.setEnabled(true);
            playbtn.setEnabled(true);
            stopplay.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Reproducción de audio detenido\n", Toast.LENGTH_SHORT).show();
        });
    }

}