package br.edu.ufcg.les142;

/**
 * Created by lucasmc on 10/02/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static br.edu.ufcg.les142.R.*;

public class LazyAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<String > comentarios;
    private final List<Bitmap> bitmaps;

    public LazyAdapter(Activity context,
                       List<String > comentarios, List<Bitmap> bitmaps) {
        super(context, layout.list_row, comentarios);
        this.context = context;
        this.comentarios = comentarios;
        this.bitmaps = bitmaps;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(layout.list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(id.title);
        ImageView imageView = (ImageView) rowView.findViewById(id.list_image);
        txtTitle.setText(comentarios.get(position));
        try{ imageView.setImageBitmap(bitmaps.get(position));}
        catch (Exception e){}
        return rowView;
    }
}