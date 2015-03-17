package br.edu.ufcg.les142;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
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
    private List<Bitmap > bitmaps;
    private Bitmap image;
    private LazyAdapter adapter;
    private Installation myInst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myInst = new Installation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);
        comentarios = new ArrayList<String>();



        listView = (ListView) findViewById(R.id.list);
        commentButton = (Button) findViewById(R.id.commentButton);
        commentPhotoButton = (Button) findViewById(R.id.CommentPhotobutton);
        commentTextView = (TextView) findViewById(R.id.commentTextView);
        comentario = new Comentario();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        rel_id = bundle.getString("rel_id");
        ParseQuery<Relato> query = Relato.getQuery();
        query.fromLocalDatastore();
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

        adapter = new LazyAdapter(CommentListActivity.this, comentarios, bitmaps);

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
        int i = 0;
        ParseQuery<Comentario> query = ParseQuery.getQuery("Comentario");
        for (Comentario co : relato.getComentarios()) {

            comentarios.add("Mopa" + ": "  + co.getText());
            try{byte[] bytes = co.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmaps.add(bitmap);}
            catch(Exception e) {}
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
        }
    }

    private void comment() {
        ProgressDialog dialog = new ProgressDialog(CommentListActivity.this);
        dialog.setMessage(getString(R.string.progress_posting));
        dialog.show();
        String text = commentTextView.getText().toString().trim();
        comentario.setImage(image ,text );
        comentario.setText(text);
        comentario.setUser(ParseUser.getCurrentUser());

        relato.addComentario(comentario);



        // Save the post
        relato.saveInBackground();
        try{
            myInst.sendCommentPush(rel_id);
        }catch(Exception e){
            Log.d(Application.APPTAG, e.getMessage());
            Toast.makeText(CommentListActivity.this,
                    "Erro ao enviar notificação.", Toast.LENGTH_LONG).show();
        }


        recreate();
        dialog.cancel();


    }

}
