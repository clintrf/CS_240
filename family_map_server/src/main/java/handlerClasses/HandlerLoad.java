package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.RequestLoad;
import main.java.serviceClasses.ResponseLoad;
import main.java.serviceClasses.Services;
import java.io.*;
import java.net.HttpURLConnection;

public class HandlerLoad implements HttpHandler {

    Services service;

    public HandlerLoad(Services service) {
        this.service = service;
    }


    public void handle(HttpExchange exchange){
        EncoderDecoder coder = new EncoderDecoder();
        RequestLoad loadRequest;
        ResponseLoad loadResponse;

        try {
            loadRequest = coder.decodeRequestLoad(readString(exchange.getRequestBody()));
            loadResponse = service.load(loadRequest);

            if(loadResponse.getSuccess()){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseLoad(loadResponse), respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(coder.encodeResponseLoad(loadResponse), respBody);
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