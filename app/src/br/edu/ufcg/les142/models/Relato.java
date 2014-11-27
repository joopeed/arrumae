package br.edu.ufcg.les142.models;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rodrigo on 23/11/2014.
 */
/**
 * Data model for a post.
 */
@ParseClassName("Relatos")
public class Relato extends ParseObject {

    public String getDescricao() {
        return getString("descricao");
    }

    public void setDescricao(String value) {
        put("descricao", value);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser value) {
        put("user", value);
    }

    public ParseGeoPoint getLocalizacao() {
        return getParseGeoPoint("location");
    }

    public void setLocalizacao(ParseGeoPoint value) {
        put("location", value);
    }

    public static ParseQuery<Relato> getQuery() {
        return ParseQuery.getQuery(Relato.class);
    }

}