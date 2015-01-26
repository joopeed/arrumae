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
public class Comentario extends ParseObject implements Parcelable {

    public Comentario(){
    }

    public Comentario(Parcel in){
        readFromParcel(in);
    }

//    public void setImage(Bitmap value) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        value.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        // get byte array here
//        byte[] bytearray = stream.toByteArray();
//        if (bytearray != null) {
//            //TODO
//            ParseFile file = new ParseFile("teste".toString() + ".jpg", bytearray);
//            try {
//                file.save();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            put("image", file);
//        }
//    }
//
//    public byte[] getImage() {
//        byte[] data = null;
//        ParseFile fileObject = getParseFile("image");
//        try {
//            data = fileObject.getData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return data;
//    }

    public String getText() {
        return getString("text");
    }

    public void setText(String value) {
        put("text", value);
    }

    public static ParseQuery<Comentario> getQuery() {
        return ParseQuery.getQuery(Comentario.class);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getText());
    }

    private void readFromParcel(Parcel in) {
        setText(in.readString());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };


}
