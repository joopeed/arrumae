package br.edu.ufcg.les142.models;

import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rodrigo on 23/11/2014.
 */
public class Relato {
    private String id;
    private Usuario criador;
//    private Localizacao local;
    private Date data;
    private ArrayList<ParseFile> album;

    /*
         * Constructor for Users in the System.
         */
    public Relato(String id, Usuario criador, Date data) {
    //*Localizacao local*//,
        this.id = id;
        this.criador = criador;
        //this.local = local;
        this.data = data;
        this.album = new ArrayList<ParseFile>();
    }

    public void save(){
        ParseObject relato = new ParseObject("relato");
        relato.put("id", id);
        relato.put("criador", criador.toString());
        //relato.put("local", local.toString());
        relato.put("data", data.toString());
        relato.put("album", album);
        relato.saveInBackground();
    }


    /**
     * Assyncronous method. The list returned only will filled after the job in background.
     * @param criador
     * @return List of Relatos
     */
    public static ArrayList<Relato> getAllFrom(Usuario criador){
        ArrayList<Relato> relatos = new ArrayList<Relato>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("relato");
        query.whereEqualTo("criador", criador.toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    for(ParseObject obj: scoreList){
                       relatos.add(new Relato(obj.get("id"), obj.get("criador"), obj.get("data")))
                    }
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

   /** public Localizacao getLocal() {
        return local;
    }

    public void setLocal(Localizacao local) {
        this.local = local;
    }*/

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ArrayList<File> getAlbum() {
        return album;
    }

    public void setAlbum(ArrayList<File> album) {
        this.album = album;
    }
}
