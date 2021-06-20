package com.shureck.medmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shureck.medmobile.InputData.CheckInputActivity;

import java.io.File;

public class RootActivity extends AppCompatActivity implements View.OnClickListener {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        // startService(new Intent(this, SocketService.class));

        WorkWithToken workWithToken = new WorkWithToken(RootActivity.this);
        token = workWithToken.readToken();
        if(token == null || token.equals("")) {
            Intent intent = new Intent(RootActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {

        }

        startService(new Intent(this, SocketService.class));

        CardView photoCard = findViewById(R.id.photo_card);
        photoCard.setOnClickListener(v -> {
            Intent intentTest = new Intent(RootActivity.this, MainActivity.class);
            startActivity(intentTest);
        });
        CardView registerDeviceCard = findViewById(R.id.register_device_card);

        registerDeviceCard.setOnClickListener(v -> {
            Intent intentTest = new Intent(RootActivity.this, RegisterDeviceActivity.class);
            startActivity(intentTest);
        });

        CardView manuallyCard = findViewById(R.id.manually_card);

        manuallyCard.setOnClickListener(v -> {
            Intent intentTest = new Intent(RootActivity.this, CheckInputActivity.class);
            startActivity(intentTest);
        });


        CardView diaryCard = findViewById(R.id.diary_card);
        diaryCard.setOnClickListener(v -> {
            Intent intentTest = new Intent(RootActivity.this, DiaryActivity.class);
            startActivity(intentTest);
        });

        CardView notifyCard = findViewById(R.id.notify_card);
        notifyCard.setOnClickListener(v -> {
            Intent intentTest = new Intent(RootActivity.this, DoctorsNotifyActivity.class);
            startActivity(intentTest);
        });
//        Intent intentTest = new Intent(RootActivity.this, RegisterDeviceActivity.class);
//        startActivity(intentTest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_card:
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
    }
}