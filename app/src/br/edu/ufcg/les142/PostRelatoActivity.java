package br.edu.ufcg.les142;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import br.edu.ufcg.les142.models.Comentario;
import br.edu.ufcg.les142.models.Relato;
import br.edu.ufcg.les142.models.StatusRelato;
import br.edu.ufcg.les142.models.TipoRelato;
import com.parse.*;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

import java.util.ArrayList;

import static br.edu.ufcg.les142.R.*;

/**
 * Created by Rodr on 25/11/2014.
 */
public class PostRelatoActivity extends Activity {

    // UI references.
    private EditText postEditText;
    private Button attachButton;
    private Button postButton;
    private Bitmap image;
    private Relato post;
    private ParseGeoPoint geoPoint;
    private Spinner tipoPostSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activitypostrelato);
        Intent intent = getIntent();
        Location location = intent.getParcelableExtra(Application.INTENT_EXTRA_LOCATION);
        geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        postEditText = (EditText) findViewById(id.post_edittext);
        attachButton = (Button) findViewById(id.attach_button);
        postButton = (Button) findViewById(id.post_button);
        post = new Relato();

        tipoPostSpinner = (Spinner) findViewById(id.tipo_spinner_post);
        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this,
                array.tipo_array, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoPostSpinner.setAdapter(adapter_tipo);
        tipoPostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String novo = (String) adapterView.getItemAtPosition(pos);
                System.out.println("%%%%%%%%%%%%%%%%%%% " + novo);
                if (novo != null) {
                    TipoRelato novoTipo = TipoRelato.parse(novo);
                    post.setTipoRelato(novoTipo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        postButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                post();
            }
        });
        attachButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        //      updatePostButtonState();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            post.setImage(image);

            // mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void post() {
        String text = postEditText.getText().toString().trim();
        if (descricaoEhVazia(text)) {
            StringBuilder validationErrorMessage = new StringBuilder();
            validationErrorMessage.append(getString(string.error_empty_description));
            Toast.makeText(PostRelatoActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                .show();
            return;
        }

       /* // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(PostRelatoActivity.this);
        dialog.setMessage(getString(R.string.relato));
        dialog.show();
*/
        // Create a post.
        // Set the location to the current user's location
        final ProgressDialog dialog = new ProgressDialog(PostRelatoActivity.this);
        dialog.setMessage(getString(string.progress_posting));
        dialog.show();
        post.setLocalizacao(geoPoint);
        post.setDescricao(text);
        post.setStatusRelato(StatusRelato.ABERTO);
        post.setUser(ParseUser.getCurrentUser());
        post.setComentarios(new ArrayList<String>());

        ParseACL acl = new ParseACL();
        // Give public read access
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);
        post.setACL(acl);
        post.pinInBackground();

        // Save the post
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                finish();
            }
        });
    }

    private boolean descricaoEhVazia(String text) {
        return (text == null || text.isEmpty() || text.length() == 0);
    }
}
