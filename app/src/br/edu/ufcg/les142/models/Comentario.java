package br.edu.ufcg.les142.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.parse.*;

import java.io.ByteArrayOutputStream;

/**
 * Created by lucasmc on 26/01/15.
 */
@ParseClassName("Comentario")
public class Comentario extends ParseObject{


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
        }
        return data;
    }

    public String getText() {
        try {
            return fetchIfNeeded().getString("text");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "erro";
    }

    public void setText(String value) {
        put("text", value);
    }

    public static ParseQuery<Comentario> getQuery() {
        return ParseQuery.getQuery(Comentario.class);
    }





}
