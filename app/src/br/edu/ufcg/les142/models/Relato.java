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
    private ArrayList<File> album;

    /*
         * Constructor for Users in the System.
         */
    public Relato(String id,Usuario criador, Date data) { //*Localizacao local*//,
        this.id = id;
        this.criador = criador;
        //this.local = local;
        this.data = data;
    }

    public void save(){
        ParseObject relato = new ParseObject("relato");
        relato.put("criador", criador.toString());
        //relato.put("local", local.toString());
        relato.put("data", data.toString());
        //relato.saveInBackground();
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
