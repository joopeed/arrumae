package br.edu.ufcg.les142;

import android.util.Log;
import com.parse.*;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Rodrigo on 10/02/2015.
 */
public class Installation {
    ParseQuery pushQuery;

    public Installation(){
        pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("relato", true);
    }

    public void install(){
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
    }

    public void sendApoioPush(String channel) throws JSONException {
        JSONObject data = new JSONObject();
        try{
            data = new JSONObject("{\"action\": \"br.edu.ufcg.les142.CUSTOM_NOTIFICATION\"," +
                   "\"alert\": \"Um relato que você apoia/criou foi apoiado!\"," +
                    " \"relato\": \"".concat(channel.concat("\"}")));
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
        }
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery);
        push.setChannel(channel);
        push.setData(data);
        push.sendInBackground();

    }

    public void sendCommentPush(String channel) throws JSONException {
        JSONObject data = new JSONObject();
        try{
            data = new JSONObject("{\"action\": \"br.edu.ufcg.les142.CUSTOM_NOTIFICATION\"," +
                    "\"alert\": \"Um relato que você apoia/criou foi comentado!\"," +
                    " \"relato\": \"".concat(channel.concat("\"}")));
        }catch(JSONException e){
            Log.d("JsonException", e.toString());
        }ParsePush push = new ParsePush();
        push.setQuery(pushQuery);
        push.setChannel(channel);
        push.setData(data);
        push.sendInBackground();
    }

    public void subscribe(String channel){
        ParsePush.subscribeInBackground(channel);


    }
    public void unsubscribe(String channel){
        ParsePush.unsubscribeInBackground(channel);

    }


}
