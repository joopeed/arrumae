package br.edu.ufcg.les142;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.SupportMapFragment;

public class InitialActivity extends FragmentActivity {

    private SupportMapFragment mapa;
    /*
     * Initialize the Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Enable the current location "blue dot"
        mapa.getMap().setMyLocationEnabled(true);
    }
}