package gestionar_sincronizacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.Objects;

public class Enviar_Archivos extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private String url;
    private String path;

    public Enviar_Archivos(String url, String path) {
        this.url = url;
        this.path = path;
        performFileSearch ();
    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri;
            if (data != null) {
                uri = data.getData();
                Toast.makeText(this, "Uri"+ Objects.requireNonNull (uri).toString(), Toast.LENGTH_SHORT).show();

                System.out.println("#########################3");
                System.out.println(data);

                File file = new File(path);
                Ion.with(this)
                        .load("POST", url) //url de query
                        .setHeader("Cache-Control", "No-Cache")
                        .noCache()
                        //.setMultipartParameter("Id_Evidencia","9765499")
                        .setMultipartFile("Video","multipart/form-data", file)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                Toast.makeText(getApplicationContext(), "SUCCESSSSSS ", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }
}
