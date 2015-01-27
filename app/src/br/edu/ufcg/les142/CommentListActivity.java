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
import br.edu.ufcg.les142.models.Relato;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasmc on 26/01/15.
 */
public class CommentListActivity extends Activity {

    private Relato relato;
    private ListView listView;
    private Button commentButton;
    private TextView commentTextView;
    private String rel_id;
    private List<String > comentarios;

    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);

        listView = (ListView) findViewById(R.id.list);
        commentButton = (Button) findViewById(R.id.commentButton);
        commentTextView = (TextView) findViewById(R.id.commentTextView);

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




    }

    private void comment() {
        String text = commentTextView.getText().toString().trim();
        Comentario c = new Comentario();
        c.setText(text);

        for (Comentario co : relato.getComentarios()) {
            comentarios.add(co.getText());
        }


        relato.addComentario(c);


        // Save the post
        relato.saveInBackground();
        

    }

}
