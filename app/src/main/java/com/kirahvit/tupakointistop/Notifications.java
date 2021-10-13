package com.kirahvit.tupakointistop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Luokka sisältää tarvittavat metodit push-notifikaatioiden rakentamiseen ja lähettämiseen
 * @author Rasmus Nygård
 */

public class Notifications {

    public static Notifications notifications_Instance;
    NotificationManager notificationManager;
    /**
     * Palauttaa viittauksen singleton instanssiin
     * @return viittaus Singleton instanssiin notifications_Instance
     */
    public static Notifications getNotifications() {

        if (notifications_Instance == null) {
            notifications_Instance = new Notifications();
        }
        return notifications_Instance;
    }

    /**
     * metodi lähettää push-notifikaation annettujen parametrien mukaisesti
     * @param context kutsuvan aktiviteetin konteksti
     * @param otsikko ilmoituksen otsikko
     * @param ilmoitusteksti ilmoituksen teksti
     */

    public void sendNotification(Context context, String otsikko, String ilmoitusteksti){
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notificationchannel))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(otsikko)
                .setContentText(ilmoitusteksti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

    /**
     * rakentaa uuden ilmoituskanavan, mikäli sellaista ei ole olemassa
     * (vaadittu Oreo tai uudemmille API -versioille)
     * @param context kutsuvan metodin antama konteksti
     */

    private void createNotificationChannel(Context context){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TupakointiStop_Notifications";
            String description = "TupakointiStop Ilmoitukset";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(context.getString(R.string.notificationchannel), name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
