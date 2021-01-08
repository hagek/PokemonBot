package com.github.hagek;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APICaller {
    public static final Gson GSON = new Gson();

    public static JsonObject call(URL url) throws IOException, IllegalStateException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("user-agent", "");
        connection.setRequestProperty("content-type", "application/json");
        connection.setDoInput(true);
        StringBuilder result;
        if (connection.getResponseCode() == 200) {
            result = new StringBuilder();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            isr.close();
            is.close();
        } else {
            throw new IllegalStateException("The HTTP response code the server returned was other than 200");
        }
        return GSON.fromJson(result.toString(), new TypeToken<JsonObject>() {}.getType());
    }
}
