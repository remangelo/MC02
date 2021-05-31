package ph.dlsu.s12.remudaroa.mc02;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;

/**
 * Created by User on 10/2/2017.
 */

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;


    //widgets
    private ImageView mGps;
    private Button mainBtn;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mainBtn = (Button) findViewById(R.id.mainBtn);

        getLocationPermission();

    }

    private void init(){
        Log.d(TAG, "init: initializing");

        GoogleApiClient mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewMain = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(viewMain);
            }
        });

        hideSoftKeyboard();
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            LatLng currentCoords = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                            DecimalFormat df = new DecimalFormat("#,###,##0.00");
                            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
                            LatLng coords;

                            /*
                                Adding Quarantine Facilities Markers
                            * */

                            coords = new LatLng(14.60657573706729, 121.02360211788726);
                            MarkerOptions options = new MarkerOptions()
                                    .position(coords)
                                    .title("San Juan Medical Center")
                                    .snippet("Address: 71 N Domingo St, San Juan, 1500 Metro Manila, Philippines\n" +
                                            "Contact: +63287243266\n" +
                                            "Capacity: 73\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            Marker mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.611430543695239, 121.05516241728166);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Calutan Training Center")
                                    .snippet("Address: 1109, 502 Epifanio de los Santos Ave, Cubao, Quezon City, 1109 Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 87\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.597815418892251, 121.04553296225201);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Cardinal Santos Medical Center")
                                    .snippet("Address: 10 Wilson, Greenhills West, San Juan, 1502 Metro Manila, Philippines\n" +
                                            "Contact: +63287270001\n" +
                                            "Capacity: 48\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.581054470643778, 121.04291313050021);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("National Center For Mental Health, Mandaluyong City")
                                    .snippet("Address: Nueve de Pebrero St., Mauway, Mandaluyong, Metro Manila, Philippines\n" +
                                            "Contact: +63285319001\n" +
                                            "Capacity: 30\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.58350927408578, 121.04422104756158);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Neptali A. Gonzales High School Facility (MPNAG), Mandaluyong City")
                                    .snippet("Address: Nueve De Pebrero St. Mauway, Mandaluyong, Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 150\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.577329036115614, 121.03448498714863);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Makeshift Hospital, Maysilo Circle Brgy. Plainview (Old Mandaluyong Gym)")
                                    .snippet("Address: Maysilo Cir, Mandaluyong, Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 30" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.571488456916098, 121.04435499536285);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("South Sikap Brgy. Plainview Quarantine Facility, Mandaluyong")
                                    .snippet("Address: 291 Dansalan Rd, Mandaluyong, Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 10\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.550436111618646, 121.02209674368552);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Hotel Celeste")
                                    .snippet("Address: 02 San Lorenzo Drive Corner A. Arnaiz Avenue, San Lorenzo Village, Makati, 1223 Metro Manila\n" +
                                            "Contact: +63288878080\n" +
                                            "Capacity: 20\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.570242461042557, 121.03010392908726);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Poblacion Health Center")
                                    .snippet("Address: A. Mabini St, Makati, 1210 Metro Manila\n" +
                                            "Contact: +63288995014\n" +
                                            "Capacity: 9\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.56713715865379, 121.05044189258233);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Makati Friendship Suites")
                                    .snippet("Address: Guadalupe Bliss, Phase 3 Brgy, Makati, Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 11\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.487973951749593, 121.06279199205055);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Lakeshore Hall")
                                    .snippet("Address: 393 C-6, Lower Bicutan, Taguig, Metro Manila\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 40\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.54734104619811, 121.05988538543392);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Pembo Elementary School")
                                    .snippet("Address: Dahlia Street, Pembo, Makati City, Metro Manila, Philippines\n" +
                                            "Contact: +63288821129\n" +
                                            "Capacity: 45\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.546096662981123, 121.06216201887713);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Ospital ng Makati Parking")
                                    .snippet("Address: Sampaguita St, Makati, 1218 Metro Manila, Philippines\n" +
                                            "Contact: +63288826316\n" +
                                            "Capacity: 15\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.572329335462218, 121.06550033256218);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Dahlia Hotel")
                                    .snippet("Address: Christian Rte, Pasig, Metro Manila, Philippines\n" +
                                            "Contact: +639497090186\n" +
                                            "Capacity: 300\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            coords = new LatLng(14.546812955997947, 121.0669481547294);
                            options = new MarkerOptions()
                                    .position(coords)
                                    .title("Pateros Evacuation Building, 2nd Floor")
                                    .snippet("Address: 321 P. Herrera, Pateros, Metro Manila, Philippines\n" +
                                            "Contact: No Available Data\n" +
                                            "Capacity: 4\n" + "Distance from location: " + df.format(SphericalUtil.computeDistanceBetween(coords, currentCoords)/1000) + " KM");
                            mMarker = mMap.addMarker(options);

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }


    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}











