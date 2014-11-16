package br.edu.ufcg.les142;

import com.parse.Parse;

/**
 * Created by Leticia on 15/11/2014.
 */
public class Application extends android.app.Application {
    public static final String APPTAG = "les-142";

    public void onCreate() {
        Parse.initialize(this, "tvOukSi0AaVPFCsEhBT1NcHbOK1q9CtANbPSPLhM", "LcFGhcfgRL631bzFXFfkxebgJTAurZYQWLADfB8n");
    }
}
