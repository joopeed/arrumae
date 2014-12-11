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
    private TextView descTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydescrelato);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        descricao = bundle.getString("desc");

        descTextView = (TextView) findViewById(R.id.descTextView);

        descTextView.setText(descricao);
    }
}