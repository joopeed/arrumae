package br.edu.ufcg.les142;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.ufcg.les142.models.Relato;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Rodr on 25/11/2014.
 */
public class PostRelatoActivity extends Activity {

    // UI references.
    private EditText postEditText;
    private Button postButton;

    private ParseGeoPoint geoPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitypostrelato);
        Intent intent = getIntent();
        Location location = intent.getParcelableExtra(Application.INTENT_EXTRA_LOCATION);
        geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        postEditText = (EditText) findViewById(R.id.post_edittext);
        postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                post();
            }
        });

  //      updatePostButtonState();

    }
    public void post(){
        String text = postEditText.getText().toString().trim();

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(PostRelatoActivity.this);
        dialog.setMessage(getString(R.string.relato));
        dialog.show();

        // Create a post.
        Relato post = new Relato();

        // Set the location to the current user's location
        post.setLocalizacao(geoPoint);
        post.setDescricao(text);
        post.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();

        // Give public read access
        acl.setPublicReadAccess(true);
        post.setACL(acl);

        // Save the post
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                finish();
            }
        });
    }



}
