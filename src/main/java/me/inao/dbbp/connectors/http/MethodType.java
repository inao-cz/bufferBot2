package me.inao.dbbp.connectors.http;

public enum MethodType {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;
    MethodType(String method){
        this.method = method;
    }

    public String getMethod(){
        return method;
    }
}
