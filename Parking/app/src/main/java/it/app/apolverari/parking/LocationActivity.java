package it.app.apolverari.parking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private DBManager db;
    private LocationManager locationManager;
    private Geocoder g;
    private Double latitude, longitude;
    private List<android.location.Address> addresses;
    private String address;
    private static final int REQUEST_CODE = 1;
    private Bitmap pic;
    private String picPath = "";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e){
            Toast.makeText(LocationActivity.this, "Attenzione: permessi di localizzaizione non concessi", Toast.LENGTH_SHORT).show();
        }
        g = new Geocoder(this, Locale.getDefault());
        db = new DBManager(this);
        setButtons();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if (latitude != null && longitude != null) {
            LatLng coordinate = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Car"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
            try {
                addresses = g.getFromLocation(latitude,longitude, 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditText et = (EditText) findViewById(R.id.park_title);
            if (!addresses.isEmpty()) {
                address = addresses.get(0).getAddressLine(0) + ", " +
                        addresses.get(0).getLocality() + ", " +
                        addresses.get(0).getCountryName() + ", " +
                        addresses.get(0).getPostalCode();
                et.setText(address);
            } else {
                et.setText("NewParking");
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (latitude != null && longitude != null) {
            LatLng coordinate = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Car"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        }
    }

    private void setButtons(){
        ImageButton cancel = (ImageButton) findViewById(R.id.location_canc);
        ImageButton delete = (ImageButton) findViewById(R.id.location_del);
        ImageButton save = (ImageButton) findViewById(R.id.location_save);
        ImageButton pic = (ImageButton) findViewById(R.id.location_pic);
        ImageButton share = (ImageButton) findViewById(R.id.location_share);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.park_title);
                EditText notes = (EditText) findViewById(R.id.park_note);
                String coordinate = latitude.toString() + ":" + longitude.toString();
                String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                db.save(title.getText().toString(),
                        coordinate,
                        notes.getText().toString(),
                        picPath,
                        data);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.park_title);
                EditText notes = (EditText) findViewById(R.id.park_note);
                String coordinate = latitude.toString() + ":" + longitude.toString();
                boolean res = db.delete(title.getText().toString(), coordinate, notes.getText().toString());
                if(res){
                    Toast.makeText(LocationActivity.this, "Parcheggio eliminato correttamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, 1);
                    }
                }
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        EditText title = (EditText) findViewById(R.id.park_title);
        String imageFileName = "Parking_" + timeStamp + "_" + title.getText().toString();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        picPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            try {
                pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(picPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
