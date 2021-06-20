package com.shureck.medmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shureck.medmobile.Models.DoctorMessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DoctorsNotifyActivity extends AppCompatActivity {

    String strr;
    LinearLayout notify_container;
    private final OkHttpClient client = new OkHttpClient();
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_notify);

        WorkWithToken workWithToken = new WorkWithToken(DoctorsNotifyActivity.this);
        token = workWithToken.readToken();

        new IOAsyncTask().execute("http://10.18.0.3:8080/patient/allDoctorMessage");
    }


    public void setData(List<DoctorMessage> messages){
        notify_container = findViewById(R.id.notify_container);
        LayoutInflater inflater = LayoutInflater.from(notify_container.getContext());

        for (int i=0; i<messages.size(); i++) {
            View newMessageView = inflater.inflate(R.layout.doctor_post, null);

            TextView doctor_name = newMessageView.findViewById(R.id.doctor_name);
            TextView doctor_name2 = newMessageView.findViewById(R.id.doctor_name2);
            TextView time = newMessageView.findViewById(R.id.time);
            TextView message_text = newMessageView.findViewById(R.id.message_text);

            SimpleDateFormat sddd = new SimpleDateFormat("d MMMM, HH:mm");

            doctor_name.setText(messages.get(i).getDoctorName());
            doctor_name2.setText(messages.get(i).getPhone());
            time.setText(sddd.format(messages.get(i).getDate()));
            message_text.setText(messages.get(i).getText());

            notify_container.addView(newMessageView);
        }
    }

    class IOAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return sendData(params[0]);
        }

        @Override
        protected void onPostExecute(String response) {
            strr = response;
            System.out.println(strr);
            Gson gson = new Gson();
            List<DoctorMessage> previews = stringToArray(strr, DoctorMessage[].class);
            System.out.println("DDD "+previews.get(0).getDoctorName());
            setData(previews);
        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public String sendData(String str){
        try {
//            RequestBody formBody = new FormBody.Builder()
//                    .add("LatLong", str)
//                    .build();

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