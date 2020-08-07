package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.RequestRegister;
import main.java.serviceClasses.ResponseRegister;
import main.java.serviceClasses.Services;
import java.io.*;
import java.net.HttpURLConnection;

public class HandlerRegister implements HttpHandler {

    Services service;

    public HandlerRegister(Services service) {
        this.service = service;
    }


    public void handle(HttpExchange exchange) throws IOException {
        EncoderDecoder coder = new EncoderDecoder();
        ResponseRegister registerResponse;
        RequestRegister registerRequest;

        try {
            registerRequest = coder.decodeRequestRegister(readString(exchange.getRequestBody()));
            registerResponse = service.register(registerRequest);

            if(registerResponse.getSuccess()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseRegister(registerResponse), respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseRegister(registerResponse), respBody);
                respBody.close();
            }
        }catch(IOException e){
            e.printStackTrace();
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