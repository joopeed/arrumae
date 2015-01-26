package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.edu.ufcg.les142.models.Comentario;

import java.util.ArrayList;

/**
 * Created by lucasmc on 10/12/14.
 */
public class DescRelatoActivity extends Activity {
    private String descricao;
    private ArrayList<Comentario> comentarios;
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
        status = "Status: " + bundle.getString("status");
        comentarios = bundle.getParcelableArrayList("comentarios");

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
                bundle.putParcelableArrayList("comentarios", comentarios);

                commentIntent.putExtras(bundle);
                startActivity(commentIntent);
            }
        });
    }
}