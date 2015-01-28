package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import br.edu.ufcg.les142.models.Comentario;
import br.edu.ufcg.les142.models.Relato;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by lucasmc on 10/12/14.
 */
public class DescRelatoActivity extends Activity {
    private String descricao;
    private String rel_id;
    private Double hash;

    private ArrayList<Comentario> comentarios;
    private Relato relato;
    private String author;
    private String status;
    private TextView descTextView;
    private TextView authorTextView;
    private TextView statusTextView;
    private ImageView imageView;
    private Button commentsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydescrelato);

        this.setTitle("Relato");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        descricao = "Descrição: " + bundle.getString("desc");
        author = "Autor: " + bundle.getString("author");
        status = "Status: ";

        Spinner spinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int spinnerPosition = adapter.getPosition(bundle.getString("status"));
        spinner.setSelection(spinnerPosition);
        boolean ehResponsavel = bundle.getString("author").equals(ParseUser.getCurrentUser().getUsername());
        spinner.setEnabled(ehResponsavel);
        spinner.setAdapter(adapter);

        rel_id = bundle.getString("rel_id");


        descTextView = (TextView) findViewById(R.id.descTextView);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        authorTextView = (TextView) findViewById(R.id.authorTextView);
        imageView = (ImageView) findViewById(R.id.imageView);
        commentsButton = (Button) findViewById(R.id.commentsButton);

        descTextView.setText(descricao);
        authorTextView.setText(author);
        statusTextView.setText(status);

        try {
            byte[] bytes = bundle.getByteArray("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {

        }

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(DescRelatoActivity.this, CommentListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("rel_id", rel_id);

                commentIntent.putExtras(bundle);
                startActivity(commentIntent);
            }
        });
    }
}