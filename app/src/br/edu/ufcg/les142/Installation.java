package br.edu.ufcg.les142;

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
}
