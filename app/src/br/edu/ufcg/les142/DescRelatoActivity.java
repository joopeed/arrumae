package br.edu.ufcg.les142;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import br.edu.ufcg.les142.models.Relato;
import br.edu.ufcg.les142.models.StatusRelato;
import br.edu.ufcg.les142.models.TipoRelato;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import org.json.JSONException;

import java.util.ArrayList;

import static br.edu.ufcg.les142.R.*;

/**
 * Created by lucasmc on 10/12/14.
 */
public class DescRelatoActivity extends Activity {
    private String descricao;
    private String rel_id;
    private Relato relato;
    private Spinner spinner_status;
    private Spinner spinner_tipo;
    private String author;
    private String status;
    private String tipo;
    private TextView descTextView;
    private TextView authorTextView;
    private TextView statusTextView;
    private TextView tipoTextView;
    private ImageView imageView;
    private Button commentsButton;
    private TextView apoiosTextView;
    private ArrayList<String> apoiosList;
    private String apoio;
    private Button apoiarButton;
    private Installation myInst;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        myInst = new Installation();
        super.onCreate(savedInstanceState);
        setContentView(layout.activitydescrelato);

        this.setTitle("Relato");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        rel_id = bundle.getString("rel_id");
        descricao = "Descrição: " + bundle.getString("desc");
        author = "Autor: " + bundle.getString("author");
        apoiosList = bundle.getStringArrayList("apoios");
        apoio = apoiosList.size()>0 ? apoiosList.size() + " apoiadores": null;
        status = "Estado: ";
        tipo = "Tipo: ";

        spinner_status = (Spinner) findViewById(id.status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int spinnerPosition = adapter.getPosition(bundle.getString("status"));
        boolean ehResponsavel = bundle.getString("author").equals(ParseUser.getCurrentUser().getUsername());
        spinner_status.setEnabled(ehResponsavel);
        spinner_status.setAdapter(adapter);
        spinner_status.setSelection(spinnerPosition, true);

        spinner_tipo = (Spinner) findViewById(id.tipo_spinner);
        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this,
                array.tipo_array, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int spinnerTipoPosition = adapter_tipo.getPosition(bundle.getString("tipo"));
        spinner_tipo.setEnabled(ehResponsavel);
        spinner_tipo.setAdapter(adapter_tipo);
        spinner_tipo.setSelection(spinnerTipoPosition, true);

        ParseQuery<Relato> query = Relato.getQuery();
        query.fromLocalDatastore();
        query.getInBackground(rel_id, new GetCallback<Relato>() {
            @Override
            public void done(Relato rel, ParseException e) {
                if (e == null) {
                    relato = rel;
                    spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                            String novo = (String) adapterView.getItemAtPosition(pos);
                            if (novo != null) {
                                StatusRelato novoStatus = StatusRelato.parse(novo);
                                relato.setStatusRelato(novoStatus);
                                relato.saveInBackground();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    spinner_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                            String novo = (String) adapterView.getItemAtPosition(pos);
                            if (novo != null) {
                                TipoRelato novoTipo = TipoRelato.parse(novo);
                                relato.setTipoRelato(novoTipo);
                                relato.saveInBackground();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
        });

        descTextView = (TextView) findViewById(id.descTextView);
        statusTextView = (TextView) findViewById(id.statusTextView);
        authorTextView = (TextView) findViewById(id.authorTextView);
        apoiosTextView = (TextView) findViewById(id.apoiadoresTextView);
        imageView = (ImageView) findViewById(id.descImageView);
        commentsButton = (Button) findViewById(id.commentsButton);
        apoiarButton = (Button) findViewById(id.apoiarButton);

        descTextView.setText(descricao);
        authorTextView.setText(author);
        statusTextView.setText(status);
        if(apoio != null)  apoiosTextView.setText(apoio);

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
        if(apoiosList.contains(ParseUser.getCurrentUser().getUsername())){
            apoiarButton.setText("Desfazer apoio");
        }else {
            apoiarButton.setText("Apoiar");
        }
        apoiarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<Relato> query = Relato.getQuery();
                query.fromLocalDatastore();
                query.getInBackground(rel_id, new GetCallback<Relato>() {
                    @Override
                    public void done(Relato rel, ParseException e) {
                        if (e == null) {
                            relato = rel;
                            try {
                                ParseUser user = ParseUser.getCurrentUser();
                                if(relato.isApoiador(user)){
                                    relato.removerApoio(user);
                                    rel.fetchIfNeeded();
                                    myInst.unsubscribe(rel.getObjectId());
                                }
                                else{
                                    relato.addApoio(user);
                                    rel.fetchIfNeeded();
                                    myInst.subscribe(rel.getObjectId());
                                }
                                relato.save();

                                apoiosList = new ArrayList<String>();
                                for (ParseUser usu : relato.getApoios()) {
                                    usu.fetchIfNeeded();
                                    apoiosList.add(usu.getUsername());
                                }
                                apoio = apoiosList.size() > 0 ? apoiosList.size() + " apoiadores" : "Nenhum apoiador";
                                if(apoiosList.size()>= 1) myInst.sendApoioPush(rel.getObjectId());
                                apoiosTextView.setText(apoio);
                                if(!relato.isApoiador(user)){
                                    apoiarButton.setText("Apoiar");
                                }
                                else{
                                    apoiarButton.setText("Desfazer apoio");
                                }

                            } catch (ParseException e1) {
                                Toast.makeText(DescRelatoActivity.this,
                                        "Não foi possível apoiar esse relato. Tente de novo", Toast.LENGTH_LONG).show();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }
}
