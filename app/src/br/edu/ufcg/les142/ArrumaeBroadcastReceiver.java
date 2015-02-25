package br.edu.ufcg.les142;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import br.edu.ufcg.les142.InitialActivity;
import br.edu.ufcg.les142.DescRelatoActivity;
import com.parse.ParsePush;
import br.edu.ufcg.les142.models.Relato;
import com.parse.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 24/02/2015.
 */
public class ArrumaeBroadcastReceiver extends BroadcastReceiver {
    NotificationCompat.Builder mBuilder;
    Intent resultIntent;
    int mNotificationId = 001;
    Uri notifySound;

    String alert; // This is the message string that send from push console
    String channel;

    @Override
    public void onReceive(final Context context, Intent intent) {

//Get JSON data and put them into variables
        try {

            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            alert = json.getString("alert").toString();
            channel = json.getString("relato").toString();

        } catch (JSONException e) {

        }

//You can specify sound
        notifySound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.notify_icone_arrumae); //You can change your icon
        mBuilder.setContentText(alert);
        mBuilder.setContentTitle("ArrumAê");
        mBuilder.setSound(notifySound);
        mBuilder.setAutoCancel(true);

// this is the activity that we will send the user, change this to anything you want
        ParseQuery<Relato> query = Relato.getQuery();
        query.getInBackground(channel, new GetCallback<Relato>() {
            @Override
            public void done(Relato relato, ParseException e) {
                if (e == null) {
                    relato.pinInBackground();
                    Intent intent = new Intent(context, DescRelatoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("desc", relato.getDescricao());
                    ArrayList<String> apoios = new ArrayList<String>();
                    for (ParseUser user : relato.getApoios()) {
                        try {
                            user.fetchIfNeeded();
                            apoios.add(user.getUsername());
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                    }
                    bundle.putStringArrayList("apoios", apoios);
                    bundle.putString("rel_id", channel);

                    if (relato.getStatusRelato() != null) {
                        bundle.putString("status", relato.getStatusRelato().toString());
                    } else {
                        bundle.putString("status", "");
                    }

                    try {
                        ParseUser parseUser = relato.getUser().fetchIfNeeded();
                        bundle.putString("author", parseUser.getUsername());
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    if (relato.getImage() != null) {
                        bundle.putByteArray("image", relato.getImage());

                    }
                    intent.putExtras(bundle);
                                    }
            }
        });
        resultIntent = intent;

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(mNotificationId, mBuilder.build());

    }

}

