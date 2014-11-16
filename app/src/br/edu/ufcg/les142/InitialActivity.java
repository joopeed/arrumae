package br.edu.ufcg.les142;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class InitialActivity extends FragmentActivity {
    /*
     * Initialize the Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}