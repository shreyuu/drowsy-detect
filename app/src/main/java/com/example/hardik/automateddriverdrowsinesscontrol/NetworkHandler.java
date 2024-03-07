package com.example.hardik.automateddriverdrowsinesscontrol;

import android.util.Log;

import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class NetworkHandler {

    private static final String BASE_URL = "http://your_flask_server_ip:5000"; // Change this to your Flask server's IP address

    private OkHttpClient client = new OkHttpClient();

    private void sendEmail(String recipient, String subject, String body) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("recipient", recipient);
            jsonObject.put("subject", subject);
            jsonObject.put("body", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(BASE_URL + "/send_email")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle success
                String responseData = response.body().string();
                Log.d("Response", responseData);
            }
        });
    }

}

