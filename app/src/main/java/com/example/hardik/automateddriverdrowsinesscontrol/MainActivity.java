package com.example.hardik.automateddriverdrowsinesscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout frame;
    Button agree, disagree;
    EditText emailEditText;

    static class EmailData {
        String recipient;
        String subject;
        String body;

        EmailData(String recipient, String subject, String body) {
            this.recipient = recipient;
            this.subject = subject;
            this.body = body;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SafeSteerIO");
        frame = (FrameLayout) findViewById(R.id.frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MonitorMenu()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        boolean isFirstTime = MyPreferences.isFirst(MainActivity.this);
        if (isFirstTime == true) {
            Intent help = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(help);
        }

        EmailData emailData = new EmailData(
                "riteshmahale15@gmail.com",
                "Drowsiness Alert",
                "Alert message here..."
        );

        sendEmail(emailData);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void sendEmail(EmailData emailData) {
        Gson gson = new Gson();
        String json = gson.toJson(emailData);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, mediaType);

        Request request = new Request.Builder()
                .url("https://sendmail.pythonanywhere.com/send_mail")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle success
                    // The email was sent successfully
                } else {
                    // Handle failure
                    // There was an error in sending the email
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                // There was a network failure or an error in making the request
            }
        });
    }

}