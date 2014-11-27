package br.edu.ufcg.les142;

import android.content.Context;
import android.content.SharedPreferences;
import br.edu.ufcg.les142.models.Relato;
import com.parse.*;


public class Application extends android.app.Application {
    public static final String APPTAG = "les142";

    // Used to pass location from InitialActivity to PostRelatoActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    // Debugging switch
    public static final boolean APPDEBUG = false;


    private static SharedPreferences preferences;

    private static ConfigHelper configHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Relato.class);
        ParseUser.logInInBackground("Rodrigo","123senha", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // Handle the response
            }
        });
        Parse.initialize(this, "6RkiEquhut1FmiAGjZ7bINdRLI02r5GAFFxVdXdK", "RAQIhDUzSDYAIzNBMw6i5gLPyf3cuT3zEXSuS5a1");

        preferences = getSharedPreferences("com.parse.les142", Context.MODE_PRIVATE);

        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }



}
