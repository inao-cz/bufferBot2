package me.inao.dbbp.connectors.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public abstract class HttpConnector{


    public HttpURLConnection createConnection() throws IOException {
        URL obj = new URL(getValues().url());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(getValues().method().getMethod());
        con.setDoOutput(getValues().fetch());
        con.setUseCaches(false);
        con.setReadTimeout(getValues().readTimeout());
        con.setConnectTimeout(getValues().connectionTimeout());
        return con;
    }

    public HttpURLConnection addHeaders(HttpURLConnection connection, Map<String, String> values){
        values.forEach(connection::addRequestProperty);
        return connection;
    }

    public Http getValues(){
        return getClass().getAnnotation(Http.class);
    }

}
