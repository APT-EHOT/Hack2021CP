package com.shureck.medmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shureck.medmobile.ActionsManaging.MakeNewActionActivity;


public class SelectNewActionTypeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_action_type);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        TextView header;
        switch (v.getId()) {
            case R.id.misc_card:
                intent = new Intent(SelectNewActionTypeActivity.this, MakeNewActionActivity.class);
                startActivity(intent);
                break;
            case R.id.stairs_card:
                intent = new Intent(SelectNewActionTypeActivity.this, SetParametersForActionActivity.class);
                header = findViewById(R.id.stairs_name);
                intent.putExtra("HEADER", header.getText().toString());
                intent.putExtra("SUB_HEADER", "Укажите этажи");
                startActivity(intent);
                break;
            case R.id.walk_card:
                intent = new Intent(SelectNewActionTypeActivity.this, SetParametersForActionActivity.class);
                header = findViewById(R.id.walk_name);
                String s = header.getText().toString();
                intent.putExtra("HEADER", header.getText().toString());
                intent.putExtra("SUB_HEADER", "Укажите минуты");
                startActivity(intent);
                break;
            case R.id.run_card:
                intent = new Intent(SelectNewActionTypeActivity.this, SetParametersForActionActivity.class);
                header = findViewById(R.id.run_name);
                intent.putExtra("HEADER", header.getText().toString());
                intent.putExtra("SUB_HEADER", "Укажите минуты");
                startActivity(intent);
                break;
        }
    }
}