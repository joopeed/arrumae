package br.edu.ufcg.les142;

import android.app.Notification;
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
import java.util.Iterator;

/**
 * Created by Rodrigo on 24/02/2015.
 */
public class ArrumaeBroadcastReceiver extends BroadcastReceiver {
    NotificationCompat.Builder mBuilder;
    Intent resultIntent;
    int mNotificationId = 001;
    Uri notifySound;
    public static final String ACTION="br.edu.ufcg.les142.CUSTOM_NOTIFICATION";
    public static final String PARSE_EXTRA_DATA_KEY="com.parse.Data";
    public static final String PARSE_JSON_ALERT_KEY="alert";
    public static final String PARSE_JSON_CHANNELS_KEY="com.parse.Channel";
    private static final String TAG = "ArrumAêBroadcastReceiver";
    String alert; // This is the message string that send from push console
    String channel;
    Intent mIntent;

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            String action = intent.getAction();

            //"com.parse.Channel"
            String channel =
                    intent.getExtras()
                            .getString(PARSE_JSON_CHANNELS_KEY);

            JSONObject json =
                    new JSONObject(
                            intent.getExtras()
                                    .getString(PARSE_EXTRA_DATA_KEY));

            Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
            Iterator itr = json.keys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                Log.d(TAG, "..." + key + " => " + json.getString(key));
            }
            notify(context, intent, json);
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void notify(final Context ctx, Intent i, JSONObject dataObject)
            throws JSONException
    {
        notifySound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        alert = dataObject.getString("alet");
        mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.drawable.icone_arrumae); //You can change your icon
        mBuilder.setContentText(alert);
        mBuilder.setContentTitle("ArrumAê");
        mBuilder.setSound(notifySound);
        mBuilder.setAutoCancel(true);
        channel = dataObject.getString("relato");

        //Let the intent invoke the respond activity
        mIntent = new Intent(ctx, DescRelatoActivity.class);
        ParseQuery<Relato> query = Relato.getQuery();
        query.getInBackground(channel, new GetCallback<Relato>() {
            @Override
            public void done(Relato relato, ParseException e) {
                Log.d(TAG, relato.getDescricao());
                if (e == null) {
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
                    bundle.putString("rel_id", relato.getObjectId());

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
                    Log.d(TAG, String.valueOf(bundle.size()));
                    Log.d(TAG, String.valueOf(bundle.get("rel_id")));
                    mIntent.putExtras(bundle);
                    PendingIntent resultPendingIntent = PendingIntent.getActivity(ctx,
                            0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    mBuilder.setContentIntent(resultPendingIntent);

                    NotificationManager notificationManager = (NotificationManager) ctx
                            .getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(mNotificationId, mBuilder.build());
                }
            }
        });





    }
}

