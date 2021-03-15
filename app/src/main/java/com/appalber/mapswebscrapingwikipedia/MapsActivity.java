package com.appalber.mapswebscrapingwikipedia;

import androidx.core.os.HandlerCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Actualizacion {

    private GoogleMap mMap;
    private EditText ciudad;
    private Button boton;
    private static String nombre;
    private Actualizacion contexto = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ciudad = findViewById(R.id.et_nombreciudad);
        boton = findViewById(R.id.btn_solicitar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = ciudad.getText().toString();
                Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

                AccesoJsoup hilohandler = new AccesoJsoup(nombre, contexto, mainThreadHandler);
                Thread hilo = new Thread(hilohandler);
                hilo.start();
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    @Override
    public void Actualizar(LatLng coodenadas) {
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(coodenadas).title("Marker in " + nombre));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(coodenadas));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coodenadas, 8));    }
}
