package br.edu.ufcg.les142;

import android.content.Context;
import android.content.SharedPreferences;
import com.parse.Parse;


public class Application extends android.app.Application {
    public static final String APPTAG = "les-142";

    // Used to pass location from MainActivity to PostRelatoActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    public void onCreate() {
        Parse.initialize(this, "tvOukSi0AaVPFCsEhBT1NcHbOK1q9CtANbPSPLhM", "LcFGhcfgRL631bzFXFfkxebgJTAurZYQWLADfB8n");
    }



}
