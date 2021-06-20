package com.shureck.medmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shureck.medmobile.Models.DiaryModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiaryActivity extends AppCompatActivity {

    String strr;
    LinearLayout diary_container;
    private final OkHttpClient client = new OkHttpClient();
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        WorkWithToken workWithToken = new WorkWithToken(DiaryActivity.this);
        token = workWithToken.readToken();

        new IOAsyncTask().execute("http://10.18.0.3:8080/patient/allPressure");
    }

    public void setData(List<DiaryModel> previews){
        diary_container = findViewById(R.id.diary_container);
        LayoutInflater inflater = LayoutInflater.from(diary_container.getContext());

        for (int i=0; i<previews.size(); i++) {
            View newMessageView = inflater.inflate(R.layout.diary_post, null);

            TextView date = newMessageView.findViewById(R.id.date);
            TextView time = newMessageView.findViewById(R.id.time);
            TextView action = newMessageView.findViewById(R.id.action);
            TextView pressure = newMessageView.findViewById(R.id.pressure);
            TextView pulse = newMessageView.findViewById(R.id.pulse);

            SimpleDateFormat sddd = new SimpleDateFormat("dd.MM");
            SimpleDateFormat sdd = new SimpleDateFormat("HH:mm");
            pressure.setText("А/Д: "+previews.get(i).getTop()+"/"+previews.get(i).getBottom());
            pulse.setText("Пульс: "+previews.get(i).getPulse());
            action.setText(previews.get(i).getActivityType());
            date.setText(sddd.format(previews.get(i).getId()));
            time.setText(sdd.format(previews.get(i).getId()));

            diary_container.addView(newMessageView);
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
            Gson gson = new Gson();
            List<DiaryModel> previews = stringToArray(strr, DiaryModel[].class);
            System.out.println("DDD "+previews.get(0).getId());
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