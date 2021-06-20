package com.shureck.medmobile.ActionsManaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shureck.medmobile.R;
import com.shureck.medmobile.SelectNewActionTypeActivity;

import java.util.Arrays;
import java.util.List;

public class AddActionsActivity extends AppCompatActivity {

    List<Integer> defaultIcons;
    List<String> actionNames;
    List<String> actionDescriptions;

    LinearLayout actionsContainer;

    CardView addAction;
    CardView removeAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actions);

        actionsContainer = findViewById(R.id.actions_container);
        addAction = findViewById(R.id.add_action_card);
        removeAction = findViewById(R.id.remove_action_card);

        setActionsInfo();
        setActionsListContent();

        addAction.setOnClickListener(v -> {
            Intent intent = new Intent(AddActionsActivity.this, SelectNewActionTypeActivity.class);
            startActivity(intent);
        });

        removeAction.setOnClickListener(v -> {
            // TODO: add action removing
        });
    }

    private void setActionsInfo() {
        // TODO: change to query for shared preferences
        defaultIcons =
                Arrays.asList(
                        R.drawable.ic_baseline_airline_seat_recline_normal_24,
                        R.drawable.ic_baseline_stairs_24,
                        R.drawable.ic_baseline_directions_walk_24,
                        R.drawable.ic_baseline_directions_run_24,
                        R.drawable.ic_baseline_bolt_24
                );
        actionNames =
                Arrays.asList(
                        "Состояние покоя",
                        "Подъём по лестнице",
                        "Ходьба",
                        "Бег",
                        "Стресс"
                );
        actionDescriptions =
                Arrays.asList(
                        "Не было активности",
                        "5 этажей",
                        "10 минут",
                        "3 минуты",
                        "Вы нервничали"
                );
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setActionsListContent() {
        LayoutInflater inflaterPinned = LayoutInflater.from(actionsContainer.getContext());


        for (int i = 0; i < actionNames.size(); i++) {
            View newAction = inflaterPinned.inflate(R.layout.action_button_item, null);

            ImageView newActionIcon = newAction.findViewById(R.id.action_icon);
            TextView newActionName = newAction.findViewById(R.id.action_name);
            TextView newActionDesc = newAction.findViewById(R.id.action_desc);
            newActionIcon.setImageResource(defaultIcons.get(i));
            newActionName.setText(actionNames.get(i));
            newActionDesc.setText(actionDescriptions.get(i));
            actionsContainer.addView(newAction);
        }

    }
}