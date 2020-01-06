package com.example.ucot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Modelos.Accidente_Transito;
import Modelos.Evidencia;
import Modelos.Infraccion_Transito;
import Modelos.Vehiculo;
import Utilidades.Constantes;
import gestionar_accidentes.Accidente;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import gestionar_informacion.ConsultaDatos;
import gestionar_infracciones.Infraccion;
import gestionar_sincronizacion.Enviar_Archivos;
import gestionar_sincronizacion.Enviar_Info;
import gestionar_usuarios.AgenteTransito;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, OnLocationClickListener, PermissionsListener, OnCameraTrackingChangedListener,
        MapboxMap.OnCameraIdleListener, View.OnClickListener {

    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    private PermissionsManager permissionsManager;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private boolean isInTrackingMode;
    private ProgressBar progressBar;
    private Button downloadButton;
    private boolean isEndNotified;
    private int regionSelected;

    private RequestQueue request;
    private ProgressDialog dialog;

    // Offline objects
    private OfflineManager offlineManager;
    private OfflineRegion offlineRegion;

    Intent intent;
    EditText direccion;
    FloatingActionMenu actionMenu;
    FloatingActionButton infracciones, accidentes, consultas;
    private static final String PREF_NAME = "datos";

    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String CALLOUT_IMAGE_ID = "CALLOUT_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private GeoJsonSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbarprincipal);
        setSupportActionBar(toolbar);
        //boton flotante action menu
        actionMenu=(FloatingActionMenu)findViewById(R.id.MenuP);
        actionMenu.setClosedOnTouchOutside(true);
        //Boton refistrar infraccion, accidente, consulta
        infracciones = (FloatingActionButton) findViewById(R.id.Infraccion);
        infracciones.setOnClickListener(this);
        accidentes = (FloatingActionButton) findViewById(R.id.Accidente);
        accidentes.setOnClickListener(this);
        consultas = (FloatingActionButton) findViewById(R.id.Consulta);
        consultas.setOnClickListener(this);
        direccion = (EditText) findViewById(R.id.Direccion);
        downloadButton = findViewById(R.id.descargar);
        downloadButton.setVisibility(View.INVISIBLE);
        downloadButton.setEnabled(false);

        request = Volley.newRequestQueue (this);
        dialog = new ProgressDialog (this);
        dialog.setCancelable (false);
        dialog.setTitle (R.string.titulo_dialogo);
        dialog.setMessage ("Espere, por favor...");
        dialog.setIcon (R.drawable.ic_backup);

        Constantes.helper = new AdminSQLiteOpenHelper(this, Constantes.DB, null, 1);
    }

    //Geocodificacion inversa a partir de longitud y latitud
    public void setLocation(String lt, String lg) {
        Double lat, log;
        lat = Double.parseDouble(lt);
        log = Double.parseDouble(lg);
        Constantes.lat = lat;
        Constantes.lng = log;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> list = geocoder.getFromLocation(
                    lat, log, 1);
            if (!list.isEmpty()) {
                Address DirCalle = list.get(0);
                direccion.setText(DirCalle.getAddressLine(0));
                Constantes.ubicacion = DirCalle.getAddressLine (0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //controla boton atras
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    //Limpiar estado
    public void limpiar (){
        getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }

    //Metodo escucha botones flotantes
    @Override
    public void onClick(View view) {
        if (view == infracciones){
            Constantes.intento = 0;
            RegistrarInfraccion();
        }
        if (view == accidentes){
            RegistrarAccidente();
        }
        if (view == consultas){
            Consultar();
        }
    }

    //Metodo registrar infraccion
    public void RegistrarInfraccion(){
        intent = new Intent (MainActivity.this, Infraccion.class);
        startActivity(intent);
    }

    //Metodo registrar acciente
    public void RegistrarAccidente(){
        intent = new Intent (MainActivity.this, Accidente.class);
        startActivity(intent);
    }

    //Metodo registrar acciente
    public void Consultar(){
        intent = new Intent (MainActivity.this, ConsultaDatos.class);
        startActivity(intent);
    }

    //Metodo ver perfil
    public void Perfil(){
        intent = new Intent (MainActivity.this, AgenteTransito.class);
        startActivity(intent);
    }

    //Mostrar menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Metodo para agregar accion botones action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.offline){
            //Descargar e infresar a las regiones
            downloadedRegionList();
        }
        if (id==R.id.PefilUsuario){
            //Visualizar perfil usuario
            Perfil();
        }
        if (id==R.id.RegistroIncidencias){
            intent = new Intent (MainActivity.this, main.class);
            startActivity(intent);
        }
        if (id==R.id.Sincronizacion){
            dialog.show ();
            sincronizar();
        }
        if (id==R.id.Salir){
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void sincronizar() {
        final boolean[] band = new boolean[1];
        new Thread (() -> band[0] = gestionSincronizacion()).start ();
        if (band[0]) {
            dialog.cancel ();
            Toast.makeText (this, "OK", Toast.LENGTH_SHORT).show ();
        } else {
            dialog.cancel ();
            Toast.makeText (this, "ERROR", Toast.LENGTH_SHORT).show ();
        }
    }

    private boolean gestionSincronizacion() {
        boolean band = true;
        do {
            //Infraccion transito
            ArrayList<Infraccion_Transito> infracciones = Enviar_Info.obtenerInfracciones (Constantes.helper);
            if (infracciones != null) {
                Log.e("infracciones", infracciones.toString ());
                for (Infraccion_Transito infraccion:infracciones) {
                    band = Enviar_Info.crearInfraccion (request, infraccion);
//                    if (!band) return band;
                }
            }
            //Accidente tránsito
            ArrayList<Accidente_Transito> accidentes = Enviar_Info.obtenerAccidentes (Constantes.helper);
            if (accidentes != null) {
                for (Accidente_Transito accidente:accidentes) {
                    band = Enviar_Info.crearAccidente (request, accidente);

                    //if (!band) return band;
                }
            }
            //Evidencias
//            ArrayList<Evidencia> evidencias = Enviar_Info.obtenerEvidencias (Constantes.helper);
//            if (evidencias != null) {
//                for (Evidencia evidencia:evidencias) {
//                    band = Enviar_Info.crearEvidencia (request, evidencia);
//                    Enviar_Archivos archi = new Enviar_Archivos(Constantes.URL_CREAR_EVIDENCIA, evidencia.getVideo());
//                    intent = new Intent (MainActivity.this, Enviar_Archivos.class);
//                    startActivity(intent);
//                }
//            }
        }while (band);
        return band;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                progressBar = findViewById(R.id.progress_bar);
                // Assign progressBar for later use
                progressBar = findViewById(R.id.progress_bar);
                // Setup the offlineManager
                offlineManager = OfflineManager.getInstance(MainActivity.this);
                //localizacion
                enableLocationComponent(style);
                //Marcador arastrable de mapa
                ImageView hoveringMarker = new ImageView(MainActivity.this);
                hoveringMarker.setImageResource(R.drawable.mapbox_marker_icon_default);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                hoveringMarker.setLayoutParams(params);
                mapView.addView(hoveringMarker);
                // Add the camera idle listener
                mapboxMap.addOnCameraIdleListener(MainActivity.this);
                mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        //Obtiene y presenta las corrdenadas de la posicion del marcador o pinmap
                        Point destinationPoint = Point.fromLngLat(
                                mapboxMap.getCameraPosition().target.getLongitude(),
                                mapboxMap.getCameraPosition().target.getLatitude());
                        String lt = String.valueOf(destinationPoint.latitude());
                        String lg = String.valueOf(destinationPoint.longitude());
                        //Toast.makeText(MainActivity.this, "Latitud: "+lt+" Longitud: "+lg, Toast.LENGTH_LONG).show();
                        setLocation(lt, lg);
                    }
                });
            }
                });
    }

    //Metodo de movimento de la camara del mapa
    @Override
    public void onCameraIdle() {
        if (mapboxMap != null) {
            Point destinationPoint = Point.fromLngLat(
                    mapboxMap.getCameraPosition().target.getLongitude(),
                    mapboxMap.getCameraPosition().target.getLatitude());
        }
    }

    @Override
    public void onLocationComponentClick() {
        if (locationComponent.getLastKnownLocation() != null) {
            Toast.makeText(this, String.format(getString(R.string.current_location),
                    locationComponent.getLastKnownLocation().getLatitude(),
                    locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {

    }

    //mapbox localizacion
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        limpiar();
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .accuracyColor(Color.TRANSPARENT)
                    .build();
            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();
            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
            // Add the location icon click listener
            locationComponent.addOnLocationClickListener(this);
            // Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener(this);
            findViewById(R.id.Ubicacion).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(MainActivity.this, getString(R.string.tracking_enabled),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.tracking_already_enabled),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //Descargando regiones
    private void downloadRegion() {
        // Define offline region parameters, including bounds,
        // min/max zoom, and metadata
        int i =0;
        while (i<=1) {
            if(i==0) {
                // Start the progressBar
                startProgress();
                // Create offline definition using the current
                // style and boundaries of visible map area
                String styleUrl = mapboxMap.getStyle().getUrl();
                // Create a bounding box for the offline region
                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(-4.1243, -79.3979)) // Northeast
                        .include(new LatLng(-3.8257, -79.0399)) // Southwest
                        .build();
                // Define the offline region
                OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                        styleUrl,
                        latLngBounds,
                        8,
                        16,
                        MainActivity.this.getResources().getDisplayMetrics().density);
                // Build a JSONObject using the user-defined offline region title,
                // convert it into string, and use it to create a metadata variable.
                // The metadata variable will later be passed to createOfflineRegion()
                byte[] metadata;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(JSON_FIELD_REGION_NAME, "Mapa 1");
                    String json = jsonObject.toString();
                    metadata = json.getBytes(JSON_CHARSET);
                } catch (Exception exception) {
                    Timber.e("Failed to encode metadata: %s", exception.getMessage());
                    metadata = null;
                }
                // Create the offline region and launch the download
                offlineManager.createOfflineRegion(definition, metadata, new OfflineManager.CreateOfflineRegionCallback() {
                    @Override
                    public void onCreate(OfflineRegion offlineRegion) {
                        Timber.d("Offline region created: %s", "Mapa 1");
                        MainActivity.this.offlineRegion = offlineRegion;
                        launchDownload();
                    }
                    @Override
                    public void onError(String error) {
                        Timber.e("Error: %s", error);
                    }
                });
            }
            if(i==1) {
                // Start the progressBar
                startProgress();
                // Create offline definition using the current
                // style and boundaries of visible map area
                String styleUrl = mapboxMap.getStyle().getUrl();
                // Create a bounding box for the offline region
                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(-4.3106, -79.3527)) // Northeast
                        .include(new LatLng(-4.0946, -79.1319)) // Southwest
                        .build();
                // Define the offline region
                OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                        styleUrl,
                        latLngBounds,
                        8,
                        16,
                        MainActivity.this.getResources().getDisplayMetrics().density);
                // Build a JSONObject using the user-defined offline region title,
                // convert it into string, and use it to create a metadata variable.
                // The metadata variable will later be passed to createOfflineRegion()
                byte[] metadata;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(JSON_FIELD_REGION_NAME, "Mapa 2");
                    String json = jsonObject.toString();
                    metadata = json.getBytes(JSON_CHARSET);
                } catch (Exception exception) {
                    Timber.e("Failed to encode metadata: %s", exception.getMessage());
                    metadata = null;
                }
                // Create the offline region and launch the download
                offlineManager.createOfflineRegion(definition, metadata, new OfflineManager.CreateOfflineRegionCallback() {
                    @Override
                    public void onCreate(OfflineRegion offlineRegion) {
                        Timber.d("Offline region created: %s", "Mapa 2");
                        MainActivity.this.offlineRegion = offlineRegion;
                        launchDownload();
                    }
                    @Override
                    public void onError(String error) {
                        Timber.e("Error: %s", error);
                    }
                });
            }
            i++;
        }
    }
    private void launchDownload() {
        // Set up an observer to handle download progress and
        // notify the user when the region is finished downloading
        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
            @Override
            public void onStatusChanged(OfflineRegionStatus status) {
                // Compute a percentage
                double percentage = status.getRequiredResourceCount() >= 0
                        ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                        0.0;
                if (status.isComplete()) {
                    // Download complete
                    endProgress(getString(R.string.end_progress_success));
                    return;
                } else if (status.isRequiredResourceCountPrecise()) {
                    // Switch to determinate state
                    setPercentage((int) Math.round(percentage));
                }
                // Log what is being currently downloaded
                Timber.d("%s/%s resources; %s bytes downloaded.",
                        String.valueOf(status.getCompletedResourceCount()),
                        String.valueOf(status.getRequiredResourceCount()),
                        String.valueOf(status.getCompletedResourceSize()));
            }
            @Override
            public void onError(OfflineRegionError error) {
                Timber.e("onError reason: %s", error.getReason());
                Timber.e("onError message: %s", error.getMessage());
            }
            @Override
            public void mapboxTileCountLimitExceeded(long limit) {
                Timber.e("Mapbox tile count limit exceeded: %s", limit);
            }
        });
        // Change the region state
        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
    }
    private void downloadedRegionList() {
        // Build a region list when the user clicks the list button
        // Reset the region selected int to 0
        regionSelected = 0;
        // Query the DB asynchronously
        offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
            @Override
            public void onList(final OfflineRegion[] offlineRegions) {
                // Check result. If no regions have been
                // downloaded yet, notify user and return
                if (offlineRegions == null || offlineRegions.length == 0) {
                    int TIEMPO = 100; //como ejemplo  segundos (1,000 milisegundos) para que se haga click automaticamente
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //Ejecuta clic en boton, metodo o proceso.
                            downloadButton.performClick();
                        }
                    }, TIEMPO);
                    downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadRegion();
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Descargando archivos, no manipule el dispositivo porfavor", Toast.LENGTH_LONG).show();
                    return;
                }
                // Add all of the region names to a list
                ArrayList<String> offlineRegionsNames = new ArrayList<>();
                for (OfflineRegion offlineRegion : offlineRegions) {
                    offlineRegionsNames.add(getRegionName(offlineRegion));
                }
                final CharSequence[] items = offlineRegionsNames.toArray(new CharSequence[offlineRegionsNames.size()]);
                // Build a dialog containing the list of regions
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.navigate_title))
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Track which region the user selects
                                regionSelected = which;
                            }
                        })
                        .setPositiveButton(getString(R.string.navigate_positive_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(MainActivity.this, items[regionSelected], Toast.LENGTH_LONG).show();
                                // Get the region bounds and zoom
                                LatLngBounds bounds = ((OfflineTilePyramidRegionDefinition)
                                        offlineRegions[regionSelected].getDefinition()).getBounds();
                                double regionZoom = ((OfflineTilePyramidRegionDefinition)
                                        offlineRegions[regionSelected].getDefinition()).getMinZoom();
                                // Create new camera position
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(bounds.getCenter())
                                        .zoom(regionZoom)
                                        .build();
                                // Move camera to new position
                                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        })
                        .setNegativeButton(getString(R.string.navigate_negative_button_title), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // When the user cancels, don't do anything.
                                // The dialog will automatically close
                            }
                        }).create();
                dialog.show();
            }
            @Override
            public void onError(String error) {
                Timber.e( "Error: %s", error);
            }
        });
    }
    private String getRegionName(OfflineRegion offlineRegion) {
        // Get the region name from the offline region metadata
        String regionName;
        try {
            byte[] metadata = offlineRegion.getMetadata();
            String json = new String(metadata, JSON_CHARSET);
            JSONObject jsonObject = new JSONObject(json);
            regionName = jsonObject.getString(JSON_FIELD_REGION_NAME);
        } catch (Exception exception) {
            Timber.e("Failed to decode metadata: %s", exception.getMessage());
            regionName = String.format(getString(R.string.region_name), offlineRegion.getID());
        }
        return regionName;
    }
    private void startProgress() {
        // Disable buttons
        downloadButton.setEnabled(false);
        //Desactivar la inreraccion del usuario minetras se produce la descarga
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // Start and show the progress bar
        isEndNotified = false;
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void setPercentage(final int percentage) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(percentage);
    }
    private void endProgress(final String message) {
        // Don't notify more than once
        if (isEndNotified) {
            return;
        }
        // Enable buttons
        downloadButton.setEnabled(true);
        //Habilitar la interaccion del usuario unavez que finalice la descarga
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // Stop and hide the progress bar
        isEndNotified = true;
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        // Show a toast
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
