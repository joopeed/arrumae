package br.edu.ufcg.les142;

import android.content.Context;
import android.content.SharedPreferences;
import com.parse.Parse;


public class Application extends android.app.Application {
    public static final String APPTAG = "les-142";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;


    public void onCreate() {
        Parse.initialize(this, "tvOukSi0AaVPFCsEhBT1NcHbOK1q9CtANbPSPLhM", "LcFGhcfgRL631bzFXFfkxebgJTAurZYQWLADfB8n");
        preferences = getSharedPreferences("com.parse.anywall", Context.MODE_PRIVATE);
    }


//    public static float getSearchDistance() {
//        preferences.toString();
//        return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
//    }
}
