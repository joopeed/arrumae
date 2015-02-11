package br.edu.ufcg.les142;

import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseInstallation;


/**
 * Created by Rodrigo on 10/02/2015.
 */
public class Installation {

    public Installation(){
    }

    public void install(){
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
    }

    public void sendApoioPush(String channel){
        ParsePush push = new ParsePush();
        push.setChannel(channel);
        push.setMessage("Um relato que você apoia/criou foi apoiado");
        push.sendInBackground();

    }

    public void subscribe(String channel){
        ParsePush.subscribeInBackground(channel);


    }
    public void unsubscribe(String channel){
        ParsePush.unsubscribeInBackground(channel);

    }

    public void sendCommentPush(String channel) {
        ParsePush push = new ParsePush();
        push.setChannel(channel);
        push.setMessage("Um relato que você apoia/criou foi comentado");
        push.sendInBackground();
    }
}
