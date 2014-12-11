package br.edu.ufcg.les142.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.parse.*;

import java.io.ByteArrayOutputStream;

/**
 * Created by Rodrigo on 23/11/2014.
 */

/**
 * Data model for a relato.
 */
@ParseClassName("Relato")
public class Relato extends ParseObject {

    public String getDescricao() {
        return getString("descricao");
    }

    public void setDescricao(String value) {
        put("descricao", value);
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

    public void getImage(final ImageView iv) {
        ParseFile fileObject = getParseFile("image");
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
        });
    }

    public void setLocalizacao(ParseGeoPoint value) {
        put("location", value);
    }

    public static ParseQuery<Relato> getQuery() {
        return ParseQuery.getQuery(Relato.class);
    }
}