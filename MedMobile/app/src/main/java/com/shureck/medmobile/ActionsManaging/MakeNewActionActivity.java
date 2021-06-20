package com.shureck.medmobile.ActionsManaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.shureck.medmobile.InputData.CheckInputActivity;
import com.shureck.medmobile.R;
import com.shureck.medmobile.RootActivity;
import com.shureck.medmobile.SelectNewActionTypeActivity;

public class MakeNewActionActivity extends AppCompatActivity {

    Button getBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_action);

        getBackButton = findViewById(R.id.button_back);

        getBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(MakeNewActionActivity.this, SelectNewActionTypeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}