package com.shureck.medmobile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.shureck.medmobile.Models.DoctorMessage;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SocketService extends Service {

    private WifiManager mWifiManager;
    private AsyncHttpClient.WebSocketConnectCallback mWebSocketConnectCallback;
    private AsyncHttpClient mAsyncHttpClient;
    String strr;
    ConstraintLayout constraintLayout;
    NotificationManager notificationManager;

    Timer myTimer = new Timer(); // Создаем таймер
    final Handler uiHandler = new Handler();
    Context context = this;

    LinearLayout notify_container;
    private String token;

    private final OkHttpClient client = new OkHttpClient();
    int notificationId = 0;

    public void onCreate() {
        super.onCreate();
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WorkWithToken workWithToken = new WorkWithToken(SocketService.this);
        token = workWithToken.readToken();

        myTimer.schedule(new TimerTask() { // Определяем задачу
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {

//                        System.out.println("!!!!!!!!!!!!!!!:::::");

//                        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//                        Intent intent = new Intent(getApplicationContext(), RootActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                        NotificationCompat.Builder notificationBuilder =
//                                new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
//                                        .setAutoCancel(false)
//                                        .setSmallIcon(R.drawable.ic_launcher_background)
//                                        .setWhen(System.currentTimeMillis())
//                                        .setContentIntent(pendingIntent)
//                                        .setContentTitle("Напоминание")
//                                        .setContentText("Измерить давление")
//                                        .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//                        createChannelIfNeeded(notificationManager);
//                        notificationManager.notify(notificationId, notificationBuilder.build());
//                        notificationId++;
                        new IOAsyncTask().execute("http://10.18.0.3:8080/patient/actualDoctorMessage");
                    }
                });
            }
        }, 0L, 5L * 1000);

    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", "CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOG_TAG", "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("LOG_TAG", "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d("LOG_TAG", "onBind");
        return null;
    }

    void someTask() {
    }

    public void setData(){

    }

    class IOAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return sendData(params[0]);
        }

        @Override
        protected void onPostExecute(String response) {
            strr = response;
            System.out.println("AAAA "+strr);
            Gson gson = new Gson();
            try {
                List<DoctorMessage> previews = stringToArray(strr, DoctorMessage[].class);
                System.out.println("DDD "+previews.get(0).getDoctorName());

                for(int i=0;i<previews.size();i++){

                    notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent intent = new Intent(getApplicationContext(), RootActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
                                    .setAutoCancel(false)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentIntent(pendingIntent)
                                    .setContentTitle(previews.get(i).getDoctorName())
                                    .setContentText(previews.get(i).getText())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH);

                    createChannelIfNeeded(notificationManager);
                    notificationManager.notify(notificationId, notificationBuilder.build());
                    notificationId++;
                }

            }catch (Exception e){

            }


        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public String sendData(String str) {
        try {

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            Request request = new Request.Builder()
                    .url(str)
                    .header("Authorization","Bearer "+token)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
