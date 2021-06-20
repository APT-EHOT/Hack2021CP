package com.shureck.medmobile.InputData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.shureck.medmobile.ActionsManaging.AddActionsActivity;
import com.shureck.medmobile.DoctorsNotifyActivity;
import com.shureck.medmobile.Models.DoctorMessage;
import com.shureck.medmobile.R;
import com.shureck.medmobile.RootActivity;
import com.shureck.medmobile.SelectNewActionTypeActivity;
import com.shureck.medmobile.WorkWithToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckInputActivity extends AppCompatActivity {

    Button sendButton;
    Button backButton;

    private final OkHttpClient client = new OkHttpClient();
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_input);

        WorkWithToken workWithToken = new WorkWithToken(CheckInputActivity.this);
        token = workWithToken.readToken();

        sendButton = findViewById(R.id.button_send);
        backButton = findViewById(R.id.button_back);

        EditText top_pres_val = findViewById(R.id.top_pres_val);
        EditText low_pres_val = findViewById(R.id.low_pres_val);
        EditText pulse_val = findViewById(R.id.pulse_val);

        Intent forseIntent = getIntent();
        int top = forseIntent.getIntExtra("top",0);
        int bottom = forseIntent.getIntExtra("bottom",0);
        int pulse = forseIntent.getIntExtra("pulse",0);
        top_pres_val.setText(String.valueOf(top));
        low_pres_val.setText(String.valueOf(bottom));
        pulse_val.setText(String.valueOf(pulse));

        sendButton.setOnClickListener(v -> {

            new IOAsyncTask().execute("http://10.18.0.3:8080/patient/addPressure?top=" + top_pres_val.getText() +
                    "&bottom="+low_pres_val.getText()+"&pulse="+pulse_val.getText());

            Intent intent = new Intent(CheckInputActivity.this, SelectNewActionTypeActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CheckInputActivity.this, RootActivity.class);
            startActivity(intent);
            finish();
        });
    }

    class IOAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return sendData(params[0]);
        }

        @Override
        protected void onPostExecute(String response) {
        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public String sendData(String str){
        try {

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