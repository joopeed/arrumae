package br.edu.ufcg.les142.models;

import android.graphics.Bitmap;
import com.parse.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 23/11/2014.
 */

/**
 * Data model for a relato.
 */
@ParseClassName("Relato")
public class Relato extends ParseObject{


    public void setImage(Bitmap value) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        value.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        // get byte array here
        byte[] bytearray = stream.toByteArray();
        if (bytearray != null) {
            //TODO
            ParseFile file = new ParseFile("teste".toString() + ".jpg", bytearray);
            try {
                file.save();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            put("image", file);
        }
    }


    public byte[] getImage() {
        byte[] data = null;
        ParseFile fileObject = getParseFile("image");
        try {
             data = fileObject.getData();
        } catch (Exception e) {
            e.printStackTrace();
        } /*
        fileObject.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if (e == null) {
                    Log.d("test", "We've got data in data.");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    iv.setImageBitmap(bitmap);
                } else {
                    Log.d("test", "There was a problem downloading the data.");
                }
            }
        });*/
        return data;
    }

    public String getDescricao() {
        return getString("descricao");
    }

    public void setDescricao(String value) {
        put("descricao", value);
    }

    public StatusRelato getStatusRelato() {
        return StatusRelato.parse(getString("status"));
    }

    public void setStatusRelato(StatusRelato status) {
        put("status", status.toString());
    }

    public TipoRelato getTipoRelato() {
        return TipoRelato.parse(getString("tipo"));
    }

    public void setTipoRelato(TipoRelato tipo) {
        put("tipo", tipo.toString());
    }

    public List<String> getIDComentarios() {
        return getList("comentarios");
    }

    public void setComentarios(List<String> value) {
        put("comentarios", value);
    }

    public void addComentario(String ID){
        List<String> comentarios = getIDComentarios();
        if(comentarios == null){
            setComentarios(new ArrayList<String>());
        }
        comentarios.add(ID);

    }

    public List<ParseUser> getApoios() {
        if (getList("apoios") == null) return new ArrayList<ParseUser>();
        else return getList("apoios");
    }

    public void setApoios(List<ParseUser> value) {
        put("apoios", value);
    }

    public boolean isApoiador(ParseUser apoiador){
       for(ParseUser user: getApoios()){
           if(apoiador.equals(user)) return true;
       }
        return false;
    }

    public void addApoio(ParseUser apoiador){
        List<ParseUser> apoios = getApoios();
        if(!isApoiador(apoiador)) {
            apoios.add(apoiador);
            setApoios(apoios);
        }

    }
    public void removerApoio(ParseUser apoiador){
        List<ParseUser> apoios = getApoios();
        apoios.remove(apoiador);
        setApoios(apoios);

    }

    public void setLocalizacao(ParseGeoPoint value) {

        put("location", value);
    }


    public ParseGeoPoint getLocalizacao() {
        return getParseGeoPoint("location");
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser value) {
        if (value != null) {
            put("user", value);
        }
    }

    public static ParseQuery<Relato> getQuery() {
        return ParseQuery.getQuery(Relato.class);
    }

}