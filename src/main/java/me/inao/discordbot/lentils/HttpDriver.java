package me.inao.discordbot.lentils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.inao.dbbp.processing.annotations.Stateless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Stateless
public class HttpDriver {
    public String postRequestWithResponse(String url, Map<String, Object> args) {
        try {
            HttpURLConnection conn = this.createHttpURLConnection(url, "POST", true);
            byte[] json = null;
            if (args != null && args.size() > 0) {
                Gson gson = new GsonBuilder().create();
                json = gson.toJson(args).getBytes(StandardCharsets.UTF_8);
                conn.setFixedLengthStreamingMode(json.length);
            }
            conn.connect();
            OutputStream out = conn.getOutputStream();
            if (json != null) {
                out.write(json);
            }
            out.flush();
            String response = readResponseFromConnection(conn);
            conn.disconnect();
            out.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean postRequestWithoutResponse(String url, Map<String, Object> args) {
        try {
            HttpURLConnection conn = this.createHttpURLConnection(url, "POST", false);
            byte[] json = null;
            if (args != null && args.size() > 0) {
                Gson gson = new GsonBuilder().create();
                json = gson.toJson(args).getBytes(StandardCharsets.UTF_8);
                conn.setFixedLengthStreamingMode(json.length);
            }
            conn.connect();
            OutputStream out = conn.getOutputStream();
            if (json != null) out.write(json);
            out.flush();
            conn.disconnect();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRequestWithoutResponse(String url) {
        String result = "";
        try {
            HttpURLConnection conn = this.createHttpURLConnection(url, "GET", true);
            conn.connect();
            result = readResponseFromConnection(conn);
            conn.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    private HttpURLConnection createHttpURLConnection(String url, String method, boolean expectResponse) throws IOException {
        URL urlInside = new URL(url);
        HttpURLConnection conn = ((HttpURLConnection) urlInside.openConnection());
        conn.setRequestMethod(method);
        conn.setDoOutput(expectResponse);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        return conn;
    }

    private String readResponseFromConnection(HttpURLConnection connection) throws IOException {
        if(connection.getResponseCode() != 200) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    public Map<String, Object> addArgumentToMap(Map<String, Object> map, String name, Object val) {
        map.put(name, val);
        return map;
    }
}
