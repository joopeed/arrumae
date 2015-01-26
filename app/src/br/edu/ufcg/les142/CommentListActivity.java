package br.edu.ufcg.les142;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasmc on 26/01/15.
 */
public class CommentListActivity extends Activity {

    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycomments);
        listView = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();

        List<String> comments = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        listView.setAdapter(adapter);
    }

}
