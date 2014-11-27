package br.edu.ufcg.les142;

import android.content.Context;
import android.content.SharedPreferences;
import com.parse.Parse;
import com.parse.ParseObject;


public class Application extends android.app.Application {
    public static final String APPTAG = "les142";

    // Used to pass location from InitialActivity to PostRelatoActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    public void onCreate() {
        Parse.initialize(this, "6RkiEquhut1FmiAGjZ7bINdRLI02r5GAFFxVdXdK", "RAQIhDUzSDYAIzNBMw6i5gLPyf3cuT3zEXSuS5a1");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        System.out.println("foo");
    }



}
