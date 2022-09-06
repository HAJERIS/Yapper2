package com.cometchat.pro.javasampleapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.javasampleapp.R;
import com.cometchat.pro.models.User;
import com.cometchat.pro.javasampleapp.AppConstants;
import com.cometchatworkspace.resources.utils.Utils;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView superhero1;

    private MaterialCardView superhero2;

    private MaterialCardView superhero3;

    private MaterialCardView superhero4;

    private AppCompatImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setStatusBarColor(this, getResources().getColor(android.R.color.white));
        AppSettings appSettings = new AppSettings.AppSettingsBuilder().
                subscribePresenceForAllUsers().setRegion(AppConstants.REGION).build();
        CometChat.init(this, AppConstants.APP_ID, appSettings,
                new CometChat.CallbackListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (CometChat.getLoggedInUser() != null) {
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }
                        CometChat.setSource("ui-kit", "android", "java");
                    }

                    @Override
                    public void onError(CometChatException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        superhero1 = findViewById(R.id.superhero1);
        superhero2 = findViewById(R.id.superhero2);
        superhero3 = findViewById(R.id.superhero3);
        superhero4 = findViewById(R.id.superhero4);
        ivLogo = findViewById(R.id.ivLogo);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        superhero1.setOnClickListener(view -> {
            findViewById(R.id.superhero1Progressbar).setVisibility(View.VISIBLE);
            login("superhero1");
        });
        superhero2.setOnClickListener(view -> {
            findViewById(R.id.superhero2Progressbar).setVisibility(View.VISIBLE);
            login("superhero2");
        });
        superhero3.setOnClickListener(view -> {
            findViewById(R.id.superhero3Progressbar).setVisibility(View.VISIBLE);
            login("superhero3");
        });
        superhero4.setOnClickListener(view -> {
            findViewById(R.id.superhero4Progressbar).setVisibility(View.VISIBLE);
            login("superhero4");
        });

        if (Utils.isDarkMode(this)) {
            ivLogo.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else {
            ivLogo.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryTextColor)));
        }
    }

    private void login(String uid) {
        CometChat.login(uid, AppConstants.AUTH_KEY, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onError(CometChatException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createUser(View view) {
        startActivity(new Intent(this,CreateUserActivity.class));
    }
}