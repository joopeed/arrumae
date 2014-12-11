package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lucasmc on 10/12/14.
 */
public class DescRelatoActivity extends Activity {
    private String descricao;
    private String author;
    private TextView descTextView;
    private TextView authorTextView;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydescrelato);

        this.setTitle("Relato");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        descricao = "Descrição: " + bundle.getString("desc");
        author = "Autor: " + bundle.getString("author");




        descTextView = (TextView) findViewById(R.id.descTextView);
        authorTextView = (TextView) findViewById(R.id.authorTextView);
        imageView = (ImageView) findViewById(R.id.imageView);

        descTextView.setText(descricao);
        authorTextView.setText(author);

        try {
            byte[] bytes = bundle.getByteArray("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }
}