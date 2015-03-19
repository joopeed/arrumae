package br.edu.ufcg.les142;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.*;
import br.edu.ufcg.les142.models.Comentario;
import br.edu.ufcg.les142.models.Relato;
import com.parse.*;

import java.util.*;

import static br.edu.ufcg.les142.R.*;


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
    private ArrayList<Integer> hasPhotos;
    private List<Bitmap > bitmaps;
    private Bitmap image = null;
    private LazyAdapter adapter;
    private Installation myInst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myInst = new Installation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);
        comentarios = new ArrayList<String>();
        bitmaps = new ArrayList<Bitmap>();
        hasPhotos = new ArrayList<Integer>();

        listView = (ListView) findViewById(id.list);
        commentButton = (Button) findViewById(id.commentButton);
        commentPhotoButton = (Button) findViewById(id.CommentPhotobutton);
        commentTextView = (TextView) findViewById(id.commentTextView);
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

        adapter = new LazyAdapter(CommentListActivity.this, comentarios, bitmaps, hasPhotos);

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
        query.whereEqualTo("relatoID", rel_id);
        query.findInBackground(new FindCallback<Comentario>() {
            @Override
            public void done(List<Comentario> comments, ParseException e) {
                for (Comentario co : comments) {
                    try {
                        ParseUser u = (co.getParseUser("user"));
                        comentarios.add(u.getUsername() + ": " + co.getText());
                        hasPhotos.add(0);

                        try {
                            byte[] bytes = co.getImage();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            hasPhotos.remove(hasPhotos.lastIndexOf(0));
                            hasPhotos.add(1);

                        } catch (Exception el) {
                            Log.d("ARRUMAE ERROR", "Erro ao tentar pegar imagens");
                        }



                    } catch (Exception el) {
                        el.printStackTrace();
                    }
                }
            }
        });



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
            comentario.setHasPhoto(true);
        }
    }

    private void comment() {

        ProgressDialog dialog = new ProgressDialog(CommentListActivity.this);
        String text = commentTextView.getText().toString().trim();
        if(descricaoEhVazia(text)){
            Toast.makeText(this, string.error_empty_description,  Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage(getString(R.string.progress_posting));
        dialog.show();

        comentario.setText(text);
        comentario.setUser(ParseUser.getCurrentUser());
        comentario.setRelatoID(rel_id);
        comentario.saveInBackground();
        relato.addComentario(comentario.getObjectId());

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

    private boolean descricaoEhVazia(String text) {
        return (text == null || text.isEmpty() || text.length() == 0);
    }
}
