package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.RequestLogin;
import main.java.serviceClasses.ResponseLogin;
import main.java.serviceClasses.Services;
import java.io.*;
import java.net.HttpURLConnection;

public class HandlerLogin implements HttpHandler {

    Services service;
    public HandlerLogin(Services service) {
        this.service = service;
    }

    public void handle(HttpExchange exchange){
        EncoderDecoder coder = new EncoderDecoder();
        RequestLogin loginRequest;
        ResponseLogin loginResponse;

        try {
            loginRequest = coder.decodeRequestLogin(readString(exchange.getRequestBody()));
            loginResponse = service.login(loginRequest);

            if(loginResponse.getSuccess()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseLogin(loginResponse), respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseLogin(loginResponse), respBody);
                respBody.close();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}