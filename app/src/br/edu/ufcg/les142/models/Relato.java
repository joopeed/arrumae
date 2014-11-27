package br.edu.ufcg.les142.models;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rodrigo on 23/11/2014.
 */
public class Relato extends ParseObject {
    private String id;
    private Usuario criador;
    private String descricao;
    private ParseGeoPoint  localizacao;
    private Date data;
    private ArrayList<File> album;

    /*
         * Constructor for Users in the System.
         */
    public Relato(String id, Usuario criador, Date data) {
    //*Localizacao local*//,
        this.id = id;
        this.criador = criador;
        //this.local = local;
        this.data = data;
    }
    public Relato(){

    }
    public ParseGeoPoint getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(ParseGeoPoint localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
