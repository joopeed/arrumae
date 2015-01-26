package br.edu.ufcg.les142;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.edu.ufcg.les142.models.Comentario;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasmc on 26/01/15.
 */
public class CommentListActivity extends Activity {

    private ArrayList<Comentario> comentarios;
    private ListView listView;
    private Button commentButton;
    private TextView commentTextView;
    private Comentario comentario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);

        listView = (ListView) findViewById(R.id.list);
        commentButton = (Button) findViewById(R.id.commentButton);
        commentTextView = (TextView) findViewById(R.id.commentTextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        comentarios = bundle.getParcelableArrayList("comentarios");


        comentario = new Comentario();
        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comment();
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, (List) comentarios);
        listView.setAdapter(adapter);
    }

    private void comment() {
        String text = commentTextView.getText().toString().trim();

        final ProgressDialog dialog = new ProgressDialog(CommentListActivity.this);
        dialog.setMessage(getString(R.string.progress_posting));
        dialog.show();
        comentario.setText(text);
        comentarios.add(comentario);

        ParseACL acl = new ParseACL();
        // Give public read access
        acl.setPublicReadAccess(true);
        comentario.setACL(acl);

        // Save the comment
        comentario.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                finish();
            }
        });

    }

}
