package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.ResponseClear;
import main.java.serviceClasses.Services;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerClear implements HttpHandler {

    Services service;

    public HandlerClear(Services service) {
        this.service = service;
    }

    public void handle(HttpExchange exchange){
        EncoderDecoder coder = new EncoderDecoder();
        ResponseClear clearResponse;

        try {
            clearResponse = service.clear();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(coder.encodeResponseClear(clearResponse), respBody);
            respBody.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}