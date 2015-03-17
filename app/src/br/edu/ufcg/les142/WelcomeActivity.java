package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static br.edu.ufcg.les142.R.*;

/**
 * Created by Rodrigo on 08/12/2014.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.welcome);

        // Log in button click handler
        Button loginButton = (Button) findViewById(id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        // Sign up button click handler
        Button signupButton = (Button) findViewById(id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            }
        });
    }
}