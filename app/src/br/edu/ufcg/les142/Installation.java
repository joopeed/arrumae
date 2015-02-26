package br.edu.ufcg.les142;

import android.util.Log;
import com.parse.*;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Rodrigo on 10/02/2015.
 */
public class Installation {

    public Installation(){
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public void install(){
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
    }

    public void sendApoioPush(String channel) throws JSONException {
        JSONObject data = new JSONObject();
        try{
            data = new JSONObject();
            data.put("action", ArrumaeBroadcastReceiver.ACTION);
            data.put("alet", "Um relato que você apoia/criou foi apoiado!");
            data.put("relato", channel);
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
        }
        ParsePush push = new ParsePush();
        push.setChannel(channel);
        push.setData(data);
        push.sendInBackground();

    }

    public void sendCommentPush(String channel) throws JSONException {
        JSONObject data = new JSONObject();
        try{
            data = new JSONObject();
            data.put("action", ArrumaeBroadcastReceiver.ACTION);
            data.put("alet", "Um relato que você apoia/criou foi comentado!");
            data.put("relato", channel);
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
        }ParsePush push = new ParsePush();
        push.setChannel(channel);
        push.setData(data);
        push.sendInBackground();
    }

    public void subscribe(String channel){
        ParsePush.subscribeInBackground(channel.trim().replace(" ", "_").replace("/", "_"));


    }
    public void unsubscribe(String channel){
        ParsePush.unsubscribeInBackground(channel.trim().replace(" ", "_").replace("/", "_"));

    }


}
