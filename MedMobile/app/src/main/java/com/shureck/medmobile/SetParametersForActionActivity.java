package com.shureck.medmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvContentRating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shureck.medmobile.ActionsManaging.MakeNewActionActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetParametersForActionActivity extends AppCompatActivity {

    TextView headerView;
    TextView subHeaderView;
    Button getBackButton;
    Button button_save;
    private String token;
    EditText pulse_val;
    String strr;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_parameters_for_action);

        WorkWithToken workWithToken = new WorkWithToken(SetParametersForActionActivity.this);
        token = workWithToken.readToken();

        String header = getIntent().getStringExtra("HEADER");
        String subHeader = '(' + getIntent().getStringExtra("SUB_HEADER") + ')';

        headerView = findViewById(R.id.set_params_action_header);
        subHeaderView = findViewById(R.id.set_params_action_sub_header);

        headerView.setText(header);
        subHeaderView.setText(subHeader);

        pulse_val = findViewById(R.id.pulse_val);

        getBackButton = findViewById(R.id.button_back);
        button_save = findViewById(R.id.button_save);

        getBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(SetParametersForActionActivity.this, SelectNewActionTypeActivity.class);
            startActivity(intent);
            finish();
        });

        button_save.setOnClickListener(v -> {

            new IOAsyncTask().execute("http://10.18.0.3:8080/patient/addActivityType?activityType="+headerView.getText()+" "+pulse_val.getText());

            Intent intent = new Intent(SetParametersForActionActivity.this, RootActivity.class);
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
            strr = response;
            System.out.println(strr);
            Gson gson = new Gson();
//            List<DoctorMessage> previews = stringToArray(strr, DoctorMessage[].class);
//            System.out.println("DDD "+previews.get(0).getDoctorName());
        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public String sendData(String str){
        try {
//            RequestBody formBody = new FormBody.Builder()
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