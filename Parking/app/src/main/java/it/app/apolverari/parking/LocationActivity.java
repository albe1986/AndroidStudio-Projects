package it.app.apolverari.parking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private DBManager db;
    private Geocoder g;
    private Double latitude, longitude;
    private List<android.location.Address> addresses;
    private String address;
    private static final int REQUEST_CODE = 1;
    private Bitmap pic;
    private boolean isNew = true;
    private String picPath = "";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private EditText fieldNote;
    private EditText fieldTitle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        g = new Geocoder(this, Locale.getDefault());
        db = new DBManager(this);
        fieldNote = (EditText) findViewById(R.id.park_note);
        fieldTitle = (EditText) findViewById(R.id.park_title);
        setButtons();
        Intent i  = getIntent();
        if (i.getExtras() != null) {
            isNew = i.getBooleanExtra("isNew", true);
            Parking p = (Parking) i.getExtras().get("park");
            String coordinate = db.getCoordinate(p.getAddress(), p.getDate());
            String note = db.getNote(p.getAddress(), p.getDate());
            latitude = Double.parseDouble(coordinate.split(":")[0]);
            longitude = Double.parseDouble(coordinate.split(":")[1]);
            fieldTitle.setEnabled(false);
            fieldNote.setText(note);
            fieldNote.setEnabled(false);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (latitude != null && longitude != null) {
            LatLng coordinate = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Car"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        }
    }

    private void setButtons() {
        ImageButton cancel = (ImageButton) findViewById(R.id.location_canc);
        ImageButton delete = (ImageButton) findViewById(R.id.location_del);
        ImageButton save = (ImageButton) findViewById(R.id.location_save);
        ImageButton pic = (ImageButton) findViewById(R.id.location_pic);
        ImageButton edit = (ImageButton) findViewById(R.id.location_edit);
        ImageButton go = (ImageButton) findViewById(R.id.location_go);
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
                if (res) {
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

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldNote.setEnabled(true);
                fieldTitle.setEnabled(true);
            }
        });
    }

    private File createImageFile() throws IOException {
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
        picPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(picPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null && isNew) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
            }
            if (latitude != null && longitude != null) {
                LatLng coordinate = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Car"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));
                setParkAddress();
            }
        } catch (SecurityException e) {
            Toast.makeText(LocationActivity.this, "Attenzione: permessi di localizzaizione non concessi", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setParkAddress(){
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
