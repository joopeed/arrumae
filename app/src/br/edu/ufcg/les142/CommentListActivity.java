package br.edu.ufcg.les142;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.edu.ufcg.les142.models.Comentario;
import br.edu.ufcg.les142.models.Relato;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasmc on 26/01/15.
 */
public class CommentListActivity extends Activity {

    private Relato relato;
    private Comentario comentario;
    private ListView listView;
    private Button commentButton;
    private Button commentPhotoButton;
    private TextView commentTextView;
    private String rel_id;
    private List<String > comentarios;
    private Bitmap image;

    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);

        listView = (ListView) findViewById(R.id.list);
        commentButton = (Button) findViewById(R.id.commentButton);
        commentPhotoButton = (Button) findViewById(R.id.CommentPhotobutton);
        commentTextView = (TextView) findViewById(R.id.commentTextView);
        comentario = new Comentario();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        rel_id = bundle.getString("rel_id");
        ParseQuery<Relato> query = Relato.getQuery();
        comentarios = new ArrayList<String>();

        query.getInBackground(rel_id, new GetCallback<Relato>() {
            @Override
            public void done(Relato rel, ParseException e) {
                if (e == null) {
                    relato = rel;
                    loadRelatos();
                    listView.setAdapter(adapter);
                }
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comentarios);
        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comment();
            }
        });
        commentPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });




    }

    private void loadRelatos() {
        for (Comentario co : relato.getComentarios()) {
            comentarios.add(co.getText());
        }
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
            comentario.setImage(image);
        }
    }

    private void comment() {
        final ProgressDialog dialog = new ProgressDialog(CommentListActivity.this);
        dialog.setMessage(getString(R.string.progress_posting));
        dialog.show();
        String text = commentTextView.getText().toString().trim();

        comentario.setText(text);
        comentario.setUser(ParseUser.getCurrentUser());
        relato.addComentario(comentario);


        // Save the post
        relato.saveInBackground();
        recreate();
        dialog.cancel();


    }

}
