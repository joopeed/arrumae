package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import br.edu.ufcg.les142.R;

/**
 * Created by lucasmc on 10/12/14.
 */
public class DescRelatoActivity extends Activity {
    private String descricao;
    private String author;
    private TextView descTextView;
    private TextView authorTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydescrelato);

        this.setTitle("Relato");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        descricao = bundle.getString("desc");
        author = bundle.getString("author");

        descTextView = (TextView) findViewById(R.id.descTextView);
        authorTextView = (TextView) findViewById(R.id.authorTextView);

        descTextView.setText(descricao);
    }
}